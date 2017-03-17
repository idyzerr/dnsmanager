package com.example.dns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dnsapk.R;

public class DatabaseText extends Activity {

	private Intent intent;
	private TextView textViewDadabase;
	private String databaseText;
	private String fileName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database_text);
		intent = getIntent();
		databaseText = intent.getStringExtra("databaseText");
		fileName = intent.getStringExtra("fileName");
		setTitle("/etc/bind/domeny/" + fileName);
		textViewDadabase = (TextView)findViewById(R.id.textViewDatabase);
		textViewDadabase.setText(databaseText);
	}
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.database_text, menu);
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
