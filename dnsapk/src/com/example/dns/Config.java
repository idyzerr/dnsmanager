package com.example.dns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dnsapk.R;


public class Config extends Activity {
	
	public ListView listView;
	private Intent intent;
	private String fileNames;
	private String ip;
	private String[] strTabFileNames;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		setTitle("Pliki konfiguracyjne");
		context = this;
		intent = getIntent();
		fileNames = intent.getStringExtra("fileNames");
		ip = intent.getStringExtra("ip");
		strTabFileNames = fileNames.split(" ");
		listView = (ListView)findViewById(R.id.listView);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	              android.R.layout.simple_list_item_1, android.R.id.text1, strTabFileNames);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String itemValue = (String)listView.getItemAtPosition(position);
				String command = "cat /etc/bind/";
				SshTask sshConnection = new SshTask(command, itemValue, context, ip);
				sshConnection.execute();
			}
		});
	
	}
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config, menu);
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
