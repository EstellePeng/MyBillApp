package com.example.bill;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

class ListViewAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<PInfomation> list;
	private ArrayList<GridInfo> listincome;
	private ArrayList<GridInfo> listpayout;
	private double[] total_dayincome;
	private double[] total_daypayout;
	public ListViewAdapter(Context context, ArrayList<PInfomation> list,
			double[] total_dayincome, double[] total_daypayout) {
		super();
		this.context = context;
		this.list = list;
		this.total_dayincome = total_dayincome;
		this.total_daypayout = total_daypayout;
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
		initDate();
		convertView = View.inflate(context, R.layout.listview_small_layout, null);
		TextView tv_list_time = (TextView) convertView.findViewById(R.id.tv_list_time);
		ImageView iv_small = (ImageView) convertView.findViewById(R.id.iv_small);
		TextView tv_small_type = (TextView) convertView.findViewById(R.id.tv_small_type);
		TextView tv_small_sz = (TextView) convertView.findViewById(R.id.tv_small_sz);
		LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.ll);
		String type = list.get(position).getType();
		tv_small_type.setText(type);
		tv_list_time.setText(list.get(position).getMonth()+"月"+list.get(position).getDay()+"日");
		TextView tv_list_income = (TextView) convertView.findViewById(R.id.tv_list_income);
		TextView tv_list_payout = (TextView) convertView.findViewById(R.id.tv_list_payout);
		
		for (int i = 0; i < total_dayincome.length; i++) {
			if (list.get(position).getDay()==i+1) {
				tv_list_income.setText(total_dayincome[i]+"");
			    tv_list_payout.setText(total_daypayout[i]+"");
			}
		}
//		tv_list_income.setText(total_dayincome[15]+"");
//	    tv_list_payout.setText(3+"");
		
		if (list.get(position).getIncome()==0) {
			tv_small_sz.setText(list.get(position).getPayout()+"");
			tv_small_sz.setTextColor(Color.parseColor("#16D0AE"));
			for (int i = 0; i < listpayout.size(); i++) {
				if (type.equals(listpayout.get(i).getType())) {
					iv_small.setImageResource(listpayout.get(i).getImg());
					break;
				}
			}	
		}else { 
			tv_small_sz.setText(list.get(position).getIncome()+"");
			for (int i = 0; i < listincome.size(); i++) {
				if (type.equals(listincome.get(i).getType())) {
					iv_small.setImageResource(listincome.get(i).getImg());
					break;
				}
			}
		}
		if (position!=0) {
			if (list.get(position).getDay()==list.get(position-1).getDay()) {
				ll.setVisibility(View.GONE);
			}else {
				ll.setVisibility(View.VISIBLE);
			}
		}else {
			ll.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	private void initDate() {
		
		//收入
		int[] imgs1 = {
				R.drawable.gz,
				R.drawable.jz,
				R.drawable.jj,
				R.drawable.hb,
				R.drawable.tz
				};
		String[] types1 = {"工资","兼职","奖金","红包","投资"};
		listincome = new ArrayList<GridInfo>();
		for (int i = 0; i < types1.length; i++) {
			GridInfo info1 = new GridInfo(imgs1[i], types1[i]);
			listincome.add(info1);
		}
		//支出
		int[] imgs2 ={
				R.drawable.ch,
				R.drawable.rc,
				R.drawable.yl,
				R.drawable.wg,
				R.drawable.fs,
				R.drawable.jt,
				R.drawable.fw,
				R.drawable.hf,
				R.drawable.hzp,
				R.drawable.sl
		};
		String[] types2 = {"吃喝","日常","娱乐","网购","服饰","交通","房屋","话费","化妆品","送礼"};
		listpayout = new ArrayList<GridInfo>();
		for (int i = 0; i < types2.length; i++) {
			GridInfo info2 = new GridInfo(imgs2[i], types2[i]);
			listpayout.add(info2);
		}
	}
	
}
