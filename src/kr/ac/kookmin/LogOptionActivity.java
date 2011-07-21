package kr.ac.kookmin; 

import java.io.File;

import android.app.Activity; 
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent; 
import android.content.SharedPreferences;
import android.os.Bundle; 
import android.os.Environment;
import android.view.View; 
import android.widget.Button; 
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class LogOptionActivity extends Activity{ 
     private OnCheckedChangeListener BasicCheckListener;
     
     private CheckBox c_Filter;
     private CheckBox c_File;
     private CheckBox c_Color;
     private CheckBox c_Toast;
     
     private EditText e_Filter;
     private EditText e_FileName;
     
     private boolean b_Filter;
     private boolean b_File;
     private boolean b_Color;
     private boolean b_Toast;
     
     private String s_Filter;
     private String s_FileName;
     
     private Button op_ok;
     private Button op_cancle;
     
     private boolean b_Overwrite = false;
     
     public void onCreate(Bundle savedInstanceState) { 
         super.onCreate(savedInstanceState); 
         setContentView(R.layout.optionset);
         // 이미 저장된 값들 불러와서 초기화
         OptionLoad();
         InitItems();
     }
     
     private void InitItems() {
         c_Filter =(CheckBox)findViewById(R.id.filter_use);
         c_File =(CheckBox)findViewById(R.id.file_save_set);
         c_Color =(CheckBox)findViewById(R.id.color_set);
         c_Toast =(CheckBox)findViewById(R.id.toast_use);
         c_Filter.setTag("Filter");
         c_File.setTag("File");
         c_Color.setTag("Color");
         c_Toast.setTag("Toast");
         
         e_FileName = (EditText)findViewById(R.id.file_name);
         e_Filter = (EditText)findViewById(R.id.filter_set);
         
         BasicCheckListener = new OnCheckedChangeListener() {
  			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
  				String targetBtn = (String)buttonView.getTag();
 		        if(isChecked){util_editEnable(targetBtn, true);
 		           } else {util_editEnable(targetBtn, false);}
 			}
          };
          c_Filter.setOnCheckedChangeListener(BasicCheckListener);
          c_File.setOnCheckedChangeListener(BasicCheckListener);
          c_Color.setOnCheckedChangeListener(BasicCheckListener);
          c_Toast.setOnCheckedChangeListener(BasicCheckListener);
          
          op_ok = (Button)findViewById(R.id.option_ok); 
          op_ok.setOnClickListener(new View.OnClickListener(){ 
              public void onClick(View v) { 
            	  optionOk(); 
              }}); 
          op_cancle = (Button)findViewById(R.id.option_cancel); 
          op_cancle.setOnClickListener(new View.OnClickListener(){ 
              public void onClick(View v) { 
            	  optionCancel(); 
              }});
          
          e_FileName.setText(s_FileName);
          e_Filter.setText(s_Filter);
          c_Filter.setChecked(b_Filter);
          c_File.setChecked(b_File);
          c_Color.setChecked(b_Color);
          c_Toast.setChecked(b_Toast);
          
          if(!b_File) {
			e_FileName.setEnabled(false);
			e_FileName.setClickable(false);
			e_FileName.setFocusableInTouchMode(false);
			e_FileName.setFocusable(false);
          }
          if(!b_Filter) {
        	e_Filter.setEnabled(false);
        	e_Filter.setClickable(false);
        	e_Filter.setFocusableInTouchMode(false);
        	e_Filter.setFocusable(false);
          }
     }
     
     private void optionOk() {
    	 s_FileName = e_FileName.getText().toString();
    	 s_Filter = e_Filter.getText().toString();
    	 
    	 // 파일 중복 검사
	    String ext = Environment.getExternalStorageState();

 		String mSdPath;
 		if (ext.equals(Environment.MEDIA_MOUNTED))
 			mSdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
 		else
 			mSdPath = Environment.MEDIA_MOUNTED;
 		File file = null;
 		file = new File(mSdPath + "/dir/" + s_FileName);
 		// 메세지 박스 출력
 		if(file.exists()) {
 			AlertDialog.Builder bld = new AlertDialog.Builder(LogOptionActivity.this);
 			bld.setTitle("파일명 확인");
 			bld.setMessage("입력하신 파일명이 이미 존재합니다. 계속하시겠습니까?");
 			bld.setIcon(R.drawable.icon);
 			bld.setPositiveButton("이어쓰기", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					b_Overwrite = true;
					OptionSave();
					finish();
				}
			});
 			bld.setNeutralButton("덮어쓰기", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					b_Overwrite = false;
					OptionSave();
					finish();
				}
			});
 			bld.setNegativeButton("취소", null);
 			bld.show(); 			
 		} else {
			OptionSave();
			finish();
 		}
     } 
     
     private void optionCancel() {
    	 //startActivity(new Intent(this, LogTestActivity.class)); 
    	 finish(); 
     }
     
     private void OptionSave() {
    	 // 특정 번호의 공유 저장소를 편집가능 상태로 불러온다
    	 SharedPreferences.Editor edt = getSharedPreferences("Option", Activity.MODE_PRIVATE).edit();
    	 
    	 edt.putBoolean("FilterOption", b_Filter);
    	 edt.putString("Filter", s_Filter);
    	 edt.putBoolean("SaveOption", b_File);
    	 edt.putString("FileName", s_FileName);
    	 edt.putBoolean("ColorOption", b_Color);
    	 edt.putBoolean("ToastOption", b_Toast);
    	 edt.putBoolean("OverWrite", b_Overwrite);
    	 
    	 //위에서 저장한 내용대로, 실제로 데이터를 저장한다.
    	 edt.commit();
     }
     
     private void OptionLoad() {
    	 // 저장소 객체를 생성
    	 SharedPreferences prefs = getSharedPreferences("Option", Activity.MODE_PRIVATE);
    	 b_Filter = prefs.getBoolean("FilterOption", false);
    	 s_Filter = prefs.getString("Filter", "");
    	 b_File = prefs.getBoolean("SaveOption", true);
    	 s_FileName = prefs.getString("FileName", "Default");
    	 b_Color = prefs.getBoolean("ColorOption", false);
    	 b_Toast = prefs.getBoolean("ToastOption", true);
    	 b_Overwrite = prefs.getBoolean("OverWrite", false);
     }
     
 	private void util_editEnable(String target, boolean sw){
 		if(target.compareTo("Filter") == 0) {
 			b_Filter = sw;
 			e_Filter.setEnabled(sw);
	 		e_Filter.setClickable(sw);
	 		e_Filter.setFocusableInTouchMode(sw);
	 		e_Filter.setFocusable(sw);
		} else if(target.compareTo("File") ==0) {
			b_File = sw;
 			e_FileName.setEnabled(sw);
 			e_FileName.setClickable(sw);
 			e_FileName.setFocusableInTouchMode(sw);
 			e_FileName.setFocusable(sw);
		} else if(target.compareTo("Color") == 0) {
			b_Color = sw;
		} else {
			b_Toast = sw;
		}
	}
} 