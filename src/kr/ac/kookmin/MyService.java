package kr.ac.kookmin;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyService extends Service{  
   
	private NotificationManager nm;
	private Notification notification;
	private PendingIntent intent;
	boolean mQuit; 
	NewsThread thread; 
	Toast mToast = null;
	Toast t2;
	String lastMessage = "test";
	
	boolean b_File;
	boolean b_Filter;
	boolean b_Toast;
	boolean b_Color;
	String s_FileName;
	String s_Filter;
	boolean b_Overwrite;
   
	public void onCreate() {   
		super.onCreate(); 
		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		intent = PendingIntent.getActivity(MyService.this, 0, new Intent(MyService.this, LogTestActivity.class), 0);
		notification = new Notification(android.R.drawable.ic_input_add, "Logs..", System.currentTimeMillis());
		notification.setLatestEventInfo(MyService.this, "Log Test", "Hello", intent);
		nm.notify(1234, notification);
		
	   	 SharedPreferences prefs = getSharedPreferences("Option", Activity.MODE_PRIVATE);
		 b_Filter = prefs.getBoolean("FilterOption", false);
		 s_Filter = prefs.getString("Filter", "");
		 b_File = prefs.getBoolean("SaveOption", true);
		 s_FileName = prefs.getString("FileName", "Default");
		 b_Color = prefs.getBoolean("ColorOption", false);
		 b_Toast = prefs.getBoolean("ToastOption", true);
		 b_Overwrite = prefs.getBoolean("OverWrite", false);
	}  
   
	public void onDestroy() { //서비스가 종료될 때 동작을 구현.  
		super.onDestroy(); 
		nm.cancel(1234);
	  //LinearLayout linear = (LinearLayout)View.inflate(MyService.this, R.layout.toastview, null); 
	  //Toast t2 = new Toast(MyService.this); 
	   
	  //t2.setView(linear); 
	  //t2.setText("hh"); 
	  //t2.show(); 
	  //mToast = Toast.makeText(this, "Service End!!!", Toast.LENGTH_SHORT); 
		Toast.makeText(this, "Service End!", 0).show();  
		t2.cancel();
  
		mQuit = true;  
 }  
   
	public int onStartCommand(Intent intent,int flags, int startId) {  
		//서비스 시작할 때 수행할 동작을 구현.   
		Log.e("MyService", "Service startId = " + startId);  
		super.onStart(intent, startId);  
        mQuit = false; 
         
        //NewsThread 
        thread = new NewsThread(this, mHandler);  
        thread.setDaemon(true);  
        thread.start();  
        return START_STICKY;      
    }  
      
    public IBinder onBind(Intent intent) {  
    	//서비스 작업 코드를 구현.  
     return null;  
    }  
      
    class NewsThread extends Thread {  
    	MyService mParent;  
    	Handler mHandler;  
    	Test test1;// = new Test(); 
    	public NewsThread(MyService parent, Handler handler) {  
    		mParent = parent;  
    		mHandler = handler;  
    		if(b_Filter) {
    			test1 = new Test(s_Filter);
    		} else {
    			test1 = new Test();
    		}
    	}  
    	public void run(){  
    		while(!mQuit) {  
    			test1.run();
    			if(b_File == true) {
    				LogUtil.FileSave(test1.test1, s_FileName, b_Overwrite);
    				if(b_Overwrite == false)
    					b_Overwrite = true;
    			}

    			Message msg = new Message();  
    			msg.what = 0; 
    			//msg.obj = LogUtil.FileOpen();
    			msg.obj = test1.GetLine();
    			//msg.obj = test1.test1;
    			mHandler.sendMessage(msg);
    			
    			//test1.test1.clear();
    			try {Thread.sleep(3000);  
    			}catch (Exception e){;}  
    		} 
    	}  
    }

Handler mHandler = new Handler() {  
    	public void handleMessage(Message msg) {  
    		if(msg.what == 0) {  
    			String news = (String)msg.obj;
    			String[] parse = news.split("\n");
    			
    			
    			//ArrayList<String> message = (ArrayList<String>)msg.obj;
    			//String news = message.get(message.size()-1);
    			
    			if(lastMessage.compareTo(parse[2]) != 0) {
     
    			LinearLayout linear = (LinearLayout)View.inflate(MyService.this, R.layout.toastview, null); 
    			TextView text1 = (TextView)linear.findViewById(R.id.toastText1);
    			TextView text2 = (TextView)linear.findViewById(R.id.toastText2);
    			TextView text3 = (TextView)linear.findViewById(R.id.toastText3);
     
    			t2 = new Toast(MyService.this); 
    			t2.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
     
       			char ch = news.charAt(0); // D, E, I, W 
       			int i =0; 
     
       			//D/~~~~: 까지 파싱. 
       			for(i=0; news.charAt(i) != ':'; i++) 
       			{
       			} 
     
       			//text1.setBackgroundColor(0x55ffffff);
       				// : 뒤에 나오는 내용은 black
       			
       			char opword1 = parse[0].charAt(0); // D, E, I, W 
       			setbkColor(opword1, text1);
       			
       			SpannableStringBuilder sp = new SpannableStringBuilder(parse[0]); 
       			//sp.setSpan(new ForegroundColorSpan(0xFF0000FF), 0, parse[0].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
       			text1.append(sp);
       			text1.append("\n");
       			
       			
       		
       			(tempStr.tStr) += (sp.toString() +"\n");
       			
       			
       			//text2.setBackgroundColor(0x553344ff);
       			
       			char opword2 = parse[1].charAt(0); // D, E, I, W 
       			setbkColor(opword2, text2);
       			sp = new SpannableStringBuilder(parse[1]); 
       			//sp.setSpan(new ForegroundColorSpan(0xFFFF00FF), 0, parse[1].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
       			text2.append(sp);
       			text2.append("\n");
       			
       			(tempStr.tStr) += (sp.toString() +"\n");
       			
       			
       			
       			//text2.setBackgroundColor(0x5500ff00);
       			
       			char opword3 = parse[2].charAt(0); // D, E, I, W 
       			setbkColor(opword3, text3);
       			sp = new SpannableStringBuilder(parse[2]); 
       			//sp.setSpan(new ForegroundColorSpan(0xFF00FFFF), 0, parse[2].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
       			text3.append(sp);
       			
       			//	MyService - mTextView 에 붙임. 실제로 보이는 내용. 
                //(temp.tv).append(sp+"\n");
       			(tempStr.tStr) += sp.toString() +"\n";
       	
       			
       			t2.setView(linear); 
       			if(b_Toast == true) {
       				t2.show(); 
       			}
       		
    			}
       			lastMessage = parse[2];
    		}  
    	}  
    }; 
  //I일경우 배경 white, 명령줄 텍스트는 red 
    void setbkColor(char ch, TextView t)
    {
    	switch(ch) 
    	{ 
    	case 'I':
    		t.setBackgroundColor(0x55ffff00); 
    		break; 
    	case 'D':
    		t.setBackgroundColor(0x556495ed); 
    		break;
    	case 'W':
    		t.setBackgroundColor(0x55c71585); 
    		break;
    	case 'E':
    		t.setBackgroundColor(0x559acd32); 
    		break;
    	} 
    }
}
