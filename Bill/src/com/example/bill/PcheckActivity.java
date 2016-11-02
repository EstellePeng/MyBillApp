package com.example.bill;

import java.util.ArrayList;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PcheckActivity extends Activity {

	private EditText et_year;
	private EditText et_month;
	private EditText et_day;
	private ListView lv_check;
	private SQL helper;
	private ArrayList<PInfomation> list;
	private SQLiteDatabase db;
	private TextView tv_ch_year;
	private TextView tv_ch_income;
	private TextView tv_ch_payout;
	private TextView tv_ch_rest;
	private TextView tv_ch2_month;
	private TextView tv_ch2_income;
	private TextView tv_ch2_payout;
	private TextView tv_ch2_rest;
	private LinearLayout ll1;
	private LinearLayout ll2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pcheck);
		initView();
	}
	
	private void initView() {
		et_year = (EditText) findViewById(R.id.et_year);
		et_month = (EditText) findViewById(R.id.et_month);
		et_day = (EditText) findViewById(R.id.et_day);
		lv_check = (ListView) findViewById(R.id.lv_check);
		helper = new SQL(this);
		ll1 = (LinearLayout) findViewById(R.id.ll1);
		ll2 = (LinearLayout) findViewById(R.id.ll2);
		//ll1
		tv_ch_year = (TextView) findViewById(R.id.tv_ch_year);
		tv_ch_income = (TextView) findViewById(R.id.tv_ch_income);
		tv_ch_payout = (TextView) findViewById(R.id.tv_ch_payout);
		tv_ch_rest = (TextView) findViewById(R.id.tv_ch_rest);
		//ll2
		tv_ch2_month = (TextView) findViewById(R.id.tv_ch2_month);
		tv_ch2_income = (TextView) findViewById(R.id.tv_ch2_income);
		tv_ch2_payout = (TextView) findViewById(R.id.tv_ch2_payout);
		tv_ch2_rest = (TextView) findViewById(R.id.tv_ch2_rest);
	}
	public void turn(View v) {
		finish();
	}

	public void check(View v) {
		db = helper.getWritableDatabase();
		String year = et_year.getText()+"";
		String month = et_month.getText()+"";
		String day = et_day.getText()+"";
		if (year.equals("")) {
			Toast.makeText(this, "年份不能为空", 0).show();
		}else if (month.equals("")) {
			lv_check.setVisibility(View.GONE);
			ll2.setVisibility(View.GONE);
			ll1.setVisibility(View.VISIBLE);
			//查询整年
			Cursor cursor =db.rawQuery("select * from money where year=?", new String[]{year});
			list = new ArrayList<PInfomation>();
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				int month1 = cursor.getInt(cursor.getColumnIndex("month"));
				int day1 = cursor.getInt(cursor.getColumnIndex("day"));
				String type = cursor.getString(cursor.getColumnIndex("type"));
				double income = cursor.getDouble(cursor.getColumnIndex("income"));
				double payout = cursor.getDouble(cursor.getColumnIndex("payout"));
				String mk = cursor.getString(cursor.getColumnIndex("mk"));
		        PInfomation info = new PInfomation(id, Integer.parseInt(year), month1, day1, type, income, payout, mk);
		        list.add(info);
			}
			double total_income=0;
			double total_payout=0;
			for (int i = 0; i < list.size(); i++) {
				total_income +=list.get(i).getIncome();
				total_payout+=list.get(i).getPayout();
			}
			tv_ch_year.setText(year);
			tv_ch_income.setText(total_income+"");
			tv_ch_payout.setText(total_payout+"");
			tv_ch_rest.setText(total_income-total_payout+"");
		}else if (day.equals("")) {
			lv_check.setVisibility(View.GONE);
			ll1.setVisibility(View.GONE);
			ll2.setVisibility(View.VISIBLE);
			//查询某年某月
			Cursor cursor =db.rawQuery("select * from money where year=? and month=?", new String[]{year,month});
			list = new ArrayList<PInfomation>();
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				int month1 = cursor.getInt(cursor.getColumnIndex("month"));
				int day1 = cursor.getInt(cursor.getColumnIndex("day"));
				String type = cursor.getString(cursor.getColumnIndex("type"));
				double income = cursor.getDouble(cursor.getColumnIndex("income"));
				double payout = cursor.getDouble(cursor.getColumnIndex("payout"));
				String mk = cursor.getString(cursor.getColumnIndex("mk"));
		        PInfomation info = new PInfomation(id, Integer.parseInt(year), month1, day1, type, income, payout, mk);
		        list.add(info);
			}
			double total_income=0;
			double total_payout=0;
			for (int i = 0; i < list.size(); i++) {
				total_income +=list.get(i).getIncome();
				total_payout+=list.get(i).getPayout();
			}
			tv_ch2_month.setText(month+"月");
			tv_ch2_income.setText(total_income+"");
			tv_ch2_payout.setText(total_payout+"");
			tv_ch2_rest.setText(total_income-total_payout+"");
			
		}else {
			lv_check.setVisibility(View.VISIBLE);
			ll1.setVisibility(View.GONE);
			ll2.setVisibility(View.GONE);
			//查询具体到某日
			Cursor cursor =db.rawQuery("select * from money where year=? and month=? and day=?", new String[]{year,month,day});
			list = new ArrayList<PInfomation>();
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				int month1 = cursor.getInt(cursor.getColumnIndex("month"));
				int day1 = cursor.getInt(cursor.getColumnIndex("day"));
				String type = cursor.getString(cursor.getColumnIndex("type"));
				double income = cursor.getDouble(cursor.getColumnIndex("income"));
				double payout = cursor.getDouble(cursor.getColumnIndex("payout"));
				String mk = cursor.getString(cursor.getColumnIndex("mk"));
		        PInfomation info = new PInfomation(id, Integer.parseInt(year), month1, day1, type, income, payout, mk);
		        list.add(info);
			}
			double[] total_income=new double[Integer.parseInt(day)];
			double[] total_payout=new double[Integer.parseInt(day)];
			int[] days = new int[Integer.parseInt(day)];
			for (int i = 0; i <days.length; i++) {
				days[i] = i+1;
				total_income[i]=0;
				total_payout[i]=0;
				int id=0; 
				String type="" ;
				double income=0;
				double payout=0;
				String mk = "";
				Cursor cursor2 =db.rawQuery("select *from money where month=? and day=?", new String[]{month+"",days[i]+""});
				ArrayList<PInfomation> list2 = new ArrayList<PInfomation>();
				while (cursor2.moveToNext()) {
					id = cursor2.getInt(cursor2.getColumnIndex("id"));
					int year1 = cursor2.getInt(cursor2.getColumnIndex("year"));
					int month1 = cursor2.getInt(cursor2.getColumnIndex("year"));
					type = cursor2.getString(cursor2.getColumnIndex("type"));
					income = cursor2.getDouble(cursor2.getColumnIndex("income"));
					payout = cursor2.getDouble(cursor2.getColumnIndex("payout"));
					mk = cursor2.getString(cursor2.getColumnIndex("mk"));
			        PInfomation info = new PInfomation(id, year1, month1, days[i], type, income, payout, mk);
			        list2.add(info);
				}
				//计算每天的收支
				for (int j = 0; j < list2.size(); j++) {
					total_income[i]+=list2.get(j).getIncome();
					total_payout[i]+=list2.get(j).getPayout();
				}
			}
//			for (int i = 0; i < list.size(); i++) {
//				total_income[0] +=list.get(i).getIncome();
//				total_payout[0]+=list.get(i).getPayout();
//				
//			}
			lv_check.setAdapter(new ListViewAdapter(this, list, total_income, total_payout));
		}
	}
}
