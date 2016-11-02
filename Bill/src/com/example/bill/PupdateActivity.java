package com.example.bill;

import android.os.Bundle;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class PupdateActivity extends Activity {

	private PInfomation info;
	private TextView tv_money;
	private TextView tv_fenlei;
	private TextView tv_time;
	private TextView tv_mk;
	private SQL helper;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pupdate);
		initView();
		intent = getIntent();
		info = (PInfomation)intent.getSerializableExtra("info");
//		String time = intent.getStringExtra("time");
		if (info.getIncome()==0) {
			tv_money.setText(info.getPayout()+"");
			tv_fenlei.setText("֧��>"+info.getType());
		}else {
			tv_money.setText(info.getIncome()+"");
			tv_fenlei.setText("����>"+info.getType());
		}
		tv_time.setText(info.getYear()+"��"+info.getMonth()+"��"+info.getDay()+"��");
		
		if (info.getMk().equals("")) {
			tv_mk.setHint("δ��д");
		}else tv_mk.setText(info.getMk());
		
	}

	private void initView() {
		tv_money = (TextView) findViewById(R.id.tv_money);
		tv_fenlei = (TextView) findViewById(R.id.tv_fenlei);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_mk = (TextView) findViewById(R.id.tv_mk);
		helper = new SQL(this);
	}
	//����
    public void turn(View v) {
		finish();
	}
    //��ת���޸�ҳ��
	public void update(View v) {
		Intent intent = new Intent(this,PshowActivity.class);
		intent.putExtra("info", info);
		startActivity(intent);
	}
	public void delete(View v) {
		Builder builder = new Builder(this);
		builder.setTitle("ɾ��");
		builder.setMessage("ȷ��Ҫɾ���˵���");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				SQLiteDatabase db = helper.getWritableDatabase();
				db.delete("money", "id=?", new String[]{info.getId()+""});
				db.close();
				setResult(2, intent);
				finish();
			}
		});
		builder.setNegativeButton("ȡ��", null);
		builder.show();
	}

}
