package com.genymotion.genyd;

import android.app.Service;
import android.os.IBinder;
import android.util.Log;
import android.text.ClipboardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class GenydService extends Service {

	private BroadcastReceiver receiver;
	private IntentFilter filter;
	private ClipboardManager clipboardManager;
	private Boolean stopRecursion;

	static {
		Log.d(TAG, "Loading genyd library");
		System.loadLibrary("genyd");
	}

	public GenydService(Context context) {
		Log.d(TAG, "GenydService startup");

		stopRecursion = false;
		registerReceiver(receiver, filter);

		clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		filter = new IntentFilter("com.genymotion.clipboardproxy.CLIP_CHANGED");
		receiver = new myBroadcastReceiver();
	}

		class myBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("com.genymotion.clipboardproxy.CLIP_CHANGED")) {
				synchronized (stopRecursion) {
					if (!stopRecursion) {
						if (clipboardManager.hasText()) {
							setHostClipboard(clipboardManager.getText().toString());

							Log.d("GenydService", "onPrimaryClipChanged");
						}
					} else {
						stopRecursion = false;
					}
				}
			}
		}
	}

	private native void startGenyd();

	private class GenydThread implements Runnable {
		@Override
		public void run() {
			Log.d(TAG, "starting genyd");
			startGenyd();
		}
	}

	private void setAndroidClipboard(String text) {
		synchronized (stopRecursion) {
			Log.d(TAG, "Set clipboard");
			stopRecursion = true;
			clipboardManager.setText(text);
		}
	}

	private native void setHostClipboard(String text);

	public native double getGpsAltitude();

	public native void setGpsAltitude(double value);

	public native boolean getGpsStatus();

	public native void setGpsStatus(boolean value);
}
