package com.example.bill;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

//Õ¯∏Ò≤ºæ÷  ≈‰∆˜
	class GridAdapter extends BaseAdapter{
		private Context context;
		private ArrayList<GridInfo> list;
		public GridAdapter(Context context, ArrayList<GridInfo> list) {
			super();
			this.context = context;
			this.list = list;
		}
		public int getCount() {
			return list.size();
		}
		public Object getItem(int position) {
			return list.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = View.inflate(context, R.layout.gridview_layout, null);
			ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
			CheckBox cb = (CheckBox) convertView.findViewById(R.id.cb);
			TextView tv_type = (TextView) convertView.findViewById(R.id.tv_type);
		    iv.setImageResource(list.get(position).getImg());
		    tv_type.setText(list.get(position).getType());
			return convertView;
		}
	}

