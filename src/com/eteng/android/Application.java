package com.eteng.android;

import android.util.Log;
import cn.jpush.android.api.JPushInterface;

public class Application extends android.app.Application {
	
	private static final String TAG = "JPush";

	@Override
	public void onCreate() {
		Log.d(TAG, "[ExampleApplication] onCreate");
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		
		super.onCreate();
	}

}
