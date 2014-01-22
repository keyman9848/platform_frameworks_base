package com.genymotion.genyd;

//import android.app.Service;
import android.content.ClipData;
//import android.content.Intent;
//import android.os.IBinder;
import android.util.Log;
import android.content.ClipboardManager;
import android.content.Context;

public class GenydService extends IGenydService.Stub implements
		ClipboardManager.OnPrimaryClipChangedListener {

	private static final String TAG = "GenydService";
	private ClipboardManager clipboardManager;
	private Context myContext;

	static {
		Log.d(TAG, "Loading genyd library");
		System.loadLibrary("genyd");
	}

	public GenydService(Context context) {
		Log.d(TAG, "GenydService startup");
		
		myContext = context;

		clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		clipboardManager.addPrimaryClipChangedListener(this);

		new Thread(new GenydThread()).start();
	}

	@Override
	public void onPrimaryClipChanged() {

		if (clipboardManager.hasPrimaryClip()) {
			ClipData data = clipboardManager.getPrimaryClip();

			CharSequence label = data.getDescription().getLabel();
			if (label == null
					|| (label != null && !label.toString().equals(TAG))) {

				ClipData.Item item = data.getItemAt(0);
				String clipboardText = item.coerceToText(myContext).toString();
				setHostClipboard(clipboardText);

				Log.d(TAG, "onPrimaryClipChanged");
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
		Log.d(TAG, "Set clipboard");
		clipboardManager.setPrimaryClip(ClipData.newPlainText(TAG, text));
	}

	private native void setHostClipboard(String text);

    /** Error */
    
	public native int getError();

    /** Battery */
    
	public native int getBatteryLevel();
	public native void setBatteryLevel(int value);

	public native int getBatteryMode();
	public native void setBatteryMode();
    
	public native int getBatteryStatus();
	public native void setBatteryStatus();
    
    /** Gps */

	public native double getGpsAccuracy();
	public native void setGpsAccuracy();
    
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
    
    /** Orientation */
    
	public native int getOrientationAngle();
	public native void setOrientationAngle(int angle);
    
    /** Phone */
    
	public native void sendSms(String source, String msg);
	public native void PhoneCall(String source);
    
    /** Radio **/
    
	public native int getPhoneType();
	public native void setPhoneType(int type);
    
	public native String getDeviceId();
	public native void setDeviceId(String id);
	public native void setRandomDeviceId();
}
