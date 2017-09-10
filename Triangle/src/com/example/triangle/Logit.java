package com.example.triangle;

import android.util.Log;

public class Logit {
	public static boolean sDebug = true;
	
	public static void d(String TAG, String msg) {
		if (sDebug) {
			Log.d(TAG, msg);
		}
	}
	
	public static void i(String tag, String msg) {
		if (sDebug) {
			Log.i(tag, msg);
		}
	}
	
	public static void v(String tag, String msg) {
		if (sDebug) {
			Log.v(tag, msg);
		}
	}
	
	public static void e(String tag, String msg, Throwable throwable) {
		if (sDebug) {
			Log.e(tag, msg, throwable);
		}
	}
	
	public static void e(String tag, String msg) {
		if (sDebug) {
			Log.e(tag, msg);
		}
	}
	
	public static void w(String tag, String msg) {
		if (sDebug) {
			Log.w(tag, msg);
		}
	}
}
