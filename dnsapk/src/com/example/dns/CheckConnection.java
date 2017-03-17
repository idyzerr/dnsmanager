package com.example.dns;

import org.apache.http.conn.util.InetAddressUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.dnsapk.R;

public class CheckConnection extends Activity {
	EditText editIp;
	Button buttonLogin;
	public String ip;
	Context context;
	AlertDialog alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_connection);
		context = this;
		setTitle("DNS Manager");
		editIp = (EditText)findViewById(R.id.editIp);
		buttonLogin = (Button)findViewById(R.id.buttonLogin);
		alertDialog = new AlertDialog.Builder(this).create();
		
		buttonLogin.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ip = editIp.getText().toString();
				if(ip.equals("")){
					alertDialog.setTitle("Uwaga!");
		            alertDialog.setMessage("Adres IP nie został wprowadzony!");
		            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog,int which) {
		                        }
		            });
		            alertDialog.show();
				}else if (!InetAddressUtils.isIPv4Address(ip)) {
					alertDialog.setTitle("Uwaga!");
		            alertDialog.setMessage("Wprowadzony adres IP jest niepoprawny!");
		            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog,int which) {
		                        }
		            });
		            alertDialog.show();
				}else{
					SshTask sshConnection = new SshTask("ls /", context, ip);
					sshConnection.execute();
				}
			}
		});
		
		editIp.setOnFocusChangeListener( new OnFocusChangeListener() {
		
		@SuppressWarnings("deprecation")
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					ip = editIp.getText().toString();
					if (!InetAddressUtils.isIPv4Address(ip)) {
						alertDialog.setTitle("Uwaga!");
			            alertDialog.setMessage("Wprowadzony adres IP jest niepoprawny!");
			            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			                        public void onClick(DialogInterface dialog,int which) {
			                        }
			            });
			            // Showing Alert Message
			            alertDialog.show();
					
					}else{
						SshTask sshConnection = new SshTask("ls /", context, ip);
						sshConnection.execute();
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
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_connection, menu);
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
