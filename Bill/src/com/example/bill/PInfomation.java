package com.example.bill;

import java.io.Serializable;

import org.apache.http.entity.SerializableEntity;

import android.database.sqlite.SQLiteOpenHelper;

public class PInfomation implements Serializable {
private int id;
private int year;
private int month;
private int day;
private String type;
private double income;
private double payout;
private String mk;
public PInfomation(int id, int year, int month, int day, String type,
		double income, double payout, String mk) {
	super();
	this.id = id;
	this.year = year;
	this.month = month;
	this.day = day;
	this.type = type;
	this.income = income;
	this.payout = payout;
	this.mk = mk;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getYear() {
	return year;
}
public void setYear(int year) {
	this.year = year;
}
public int getMonth() {
	return month;
}
public void setMonth(int month) {
	this.month = month;
}
public int getDay() {
	return day;
}
public void setDay(int day) {
	this.day = day;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public double getIncome() {
	return income;
}
public void setIncome(double income) {
	this.income = income;
}
public double getPayout() {
	return payout;
}
public void setPayout(double payout) {
	this.payout = payout;
}
public String getMk() {
	return mk;
}
public void setMk(String mk) {
	this.mk = mk;
}



}
