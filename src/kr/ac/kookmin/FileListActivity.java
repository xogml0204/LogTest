package kr.ac.kookmin;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class FileListActivity extends Activity {
	
	ListView list;
	ArrayList<FileListItem> fileArray;
	FileListAdapter Adapter;
	File dir = null;
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filelistview);
		DrawList();
	}
	
	public void OnClickDel(View v) {
		int pos = list.getCheckedItemPosition();
		Toast.makeText(this, String.valueOf(pos), Toast.LENGTH_SHORT).show();
		if(pos != -1)
			DeleteLogFile(pos);
	}
	public void OnClickOpen(View v) {
		int pos = list.getCheckedItemPosition();
		if(pos != -1) {
			Intent intent = new Intent(FileListActivity.this, LogViewActivity.class);
			intent.putExtra("Format", fileArray.get(pos).m_file.getName());
			startActivity(intent);
		}

	}

	public void DeleteLogFile(int pos) {
		fileArray.get(pos).m_file.delete();
		DrawList();
	}
	
			
	public void DrawList() {
		
		if(fileArray != null) {
			fileArray.clear();
		}
		fileArray = new ArrayList<FileListItem>();
		
		String ext = Environment.getExternalStorageState();

		String mSdPath;
		if (ext.equals(Environment.MEDIA_MOUNTED))
			mSdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		else
			mSdPath = Environment.MEDIA_MOUNTED;
		
		dir = new File(mSdPath + "/dir");
		
		
		for(File list : dir.listFiles())
		{
			fileArray.add(new FileListItem(R.drawable.icon, list));
		}
		
		Adapter = new FileListAdapter(this, R.layout.fileview, fileArray);
		
		list = (ListView)findViewById(R.id.filelist);
		list.setAdapter(Adapter);
		list.setItemsCanFocus(false);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
	
	class FileListItem {
		FileListItem(int aIcon, File aFile) {
			m_icon = aIcon;
			m_file = aFile; 		
		}
		int m_icon;
		File m_file;
	}
}