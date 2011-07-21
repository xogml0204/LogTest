package kr.ac.kookmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class LogViewActivity extends Activity {
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logview);
		
		Intent intent = getIntent();
		String fileName = intent.getStringExtra("Format");
		
		TextView mTextView;
		mTextView = (TextView)findViewById(R.id.text_view_id1);
		
		String[] temp = LogUtil.FileOpen(fileName);
		
		
		for(int i=0; temp.length>i; i++) {
			
			SpannableString ss = new SpannableString(temp[i]);
		
			switch(temp[i].charAt(0)) {
			case 'D' :
				ss.setSpan(new ForegroundColorSpan(0xFF0000FF), 0, temp[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				break;
			case 'E':
				ss.setSpan(new ForegroundColorSpan(0xFFFF0000), 0, temp[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				break;
			case 'I':
				ss.setSpan(new ForegroundColorSpan(0xFF00FF00), 0, temp[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				break;
			case 'W':
				ss.setSpan(new ForegroundColorSpan(0xFFFFFF00), 0, temp[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				break;
			default:
				ss.setSpan(new ForegroundColorSpan(0xFF000000), 0, temp[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				break;
			}
			mTextView.append(ss);
			mTextView.append("\n");			
		}
		
        ScrollView svw = (ScrollView)findViewById(R.id.scr1); 
        svw.setVerticalScrollBarEnabled(true);
	}
	
	public void mOnClick(View v) {
		finish();
	}

}