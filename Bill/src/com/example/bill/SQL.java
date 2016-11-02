package com.example.bill;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL extends SQLiteOpenHelper{

	public SQL(Context context) {
		super(context, "user.db", null, 1);
	}

	public void onCreate(SQLiteDatabase db) {
	    db.execSQL("create table money(id integer primary key autoincrement,year integer,month integer,day integer,type varchar(10),income double,payout double,mk varchar(50));");
		
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
