package com.example.dns;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.dnsapk.R;

@SuppressLint("ShowToast")
public class MainActivity extends Activity{
	
	Button buttonStart;
	Button buttonStop;
	Button buttonStatus;
	Button buttonDatabase;
	Button buttonLogs;
	Button buttonConfig;
	ProgressDialog pDlg;
	EditText editLogin;
	EditText editPassword;
	EditText editIp;
	AlertDialog alertDialog; 
	private OnFocusChangeListener listener;
	public static final int PLEASE_WAIT_DIALOG = 1;
	public String login;
	public String password;
	public String ip;
	public String sshMessage;
	public Context context;
	public String statusTask;
	public View v;
	public Intent intent;
	ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("DNS Manager");
		context = this;
		intent = getIntent();
		ip = intent.getStringExtra("ip");
		statusTask = "false";
		alertDialog = new AlertDialog.Builder(this).create();
		buttonStart = (Button)findViewById(R.id.buttonStart);
		buttonStop = (Button)findViewById(R.id.buttonStop);
		buttonStatus = (Button)findViewById(R.id.buttonStatus);
		buttonDatabase = (Button)findViewById(R.id.buttonDatabase);
		buttonLogs = (Button)findViewById(R.id.ButtonLog);
		buttonConfig = (Button)findViewById(R.id.buttonConfig);
		//editIp = (EditText)findViewById(R.id.editIp);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
		
		
		
		
		buttonConfig.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if(statusTask.equals("true")){
				//ip = editIp.getText().toString();
				//if(!ip.equals("")){
					String command = "ls /etc/bind | grep named";
					SshTask sshConnection = new SshTask(command, context, ip);
					sshConnection.execute();
				//}else{
				//	Toast.makeText(MainActivity.this, "Ip address field is empty!", Toast.LENGTH_LONG).show();
				//}
				//} else{
					/*
					alertDialog.setTitle("Uwaga!");
		            alertDialog.setMessage("Wprowadz poprawny adres ip lub popraw klucz prywatny i publiczny!");
		            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog,int which) {
		                        }
		            });
		            // Showing Alert Message
		            alertDialog.show(); */
				//}
			}
		});
		
		buttonLogs.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if(statusTask.equals("true")){
				//ip = editIp.getText().toString();
				//if(!ip.equals("")){
					String command = "tail -n 100 /var/log/named/queries.log | head -n 100";
					SshTask sshConnection = new SshTask(command, context, ip);
					sshConnection.execute();
				//}else{
				//	Toast.makeText(MainActivity.this, "Ip address field is empty!", Toast.LENGTH_LONG).show();
				//}
				//}else{
					/*
					alertDialog.setTitle("Uwaga!");
		            alertDialog.setMessage("Wprowadz poprawny adres ip lub popraw klucz prywatny i publiczny!");
		            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog,int which) {
		                        }
		            });
		            // Showing Alert Message
		            alertDialog.show(); */
				//}
			}
		});
		/*
		editIp.setOnFocusChangeListener( new OnFocusChangeListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					ip = editIp.getText().toString();
					if (!InetAddressUtils.isIPv4Address(ip)) {
						alertDialog.setTitle("Uwaga!");
			            alertDialog.setMessage("Wprowadzony adres ip jest niepoprawny!");
			            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			                        public void onClick(DialogInterface dialog,int which) {
			                        }
			            });
			            // Showing Alert Message
			            alertDialog.show();
					
					}else{
						SshTask sshConnection = new SshTask("ls /", context, ip, new CallBack() {
							
							@Override
							public void onTaskCompleted(Object output) {
								// TODO Auto-generated method stub
								statusTask = output.toString();
								Log.v("moje", "@ " + statusTask );
							}
						});
						sshConnection.execute();
						    Log.v("moje", "@ " + statusTask );
					}
				}
			}
		});
		editIp.setOnEditorActionListener( new OnEditorActionListener( ){
		    public boolean onEditorAction( TextView v, int actionId, KeyEvent event ){
		        if( (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) ||
		            (actionId == EditorInfo.IME_ACTION_DONE) ){
		            editIp.clearFocus( );
		            InputMethodManager iMgr = null;
		            iMgr = (InputMethodManager)context.getSystemService( Context.INPUT_METHOD_SERVICE );
		            iMgr.hideSoftInputFromWindow( editIp.getWindowToken(), 0 );
		        }
		        return true;
		    }
		});
		*/
		buttonDatabase.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if(statusTask.equals("true")){
				//ip = editIp.getText().toString();
				//if(!ip.equals("")){
					String command = "ls /etc/bind/domeny";
					SshTask sshConnection = new SshTask(command, context, ip);
					sshConnection.execute();
				//}else{
					//	Toast.makeText(MainActivity.this, "Ip address field is empty!", Toast.LENGTH_LONG).show();
					//}
				//}else{
					/*
					alertDialog.setTitle("Uwaga!");
		            alertDialog.setMessage("Wprowadz poprawny adres ip lub popraw klucz prywatny i publiczny!");
		            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog,int which) {
		                        }
		            });
		            // Showing Alert Message
		            alertDialog.show(); */
				//}
			}
		});
		buttonStatus.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if(statusTask.equals("true")){
				//ip = editIp.getText().toString();
				//if(!ip.equals("")){
					String command = "service bind9 status";
					SshTask sshConnection = new SshTask(command, context, ip);
					sshConnection.execute();
				//}else{
				//	Toast.makeText(MainActivity.this, "Ip address field is empty!", Toast.LENGTH_LONG).show();
				//}
				//}else{
					/*
					alertDialog.setTitle("Uwaga!");
		            alertDialog.setMessage("Wprowadz poprawny adres ip lub popraw klucz prywatny i publiczny!");
		            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog,int which) {
		                        }
		            });
		            // Showing Alert Message
		            alertDialog.show();*/
				//}
			}
		});
		
		buttonStop.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if(statusTask.equals("true")){
				//ip = editIp.getText().toString();
				//if(!ip.equals("")){
					String command = "service bind9 stop";
					SshTask sshConnection = new SshTask(command, context, ip);
					sshConnection.execute();
				//}else{
				//	Toast.makeText(MainActivity.this, "Ip address field is empty!", Toast.LENGTH_LONG).show();
				//}
				//}else{
					/*
					alertDialog.setTitle("Uwaga!");
		            alertDialog.setMessage("Wprowadz poprawny adres ip lub popraw klucz prywatny i publiczny!");
		            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog,int which) {
		                        }
		            });
		            // Showing Alert Message
		            alertDialog.show();*/
				//}
			}
		});
		
		buttonStart.setOnClickListener(new OnClickListener(){
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				//if(statusTask.equals("true")){
					// TODO Auto-generated method stub
					//ip = editIp.getText().toString();
					//if(!ip.equals("")){
						String command = "service bind9 start";
						SshTask sshConnection = new SshTask(command, context , ip);
						sshConnection.execute();
					//}else{
					//	Toast.makeText(MainActivity.this, "Ip address field is empty!", Toast.LENGTH_LONG).show();
					//}
				//}else{
					/*alertDialog.setTitle("Uwaga!");
		            alertDialog.setMessage("Wprowadz poprawny adres ip lub popraw klucz prywatny i publiczny!");
		            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog,int which) {
		                        }
		            });
		            // Showing Alert Message
		            alertDialog.show();*/
				//}
					
			}
		});
		
		
	}

	public void ipTest(String ip){
		if(ip.equals(null)){
			
		}
	}/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
