package com.example.dns;

import java.io.BufferedReader;
import java.io.IOException;

import android.util.Log;

public class MessageSystemFilter {
	
	private String message 		= "";
	public String bindStaring 	= " * Starting";
	public String bindStopping 	= " * Stopping";
	private String bindStart 	= " * bind9 is running";
	private String bindStop 	= " * bind9 is not running";
	
	private BufferedReader br;
	public MessageSystemFilter(BufferedReader br) {
		// TODO Auto-generated constructor stub
		//this.command = command;
		//this.message = message;
		this.br = br;
	}
	private String buffReadTextCat(BufferedReader br) throws IOException{
		String line=null;  
		while((line = br.readLine()) != null){
			if(!line.equals("   ...done.")){
				this.message = this.message + "\n" + line;
			}
		}
		return this.message;
	}
	private String buffReadTextLog(BufferedReader br) throws IOException{
		String line=null;  
		Log.v("moje", "4");
		while((line = br.readLine()) != null){
			if(!line.equals("   ...done.")){
				this.message = line + "\n\n" + this.message;
			}
		}
		Log.v("moje", "5");
		return this.message;
	}
	private String buffReadTextFileName(BufferedReader br) throws IOException{
		String line=null;  
		while((line = br.readLine()) != null){
			if(!line.equals("   ...done.")){
				this.message = this.message + " " + line;
			}
		}
		return this.message;
	}
	private String buffReadTextCommand(BufferedReader br) throws IOException{
		String line=null;  
		while((line = br.readLine()) != null){
			if(!line.equals("   ...done.")){
				this.message = this.message + line;
			}
		}
		if(this.message.substring(0,11).equals(bindStaring)){
			return "Usługa została uruchomiona";
		}else if(this.message.substring(0,11).equals(bindStopping)){
			return "Usługa została zatrzymana";
		}else if(this.message.substring(0,19).equals(bindStart)){
			return "Usługa jest uruchomiona";
		}else if(this.message.substring(0,23).equals(bindStop)){
			return "Usługa nie jest uruchomiona";
		}
		return this.message;
		
	}

	public String getbuffReadTextLog() throws IOException{
		return buffReadTextLog(this.br);
	}
	public String getbuffReadTextCat() throws IOException{
		return buffReadTextCat(this.br);
	}
	public String getbuffReadTextCommand() throws IOException{
		return buffReadTextCommand(this.br);
	}
	public String getFileName() throws IOException{
		return buffReadTextFileName(this.br);
	}
}
