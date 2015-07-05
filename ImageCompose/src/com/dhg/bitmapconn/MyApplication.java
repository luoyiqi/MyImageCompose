package com.dhg.bitmapconn;

import android.app.Application;
import android.net.Uri;

import java.util.List;
public class MyApplication extends Application {
	private static List<Uri> lsit;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	public static List<Uri> getLsit() {
		return lsit;
	}
	public static void setLsit(List<Uri> lsit) {
		MyApplication.lsit = lsit;
	}
	

}
