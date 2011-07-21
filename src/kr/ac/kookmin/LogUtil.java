package kr.ac.kookmin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.os.Environment;

public class LogUtil {
	public static void FileSave(ArrayList<String> temp, String FileName, boolean overwrite) {
		String ext = Environment.getExternalStorageState();

		String mSdPath;
		if (ext.equals(Environment.MEDIA_MOUNTED))
			mSdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		else
			mSdPath = Environment.MEDIA_MOUNTED;

		File dir = null;
		File file = null;
		String nextLine = "\n";

		dir = new File(mSdPath + "/dir");
		dir.mkdir();

		try {
			file = new File(mSdPath + "/dir/" + FileName);
			FileOutputStream fos;
			
			fos = new FileOutputStream(file, overwrite);
			
			for(int i=0; i<temp.size(); i++) {
				String str = temp.get(i);
				
				fos.write(str.getBytes());
				
				fos.write(nextLine.getBytes());
			}
			fos.close();
		} catch (IOException e) {
			return;
		}
	}

	public static String[] FileOpen(String files) {
		String ext = Environment.getExternalStorageState();
		
		String mSdPath;
		if (ext.equals(Environment.MEDIA_MOUNTED))
			mSdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		else
			mSdPath = Environment.MEDIA_MOUNTED;
		
		byte[] data;

		try {
			FileInputStream fis = new FileInputStream(mSdPath + "/dir/" + files );
			data = new byte[fis.available()];
			while(fis.read(data) != -1) {;}
			fis.close();
		} catch(IOException e) {
			return null;
		}
		// Line Parsing Array List
		String abc = new String(data);
		String[] parse = abc.split("\n");
		return parse;
	}
}
