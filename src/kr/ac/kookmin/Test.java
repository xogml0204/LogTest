package kr.ac.kookmin; 

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.ArrayList; 

public class Test 
{
	private ArrayList<String> LOGCAT_CMDS = new ArrayList<String>();
	public ArrayList<String> test1 = new ArrayList<String>();
	private static final int BUFFER_SIZE = 1024;
  
	private BufferedReader reader = null;
 
	public Process mLogcatProc = null;

	public Test() { 
		LOGCAT_CMDS.add("logcat"); 
		LOGCAT_CMDS.add("-d"); 
		LOGCAT_CMDS.add("");  
	}
	
	public Test(String Option) { 
		LOGCAT_CMDS.add("logcat"); 
		LOGCAT_CMDS.add("-d"); 
		LOGCAT_CMDS.add(Option);
	} 
   
	public String GetLine(){ 
	  String temp = test1.get(test1.size()-3) + "\n" + test1.get(test1.size()-2) + "\n" + test1.get(test1.size()-1); 
	  test1.clear(); 
	  return temp; 
	} 

	public void run(){ 
		try {
			   String[] cmds = new String[LOGCAT_CMDS.size()]; 
			   LOGCAT_CMDS.toArray(cmds); 
			   mLogcatProc = Runtime.getRuntime().exec(cmds); 
		} catch (IOException e){ 
			e.printStackTrace(); 
			return;
		}
 
		try {
			reader = new BufferedReader(new InputStreamReader(mLogcatProc.getInputStream()), BUFFER_SIZE);
			String line;
			while((line = reader.readLine()) != null) {
				
				test1.add(line);
			}
			
		} catch (IOException e){ 
			e.printStackTrace(); } 
		finally { 
			if (reader != null) 
				try { reader.close(); } catch (IOException e) {} 
			stopCatter(); 
		}
	} 
	public void stopCatter(){
		if (mLogcatProc == null) 
			return; 
		mLogcatProc.destroy(); 
		mLogcatProc = null; 
	} 
}//end of class