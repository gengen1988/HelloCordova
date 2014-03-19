package com.eteng.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

public class CordovaActivity extends org.apache.cordova.CordovaActivity {
	
	private static final String TAG = "HybridPushActivity";

	private static boolean isForeground = false;
	
	//for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.eteng.android.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";

	public static boolean isForeground() {
		return isForeground;
	}
	
	private void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}
	
	// ============ life cycle ==============
	@Override
	public void onCreate(Bundle savedInstanceState) {
		registerMessageReceiver();
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		isForeground = false;
		JPushInterface.onPause(this);
		super.onPause();
	}

	@Override
	protected void onResume() {
		isForeground = true;
		JPushInterface.onResume(this);
		super.onResume();
	}
	// ============ end ==============

	public class MessageReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
				
				CordovaActivity.this.sendJavascript("alert('nihao')");
				
				Log.d(TAG, showMsg.toString());
			}
		}
	}

}
