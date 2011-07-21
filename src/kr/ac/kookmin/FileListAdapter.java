package kr.ac.kookmin;

import java.sql.Date;
import java.util.ArrayList;

import kr.ac.kookmin.FileListActivity.FileListItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FileListAdapter extends BaseAdapter {
		
		Context maincon;
		LayoutInflater Inflater;
		ArrayList<FileListItem> arSrc;
		int layout;
		
		public FileListAdapter(Context context, int alayout, ArrayList<FileListItem> aarSrc) {
			maincon = context;
			Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arSrc = aarSrc;
			layout = alayout;
		}

		public int getCount() {
			return arSrc.size();
		}

		public Object getItem(int position) {
			return arSrc.get(position).m_file;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = Inflater.inflate(layout, parent, false);
			}
			ImageView img = (ImageView)convertView.findViewById(R.id.file_img);
			img.setImageResource(arSrc.get(position).m_icon);
			
			TextView txt = (TextView)convertView.findViewById(R.id.file_name);
			String size = String.valueOf((int)(arSrc.get(position).m_file.length()/1024));
			txt.setText(arSrc.get(position).m_file.getName() + "   (" + size + " KB)");
			
			TextView txt1 = (TextView)convertView.findViewById(R.id.file_modified);
			Date date = new Date(arSrc.get(position).m_file.lastModified());
			txt1.setText("Last-modified : " + date.toLocaleString());
			
			CheckBox check = (CheckBox)convertView.findViewById(R.id.file_check);
			check.setChecked(((ListView)parent).isItemChecked(position));
			check.setFocusable(false);
			check.setClickable(false);		
			return convertView;
		}
		
	}