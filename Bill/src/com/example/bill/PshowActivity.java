package com.example.bill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PshowActivity extends Activity {

	private PInfomation info;
	private GridView gv1;
	private GridView gv2;
	private RadioGroup rg;
	private RadioButton rb1;
	private RadioButton rb2;
	private ImageView img;
	private TextView tv_type;
	private EditText et_money;
	private EditText et_mk;
	private SQL helper;
	private ArrayList<GridInfo> list1;
	private ArrayList<GridInfo> list2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pshow);
		initView();
		initDate();
		Intent intent = getIntent();
		info = (PInfomation)intent.getSerializableExtra("info");
		if (info.getIncome()==0) {
			rb2.setChecked(true);
			rb1.setTextColor(Color.parseColor("#F49B13"));
			rb2.setTextColor(Color.WHITE);
			et_money.setText(info.getPayout()+"");
			for (int i = 0; i < list2.size(); i++) {
				if (info.getType().equals(list2.get(i).getType())) {
					img.setImageResource(list2.get(i).getImg());
					break;
				}
			}
		}else {
			rb1.setChecked(true);
			rb2.setTextColor(Color.parseColor("#F49B13"));
			rb1.setTextColor(Color.WHITE);
			et_money.setText(info.getIncome()+"");
			for (int i = 0; i < list1.size(); i++) {
				if (info.getType().equals(list1.get(i).getType())) {
					img.setImageResource(list1.get(i).getImg());
				}
			}
		}
		et_mk.setText(info.getMk());
		tv_type.setText(info.getType());
		
		gv1.setAdapter(new GridAdapter(PshowActivity.this,list1));
		gv2.setAdapter(new GridAdapter(PshowActivity.this,list2));
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == rb1.getId()) {
					gv1.setVisibility(View.VISIBLE);
					gv2.setVisibility(View.GONE);
					rb2.setTextColor(Color.parseColor("#F49B13"));
					rb1.setTextColor(Color.WHITE);
				}else {
					gv2.setVisibility(View.VISIBLE);
					gv1.setVisibility(View.GONE);
					rb1.setTextColor(Color.parseColor("#F49B13"));
					rb2.setTextColor(Color.WHITE);
				}
			}
		});
		//网格 点击事件
		gv1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				GridInfo info = list1.get(arg2);
				img.setImageResource(info.getImg());
				tv_type.setText(info.getType());
			}
		});
		gv2.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				GridInfo info = list2.get(arg2);
				img.setImageResource(info.getImg());
				tv_type.setText(info.getType());
			}
		});
	}
	private void initView() {
		//控件
		gv1 = (GridView) findViewById(R.id.gv1);
		gv2 = (GridView) findViewById(R.id.gv2);
		rg = (RadioGroup) findViewById(R.id.rg);
		rb1 = (RadioButton) findViewById(R.id.rb1);
		rb2 = (RadioButton) findViewById(R.id.rb2);
		img = (ImageView) findViewById(R.id.img);
		tv_type = (TextView) findViewById(R.id.tv_type);
		et_money = (EditText) findViewById(R.id.et_money);
		et_mk = (EditText) findViewById(R.id.et_mk);
		//获取数据库帮助类
		helper = new SQL(this);
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
		list1 = new ArrayList<GridInfo>();
		for (int i = 0; i < types1.length; i++) {
			GridInfo info1 = new GridInfo(imgs1[i], types1[i]);
			list1.add(info1);
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
		list2 = new ArrayList<GridInfo>();
		for (int i = 0; i < types2.length; i++) {
			GridInfo info2 = new GridInfo(imgs2[i], types2[i]);
			list2.add(info2);
		}
	}
	//取消
	public void cancel(View v) {
		finish();
	}
	public void submit(View v) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		long t = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
		Date date =new Date(t);
		String time = sdf.format(date);
		int year = Integer.parseInt(sdf1.format(date));
		int month = Integer.parseInt(sdf2.format(date));
		int day = Integer.parseInt(sdf3.format(date));
		double income;
		double payout;
		if (rb1.isChecked()) {
			income = Double.parseDouble(et_money.getText()+"");
			payout = 0;
		}else {
			payout = Double.parseDouble(et_money.getText()+"");
			income = 0;
		}
		String type = tv_type.getText()+"";
		String mk = et_mk.getText()+"";
		values.put("year",year );
		values.put("month",month );
		values.put("day",day );
		values.put("type",type );
		values.put("income",income );
		values.put("payout",payout );
		values.put("mk",mk );
		db.update("money", values, "id=?", new String[]{info.getId()+""});
		db.close();
		Intent intent = new Intent(PshowActivity.this,PbjMainActivity.class);
		startActivity(intent);
	}

}
