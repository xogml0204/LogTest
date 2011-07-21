package kr.ac.kookmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LogTestActivity extends Activity {
	ComponentName mService;
	TextView mTextView;
	String testt;
	Intent intent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
		SharedPreferences prefs = getSharedPreferences("Option", Activity.MODE_PRIVATE);
		testt = prefs.getString("FileName", "default.txt");

		Toast.makeText(this, testt, Toast.LENGTH_LONG).show();

		Button sm = (Button) findViewById(R.id.startbtn);

		sm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//startMenu();       ////////////modify
				
				startHelloService();
			}
		});
		
		Button pbtn = (Button) findViewById(R.id.pausebtn);
		pbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopHelloService();
				
			} 
		});
		
		
		Button op = (Button) findViewById(R.id.optionbtn);

		op.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				optionMenu();
			}
		});

		Button exitprog = (Button) findViewById(R.id.exitbtn);

		exitprog.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder bld = new AlertDialog.Builder(LogTestActivity.this);
	 			bld.setTitle("종료 확인");
	 			if(mService != null) {
	 				bld.setMessage("현재 실행중인 서비스를 종료하고, 프로그램을 나가시겠습니까?");
	 			} else {
	 				bld.setMessage("프로그램을 나가시겠습니까?");
	 			}
	 			bld.setIcon(R.drawable.icon);
	 			bld.setPositiveButton("종료", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						stopHelloService();
						System.exit(0);		
					}
				});
	 			
	 			bld.setNegativeButton("취소", null);
	 			bld.show();
			}
		});
		
		
		Button recentLogView = (Button)findViewById(R.id.recentlogbtn);  
        
        recentLogView.setOnClickListener(new View.OnClickListener(){  
            public void onClick(View v) {  
             recentLog();  
            }}); 
         
        Button saveLogView = (Button)findViewById(R.id.savelogbtn);  
         
        saveLogView.setOnClickListener(new View.OnClickListener(){  
            public void onClick(View v) {  
             saveLog();  
            }});

	}


	/*
	private void startMenu() { 
		  
		  //startActivity(new Intent(this, LogStartActivity.class)); 
		  //finish(); 
		  
	
		  Intent intent = new Intent(this, LogStartActivity.class); 
		  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); 
		  startActivity(intent); 
		
		 } 
	
	*/
	
	/////////////////////add!!!!!!!!!!!!!!!!!!!!!1
	
	 private void startHelloService() { 
		/* 
		Intent intent = new Intent(this, MyService.class); 
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); 
		mService = startService(intent);
         */
		
		 /////////////////////////
		 if(tempStr.tag  == -1){
			 (tempStr.tStr) = "";
			 tempStr.tag = 1;
		 }
			
		 ///////////////////////
		 
		mService = startService(new Intent(this, MyService.class)); //이거한줄만쓰는건가.......ㅡㅡ..
      
         (tempStr.tStr) += mService.toShortString()+" started.\n";
     }

	 
	 private void stopHelloService() {
         if (mService == null) {
        	 (tempStr.tStr) += "No requested service.\n";
             return;
         }
         
         Intent i = new Intent();
         i.setComponent(mService);
         
         if (stopService(i))
        	 (tempStr.tStr) += mService.toShortString()+" is stopped.\n";
         else
        	 (tempStr.tStr) += mService.toShortString()+" is already stopped.\n";
         
         tempStr.tag = -1;
         
     }
	 
	 
	 
	 ///////////////////////////////////Add End!!!!!!!!!!
	 
		 private void optionMenu() { 
		  /* 
		  startActivity(new Intent(this, LogOptionActivity.class)); 
		  finish(); 
		  */ 
		  Intent intent = new Intent(this, LogOptionActivity.class); 
		     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); 
		    
		     startActivity(intent); 
		 }  


		 private void recentLog() {    
		     /*   
		  startActivity(new Intent(this, LogRecentViewActivity.class)); 
		      finish(); 
		      */ 
		  Intent intent = new Intent(this, LogRecentViewActivity.class); 
		  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); 
		     startActivity(intent); 
		 }  
		        
		 private void saveLog() {    
		  /* 
		  startActivity(new Intent(this, FileListActivity.class)); 
		       finish(); 
		       */ 
			  Intent intent = new Intent(this, FileListActivity.class); 
			  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); 
			  startActivity(intent); 
		 }
         @Override
         public void onBackPressed() {
        	 Intent intent = new Intent();
        	 intent.setAction(Intent.ACTION_MAIN);
        	 intent.addCategory(Intent.CATEGORY_HOME);
        	 startActivity(intent);
         }
}


///ADDDDDDDDDDDDDDDDDDDDDDDD


class tempStr{
	public static String tStr = "";
	public static int tag = -1;
}
