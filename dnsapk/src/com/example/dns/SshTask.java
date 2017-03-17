package com.example.dns;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
//import android.provider.Settings.System;

public class SshTask extends AsyncTask<String, Void, String> {
	
	private String ip;	
	private int port 				= 22;
	private String superUser		= "root";
	private String message 			= "";
	private Context context 		= null;
	private String commandLsConfig	= "ls /etc/bind | grep named";
	private String commandLs		= "ls /etc/bind/domeny";
	private String command 			= "";
	private String commandCat 		= "cat /etc/bind/domeny/";
	private String commandCatConfig	= "cat /etc/bind/";
	private String commandStatus 	= "service bind9 status";
	private String commandLog		= "tail";
	private String commandTest		= "ls /";
	public String commandStart 		= "service bind9 start";
	public String commandStop		= "service bind9 stop";
	private String bindStar 		= " * bind9 is running";
	private String bindStop 		= " * bind9 is not running";
	private String bindStaring		= " * Starting";
	private String bindStopping		= " * Stopping";
	private String statusTask			= "false";
	private String fileName;
	private TextView textViewLog;
	private ProgressDialog pDlg = null;
	AlertDialog alertDialog;
	
	public SshTask(String command, Context context, String ip){
		this.command = command;
		this.context = context;
		this.ip = ip;
	}
	public SshTask(String command, String fileName, Context context, String ip){
		this.command = command;
		this.context = context;
		this.ip = ip;
		this.fileName = fileName;
	}
	public SshTask(String command, Context context, String ip, TextView textViewLog){
		this.command = command;
		this.context = context;
		this.ip = ip;
		this.textViewLog = textViewLog;
	}
	
	public String getStatusTask(){
		return statusTask;
	}
	@Override
    protected void onPreExecute() { 
        super.onPreExecute();
        showProgressDialog();
    }
	
