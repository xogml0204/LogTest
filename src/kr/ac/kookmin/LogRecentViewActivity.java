package kr.ac.kookmin;  

import android.app.Activity; 
import android.content.Intent;
import android.os.Bundle; 
import android.view.View; 
import android.widget.Button; 
import android.widget.ScrollView; 
import android.widget.TextView; 

public class LogRecentViewActivity extends Activity{   
     TextView rLogView;        

     public void onCreate(Bundle savedInstanceState) {  
         super.onCreate(savedInstanceState);  
         setContentView(R.layout.recentlogview);  
           
         rLogView = (TextView)findViewById(R.id.r_log_view);  
         Button close = (Button)findViewById(R.id.r_log_close);  
          
         close.setOnClickListener(new View.OnClickListener(){  
             public void onClick(View v) {  
                 closeView();  
             }});  
         
           
         String str; 

         /*8
         if(temp.tv == null) 
          str = "최근 로그 파일이 없습니다."; 
         else 
          str = (temp.tv).getText().toString(); 
          
         */
         
       
         if(tempStr.tStr == null) 
             str = "최근 로그 파일이 없습니다."; 
         else 
             str = tempStr.tStr; 
           
         rLogView.setText(str); 
         
         
         ScrollView r_svw = (ScrollView)findViewById(R.id.r_log_scr);  
         r_svw.setVerticalScrollBarEnabled(true); 
     }  

     private void closeView() {   
         finish(); 
       //Activity 이동 (MainMenu로) 
      //startActivity(new Intent(this, LogTestActivity.class)); 
     }
}