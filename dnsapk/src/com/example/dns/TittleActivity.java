package com.example.dns;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.dnsapk.R;

public class TittleActivity extends Activity {
	
	AlertDialog alertDialog;
	Button startMenu;
	Button help;
	Context context;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tittle);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		context = this;
		startMenu = (Button)findViewById(R.id.buttonStartMenu);
		help = (Button)findViewById(R.id.buttonHelp);
		String newFolder = "/ssh";
	    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		File dir = new File(extStorageDirectory + newFolder);
		dir.mkdir();
		
		startMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent = new Intent(context, CheckConnection.class);
			context.startActivity(intent);
			}
		});
		
		help.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, HelpActivity.class);
				context.startActivity(intent);
			}
		});
	}
	/*
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
