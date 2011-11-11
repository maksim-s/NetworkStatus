package edu.mit.media;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;

public class StatusChecker extends Service {
	public static final String BROADCAST_ACTION = "edu.mit.media.networkstatus";
	private final Handler handler = new Handler();
	Intent intent;
	int counter = 0;
	String wifiStatus = "";
	String mobileStatus = "";

	@Override
	public void onCreate() {
		super.onCreate();
		intent = new Intent(BROADCAST_ACTION);
	}
	
	@Override
	public void onStart(Intent intent, int startd) {
		handler.removeCallbacks(sendNetworkStatus);
		handler.postDelayed(sendNetworkStatus, 1000);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Runnable sendNetworkStatus = new Runnable() {
		public void run() {
			sendNetworkInfo();
			handler.postDelayed(this, 2000);
		}
	};
	
	private void sendNetworkInfo() {
		ConnectivityManager connection = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = connection.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobile = connection.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (wifi.isAvailable()) {
			wifiStatus = "Wifi Status: UP";
		} else {
			wifiStatus = "Wifi Status: DOWN";
		}
		if (mobile.isAvailable()) {
			mobileStatus = "Mobile Status: UP";
		} else {
			mobileStatus = "Mobile Status: DOWN";
		}
		intent.putExtra("wifi", wifiStatus);
		intent.putExtra("3G", mobileStatus);
		intent.putExtra("counter", "Counter: " + String.valueOf(++counter));
		sendBroadcast(intent);
	}
	
	@Override
	public void onDestroy() {
		handler.removeCallbacks(sendNetworkStatus);
		super.onDestroy();
	}
	
}