	private void showProgressDialog() {           
        pDlg = new ProgressDialog(context);
        pDlg.setMessage("Ładowanie...");
        pDlg.setProgressDrawable(context.getWallpaper());
        pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDlg.setCancelable(false);
        pDlg.show(); 
    }
	@SuppressLint("ShowToast")
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub	
		try {
				JSch sshChanel = new JSch();
				sshChanel.addIdentity(Environment.getExternalStorageDirectory()+"/ssh/id_rsa");
				Session session = sshChanel.getSession(superUser, ip, port);
				session.setConfig("StrictHostKeyChecking", "no");
				session.connect();
				Channel channel=session.openChannel("exec");
				if(command.equals(commandCat)){
					((ChannelExec)channel).setCommand(this.command+this.fileName);
				}else if(command.equals(commandCatConfig)){
					((ChannelExec)channel).setCommand(this.command+this.fileName);
				}else if(command.equals(commandStart) || command.equals(commandStop)){
					((ChannelExec)channel).setCommand(this.commandStatus);
				}else{
					((ChannelExec)channel).setCommand(this.command);
				}
				channel.setInputStream(null);
				((ChannelExec)channel).setErrStream(System.err);
				channel.connect();
				MessageSystemFilter msgSys = new MessageSystemFilter(new BufferedReader(new InputStreamReader(channel.getInputStream())));
				if(command.equals(commandLs) || command.equals(commandLsConfig)){
					this.message = msgSys.getFileName();
				}else if(command.equals(commandCat) || command.equals(commandCatConfig)){
					this.message = msgSys.getbuffReadTextCat();
				}else if(command.substring(0,4).toString().equals(commandLog)){
					this.message = msgSys.getbuffReadTextLog();
				}else if(command.equals(commandTest)){
					this.message = msgSys.getbuffReadTextCommand();
					if(!this.message.equals("")) this.message = "true";
				}else{
					this.message = msgSys.getbuffReadTextCommand();
				}
				channel.disconnect();
				if( command.equals(commandStart) && this.message.equals("Usługa nie jest uruchomiona") || command.equals(commandStop) && this.message.equals("Usługa jest uruchomiona")) {
					channel = session.openChannel("exec");
					((ChannelExec)channel).setCommand(this.command);
					channel.setInputStream(null);
					((ChannelExec)channel).setErrStream(System.err);
					channel.connect();
					msgSys = new MessageSystemFilter(new BufferedReader(new InputStreamReader(channel.getInputStream())));
					
					this.message = msgSys.getbuffReadTextCommand();
					channel.disconnect();
				}
				//channel.disconnect();
				session.disconnect();
			}catch (JSchException e) {
				// TODO Auto-generated catch block
				this.message = "n";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.message = "n";
			}					
		return this.message;
	}
	
	@SuppressWarnings("deprecation")
	protected void onPostExecute(String response) {
		pDlg.dismiss();
		//if(listener != null) listener.onTaskCompleted(this.message);
		String a = this.message.toString();
		if(message.equals("n")){
			// Toast.makeText(context, "Unable to connect! Check the IP address machines or private key", Toast.LENGTH_LONG).show();
			alertDialog = new AlertDialog.Builder(context).create();
			alertDialog.setTitle("Informacja");
            alertDialog.setMessage("Problem z połączeniem! Sprawdz poprawność adresu IP lub zgodność klucza publicznego i prywatnego!");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                        }
            });
            // Showing Alert Message
            alertDialog.show();
		}else if((command.substring(0, 4).toString().equals(commandLog) && !command.substring(8,12).toString().equals("100 "))
				|| command.length() > 54){
			String msg = textViewLog.getText().toString();
			textViewLog.setText(msg + this.message);
		}else if(command.substring(0, 4).toString().equals(commandLog)){
			Intent intent = new Intent(context, Logs.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("logsText", this.message);
			intent.putExtra("ip", this.ip);
			Log.v("moje", "10");
			context.startActivity(intent);
		}else if(command.equals(commandLsConfig)){
			Intent intent = new Intent(context, Config.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("fileNames", this.message);
			intent.putExtra("ip", this.ip);
			Log.v("moje", "10");
			context.startActivity(intent);
		}else if(command.equals(commandLs)){
			Intent intent = new Intent(context, Database.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("fileNames", this.message);
			intent.putExtra("ip", this.ip);
			Log.v("moje", "10");
			context.startActivity(intent);
		}else if(command.equals(commandCatConfig)){
			Intent intent = new Intent(context, ConfigText.class);
			Log.v("moje", "4");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Log.v("moje", "5");
			intent.putExtra("configText", this.message);
			intent.putExtra("fileName", fileName);
			Log.v("moje", this.message);
			Log.v("moje", "6");
			context.startActivity(intent);
			Log.v("moje", "7");
		}else if(command.equals(commandCat)){
			Log.v("moje", "3");
			Intent intent = new Intent(context, DatabaseText.class);
			Log.v("moje", "4");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Log.v("moje", "5");
			intent.putExtra("databaseText", this.message);
			intent.putExtra("fileName", fileName);
			Log.v("moje", this.message);
			Log.v("moje", "6");
			context.startActivity(intent);
			Log.v("moje", "7");
		}else if(command.equals(commandTest) && !this.message.equals("")){
			//Toast.makeText(context, a, Toast.LENGTH_LONG).show();
			alertDialog = new AlertDialog.Builder(context).create();
			alertDialog.setTitle("Informacja");
            alertDialog.setMessage("Aplikacja jest gotowa do zarządzania usługą DNS");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                        	//statusTask = "true";
                        	//getStatusTask();
							Intent intent = new Intent(context, MainActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							intent.putExtra("ip", ip);
							context.startActivity(intent);
                        }
            });
            // Showing Alert Message
            alertDialog.show();
		}else{
			//Toast.makeText(context, a, Toast.LENGTH_LONG).show();
			alertDialog = new AlertDialog.Builder(context).create();
			alertDialog.setTitle("Informacja");
            alertDialog.setMessage(a);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                        }
            });
            // Showing Alert Message
            alertDialog.show();
		}
	}}
