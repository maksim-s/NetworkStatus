package edu.mit.media;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class NetworkStatusActivity extends Activity {
	private Intent intent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        intent = new Intent(this, StatusChecker.class);
    }
    
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    	@Override
    	public void onReceive(Context context, Intent intent) {
    		updateStatus(intent);
    	}
    };
    
    @Override
    public void onResume() {
    	super.onResume();
    	startService(intent);
    	registerReceiver(broadcastReceiver, new IntentFilter(StatusChecker.BROADCAST_ACTION));
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	unregisterReceiver(broadcastReceiver);
		stopService(intent);
    }
    
    private void updateStatus(Intent intent) {
    	String counter      = intent.getStringExtra("counter");
    	String wifiStatus   = intent.getStringExtra("wifi");
    	String mobileStatus = intent.getStringExtra("3G");

    	
    	TextView txtCounter = (TextView) findViewById(R.id.counter);
    	TextView txtWifi    = (TextView) findViewById(R.id.wifi_status);
    	TextView txt3G      = (TextView) findViewById(R.id.g_status);
    	
    	txtCounter.setText(counter);
    	txtWifi.setText(wifiStatus);
    	txt3G.setText(mobileStatus);
    }
 
}