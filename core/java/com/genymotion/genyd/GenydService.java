package com.genymotion.genyd;

import android.util.Log;
import android.text.ClipboardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class GenydService extends IGenydService.Stub {

	private static final String TAG = "GenydService";
	private BroadcastReceiver receiver;
	private IntentFilter filter;
	private ClipboardManager clipboardManager;
	private Boolean stopRecursion;

	static {
		Log.d("GenydService", "Loading genyd library");
		System.loadLibrary("genyd");
	}

	public GenydService(Context context) {
		Log.d(TAG, "GenydService startup");

		stopRecursion = false;

		clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		filter = new IntentFilter("com.genymotion.clipboardproxy.CLIP_CHANGED");
		receiver = new myBroadcastReceiver();
		context.registerReceiver(receiver, filter);
	}

	class myBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("com.genymotion.clipboardproxy.CLIP_CHANGED")) {
				synchronized (stopRecursion) {
					if (!stopRecursion) {
						if (clipboardManager.hasText()) {
							setHostClipboard(clipboardManager.getText().toString());

							Log.d(TAG, "onPrimaryClipChanged");
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

	/** Error */

	public native int getError();
	public native int getTokenValidity();

	/** Battery */

	public native int getBatteryLevel();
	public native void setBatteryLevel(int level);

	public native int getBatteryMode();
	public native void setBatteryMode(int mode);

	public native int getBatteryStatus();
	public native void setBatteryStatus(int status);

	/** Gps */

	public native double getGpsAccuracy();
	public native void setGpsAccuracy(double value);

	public native double getGpsAltitude();
	public native void setGpsAltitude(double value);

	public native double getGpsBearing();
	public native void setGpsBearing(double value);

	public native double getGpsLatitude();
	public native void setGpsLatitude(double value);

	public native double getGpsLongitude();
	public native void setGpsLongitude(double value);

	public native boolean getGpsStatus();
	public native void setGpsStatus(boolean value);

	/** Android Id */

	public native String getAndroidId();
	public native void setAndroidId(String id);
	public native void setRandomAndroidId();

	public native String getDeviceId();
	public native void setDeviceId(String id);
	public native void setRandomDeviceId();

	/** Orientation */

	public native double getOrientationAngle();
	public native void setOrientationAngle(double angle);
}
