package com.example.bill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



import android.R.integer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PbjMainActivity extends Activity {
	private ListView lv_list;
	private TextView tv_rest;
	private TextView tv_month1;
	private TextView tv_month2;
	private TextView tv_month3;
	private TextView tv_income;
	private TextView tv_payout;
	private SQL helper;
	private ArrayList<PInfomation> list2;
	private ListView lv;
	private double total_dayincome;
	private double total_daypayout;
	private ArrayList<PInfomation> list;
	private ArrayList<GridInfo> listincome;
	private ArrayList<GridInfo> listpayout;

	private int month;
	private int day;
	private String time;
	private ArrayList<PInfomation> list_copy;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pbj_main);
		initView();
		//initDate();
		long t = System.currentTimeMillis();
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
		Date date =new Date(t);
		month = Integer.parseInt(sdf2.format(date));
		day = Integer.parseInt(sdf3.format(date));
		tv_month1.setText(month+"月剩余金额");
		tv_month2.setText(month+"月收入");
		tv_month3.setText(month+"月支出");
		show();
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(PbjMainActivity.this,PupdateActivity.class);
//				intent.putExtra("time", time);
				intent.putExtra("info", list.get(arg2));
				startActivityForResult(intent, 2);
			}
		});
	}
	private void initView() {
		tv_rest = (TextView) findViewById(R.id.tv_rest);
		tv_month1 = (TextView) findViewById(R.id.tv_month1); 
		tv_month2 = (TextView) findViewById(R.id.tv_month2); 
		tv_month3 = (TextView) findViewById(R.id.tv_month3);
		tv_income = (TextView) findViewById(R.id.tv_income);
		tv_payout = (TextView) findViewById(R.id.tv_payout);
		lv = (ListView) findViewById(R.id.lv);
		helper = new SQL(this);
	}
	//跳转到添加页面
   public void add(View v) {
	Intent intent = new Intent(this,PaddActivity.class);
	startActivityForResult(intent, 2);
}
	//刷新页面
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//		if (requestCode==2 && resultCode == 2) {
//			time = data.getStringExtra("time");
//		}
		show();
	}
	//查询按钮
	public void search(View v) {
		Intent intent = new Intent(PbjMainActivity.this,PcheckActivity.class);
		startActivity(intent);
	}
    public void exit(View v) {
		finish();
	}
	//显示结果
	private void show(){
		SQLiteDatabase db = helper.getWritableDatabase();
		//查询本月数据
		Cursor cursor =db.rawQuery("select * from money where month=?", new String[]{month+""});
		list_copy = new ArrayList<PInfomation>();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			int year = cursor.getInt(cursor.getColumnIndex("year"));
			int day = cursor.getInt(cursor.getColumnIndex("day"));
			String type = cursor.getString(cursor.getColumnIndex("type"));
			double income = cursor.getDouble(cursor.getColumnIndex("income"));
			double payout = cursor.getDouble(cursor.getColumnIndex("payout"));
			String mk = cursor.getString(cursor.getColumnIndex("mk"));
	        PInfomation info = new PInfomation(id, year, month, day, type, income, payout, mk);
	        list_copy.add(info);
		}
		list = new ArrayList<PInfomation>();
		for (int i = list_copy.size()-1; i>=0; i--) {
			list.add(list_copy.get(i));
		}
		//计算当月总收入、总支出
		double total_income=0;
		double total_payout=0;
		for (int i = 0; i < list_copy.size(); i++) {
			total_income+=list_copy.get(i).getIncome();
			total_payout+=list_copy.get(i).getPayout();
		}
		tv_income.setText(total_income+"");
		tv_payout.setText(total_payout+"");
		tv_rest.setText(total_income-total_payout+"");
		
		//按日的结果
//		Cursor cursor2 =db.rawQuery("select *from money where month=? and day=?", new String[]{month+"",day+""});
//		list2 = new ArrayList<PInfomation>();
//		while (cursor2.moveToNext()) {
//			int id = cursor2.getInt(cursor2.getColumnIndex("id"));
//			int year = cursor2.getInt(cursor2.getColumnIndex("year"));
//			String type = cursor2.getString(cursor2.getColumnIndex("type"));
//			double income = cursor2.getDouble(cursor2.getColumnIndex("income"));
//			double payout = cursor2.getDouble(cursor2.getColumnIndex("payout"));
//			String mk = cursor2.getString(cursor2.getColumnIndex("mk"));
//	        PInfomation info = new PInfomation(id, year, month, day, type, income, payout, mk);
//	        list2.add(info);
//		}	
		double[] total_dayincomes = new double[day];
		double[] total_daypayouts = new double[day];
		total_dayincome = 0;
		total_daypayout = 0;
		int[] days = new int[day];
		for (int i = 0; i <day; i++) {
			days[i] = i+1;
			total_dayincomes[i]=0;
			total_daypayouts[i]=0;
			int id=0; 
			int year=0;
			String type="" ;
			double income=0;
			double payout=0;
			String mk = "";
			Cursor cursor2 =db.rawQuery("select *from money where month=? and day=?", new String[]{month+"",days[i]+""});
			list2 = new ArrayList<PInfomation>();
			while (cursor2.moveToNext()) {
				id = cursor2.getInt(cursor2.getColumnIndex("id"));
				year = cursor2.getInt(cursor2.getColumnIndex("year"));
				type = cursor2.getString(cursor2.getColumnIndex("type"));
				income = cursor2.getDouble(cursor2.getColumnIndex("income"));
				payout = cursor2.getDouble(cursor2.getColumnIndex("payout"));
				mk = cursor2.getString(cursor2.getColumnIndex("mk"));
		        PInfomation info = new PInfomation(id, year, month, days[i], type, income, payout, mk);
		        list2.add(info);
			}
			//计算每天的收支
			for (int j = 0; j < list2.size(); j++) {
				total_dayincomes[i]+=list2.get(j).getIncome();
				total_daypayouts[i]+=list2.get(j).getPayout();
			}
			Log.i("abc", total_dayincomes[i]+"");
		}
		lv.setAdapter(new ListViewAdapter(PbjMainActivity.this,list,total_dayincomes,total_daypayouts));
	}
	
}
