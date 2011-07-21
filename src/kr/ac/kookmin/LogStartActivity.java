package kr.ac.kookmin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class LogStartActivity extends Activity{
    ComponentName mService;   
     TextView mTextView;      

     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.main);
         
         mTextView = (TextView)findViewById(R.id.text_view_id);
         Button start = (Button)findViewById(R.id.start_button);
         start.setOnClickListener(new View.OnClickListener(){
             public void onClick(View v) {
                 startHelloService();
             }});
         
         Button stop = (Button)findViewById(R.id.stop_button);
         stop.setOnClickListener(new View.OnClickListener(){
             public void onClick(View v) {
                 stopHelloService();
             }});
                 
         Button view = (Button)findViewById(R.id.view_button);
         view.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
           viewService();
         }});
        
         Button main = (Button)findViewById(R.id.main_button);
         main.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
           mainService();
         }});
        
         ScrollView svw = (ScrollView)findViewById(R.id.scr);
         svw.setVerticalScrollBarEnabled(true);
     }

     private void startHelloService() { 
         mService = startService(new Intent(this, MyService.class));
         mTextView.append(mService.toShortString()+" started.\n");
         temp.tv = mTextView;
     }

     private void stopHelloService() {
         if (mService == null) {
             mTextView.append("No requested service.\n");
             return;
         }
         
         Intent i = new Intent();
         i.setComponent(mService);
         if (stopService(i))
             mTextView.append(mService.toShortString()+" is stopped.\n");
         else
             mTextView.append(mService.toShortString()+" is alrady stopped.\n");
     }
     private void viewService() {
      //startActivity(new Intent(this, LogViewActivity.class));
      startActivity(new Intent(this, FileListActivity.class));
     }
    


     private void stopSvc() { 
         if (mService == null) {  
             return;  
         }  
           
         Intent i = new Intent();   
         i.setComponent(mService);  
         //stopService(i);           
     } 
      
     private void mainService() {   
      //Stop service 
    	 stopSvc();         
      		//finish(); 
          
      		//Toast 
         	Toast.makeText(this, "main menu!", 0).show();   
         
         	//Activity 
         	startActivity(new Intent(this, LogTestActivity.class)); 
     	} 
     
     @Override
     public void onBackPressed() {
    	 	mainService();
     }
	} 

class temp{
    public static TextView tv;
   } 