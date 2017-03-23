package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.concurrent.TimeUnit;



public class HTMLFileWriter {
	String resource;
	BufferedReader socket_in;
	boolean ready;
	String line;
	BufferedWriter writer;
	boolean headerDone = false;
	private long contentLength = 0;
	private boolean fileSucces = false;
	
	
	public HTMLFileWriter(String URI, String resource, BufferedReader socket_in) throws IOException, InterruptedException{
		
		this.resource = resource;
		this.socket_in = socket_in;
		String path = "C:/Users/Beheerder/Desktop/ClientResources/"+resource;
		if (!path.endsWith(".html")){
			if (path.contains(".html")){
				int breakindex = path.indexOf(".html");
				path = path.substring(0, breakindex+5);	
				
			}
		}
		File file = new File(path);
		//String path = "/home/r0464173/Desktop/CNresources/"+resource+".html";
	    writer = new BufferedWriter(new OutputStreamWriter(
	          new FileOutputStream(path), "utf-8"));

	}
	
	public void createFile() throws IOException, InterruptedException{
	

	 while (true) {
		 if(!socket_in.ready()){
			 TimeUnit.SECONDS.sleep(1);
		 }
		 line = socket_in.readLine();

		 System.out.println(line);
		 
		   if(headerDone){
			   
			   //every line read should be written on a new line in the file
			   writer.write(line);
			   writer.newLine();
			   writer.flush();
			   contentLength += line.length();
		   }else{
			   if(line.contains("Content-Length:")){
				   String templine = line.replace("Content-Length: ", "");
				   contentLength = Long.valueOf(templine).longValue();
				   //System.out.println("length: "+contentLength);
			   }
			   if (line.length() == 0){
				   headerDone = true;
			   	 }
			   }
	       if (!socket_in.ready()){
	    	   if (contentLength != 0){
	    		   fileSucces = true;
	    	   }
	        	break;
	       }
	 	}
	}
	
	public boolean succes(){
		return this.fileSucces;
	}
	
}
