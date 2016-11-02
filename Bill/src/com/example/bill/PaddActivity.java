package com.example.bill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.R.color;
import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.Window;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class PaddActivity extends Activity {

	private RadioButton rb1;
	private ImageView img;
	private TextView tv_type;
	private EditText et_money;
	private EditText et_mk;
	private RadioGroup rg;
	private ArrayList<GridInfo> list1;
	private ArrayList<GridInfo> list2;
	private GridView gv2;
	private GridView gv1;
	private RadioButton rb2;
	private SQL helper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_padd);
		initView();
		initDate();
		gv1.setAdapter(new GridAdapter(PaddActivity.this,list1));
		gv2.setAdapter(new GridAdapter(PaddActivity.this,list2));
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
		//���� ����¼�
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
		et_mk.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					et_mk.setBackgroundResource(R.drawable.group_book_normal);
				}else {
					et_mk.setBackgroundResource(R.drawable.group_book_select);
				}
			}
		});
	}

	private void initView() {
		//�ؼ�
		gv1 = (GridView) findViewById(R.id.gv1);
		gv2 = (GridView) findViewById(R.id.gv2);
		rg = (RadioGroup) findViewById(R.id.rg);
		rb1 = (RadioButton) findViewById(R.id.rb1);
		rb2 = (RadioButton) findViewById(R.id.rb2);
		img = (ImageView) findViewById(R.id.img);
		tv_type = (TextView) findViewById(R.id.tv_type);
		et_money = (EditText) findViewById(R.id.et_money);
		et_mk = (EditText) findViewById(R.id.et_mk);
		//��ȡ���ݿ������
		helper = new SQL(this);
		
	}
	private void initDate() {
		//����
		int[] imgs1 = {
				R.drawable.gz,
				R.drawable.jz,
				R.drawable.jj,
				R.drawable.hb,
				R.drawable.tz
				};
		String[] types1 = {"����","��ְ","����","���","Ͷ��"};
		list1 = new ArrayList<GridInfo>();
		for (int i = 0; i < types1.length; i++) {
			GridInfo info1 = new GridInfo(imgs1[i], types1[i]);
			list1.add(info1);
		}
		//֧��
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
		String[] types2 = {"�Ժ�","�ճ�","����","����","����","��ͨ","����","����","��ױƷ","����"};
		list2 = new ArrayList<GridInfo>();
		for (int i = 0; i < types2.length; i++) {
			GridInfo info2 = new GridInfo(imgs2[i], types2[i]);
			list2.add(info2);
		}
	}
	
	//������ҳ
	public void cancel(View v) {
		finish();
	}
	//������ӵ�����
	public void save(View v) {
		//��ǰϵͳʱ��
		long t = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
		Date date =new Date(t);
		String  time = sdf.format(date);
		int year = Integer.parseInt(sdf1.format(date));
		int month = Integer.parseInt(sdf2.format(date));
		int day = Integer.parseInt(sdf3.format(date));
		SQLiteDatabase db = helper.getWritableDatabase(); //�����ݿ�
		String type = tv_type.getText()+"";
		String mk = et_mk.getText()+"";
		double income;
		double payout;
		if (rb1.isChecked()) {
			income = Double.parseDouble(et_money.getText()+"");
			payout=0;
		}else {
			payout = Double.parseDouble(et_money.getText()+"");
			income=0;
		}
		String sql = "insert into money(year,month,day,type,income,payout,mk) values(?,?,?,?,?,?,?);";
		db.execSQL(sql,new Object[]{year,month,day,type,income,payout,mk});
		db.close();
		Intent intent =getIntent();
		intent.putExtra("time", time);//���˵�ʱ��
		setResult(2, intent);
		finish();
	}
	

	
	

}
