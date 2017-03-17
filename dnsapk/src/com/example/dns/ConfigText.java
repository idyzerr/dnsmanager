package com.example.dns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dnsapk.R;

public class ConfigText extends Activity {
	
	private Intent intent;
	private TextView textViewConfig;
	private String configText;
	private String fileName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_text);
		intent = getIntent();
		configText = intent.getStringExtra("configText");
		fileName = intent.getStringExtra("fileName");
		setTitle("/etc/bind/" + fileName);
		textViewConfig = (TextView)findViewById(R.id.textViewHelp);
		textViewConfig.setText(configText);
	}
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config_text, menu);
		return true;
	}
	*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
