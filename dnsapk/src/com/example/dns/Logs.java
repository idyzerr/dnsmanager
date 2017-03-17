package com.example.dns;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.dnsapk.R;

@SuppressLint("ClickableViewAccessibility")
public class Logs extends Activity  {
	
	private Intent intent;
	private EditText editTextSearch;
	private Button buttonSearch;
	private Context context;
	private String logsText;
	private TextView textViewLog;
	private ScrollView scroll;
	private String ip;
	private String command = "";
	private String command1 = "tail -n ";
	private String command2 = " /var/log/named/queries.log | head -n 100";
	private static Integer i = 2;
	private static Integer j = 2;
	Boolean searchStatus = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logs);
		context = this;
		intent = getIntent();
		editTextSearch = (EditText)findViewById(R.id.editTextSearch);
		buttonSearch = (Button)findViewById(R.id.buttonSearchLog);
		logsText = intent.getStringExtra("logsText");
		ip = intent.getStringExtra("ip");
		textViewLog = (TextView)findViewById(R.id.textViewLogs);
		textViewLog.setText(logsText);
		setTitle("/var/log/named/queries.log");
		scroll = (ScrollView)findViewById(R.id.scrollView1);
		getWindow().setSoftInputMode(
			    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
			);
		
		scroll.getViewTreeObserver().addOnScrollChangedListener(new OnScrollChangedListener() {
			
			@Override
			public void onScrollChanged() {
				// TODO Auto-generated method stub
				View view = (View) scroll.getChildAt(scroll.getChildCount() - 1);
				Integer scrollEnd = (view.getBottom() - (scroll.getHeight() + scroll.getScrollY()));
				if(scrollEnd == 0 &&  searchStatus == false){
					Integer tmp;
					tmp = i*100;
					command = command1 + tmp.toString() + command2;
					Integer msg = command.length();
					Log.v("moje",msg.toString());
					SshTask sshConnect = new SshTask(command, context, ip, textViewLog);
					sshConnect.execute();
					i++;	
				}else if(scrollEnd == 0 &&  searchStatus == true){
					Integer tmp;
					tmp = i*100;
					String searchText = editTextSearch.getText().toString();
					command = command1 + tmp.toString() + command2;
					String[] tabSearchText = searchText.split(" ");
					for(int i = 0;i < tabSearchText.length; i++){
						command = command + " | grep -i " + tabSearchText[i];
					}
					SshTask sshConnect = new SshTask(command, context, ip, textViewLog);
					sshConnect.execute();
					i++;
				}
			}
		});
		buttonSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchStatus = true;
				String searchText = editTextSearch.getText().toString();
				String command = "tail -n 100 /var/log/named/queries.log | head -n 100";
				String[] tabSearchText = searchText.split(" ");
				textViewLog.setText("");
				for(int i = 0;i < tabSearchText.length; i++){
					command = command + " | grep -i " + tabSearchText[i];
				}
				SshTask sshConnection = new SshTask(command, context, ip, textViewLog);
				sshConnection.execute();
			}
		});
	
	}
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logs, menu);
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
