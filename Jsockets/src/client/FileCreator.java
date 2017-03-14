package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class FileCreator {
	String resource;
	BufferedReader socket_in;
	boolean ready;
	String line;
	int nullcount;
	BufferedWriter writer;
	
	public FileCreator(String URI, String resource, BufferedReader socket_in) throws IOException{
		this.resource = resource;
		this.socket_in = socket_in;
		
		String path = "/home/r0464173/Desktop/CNresources/"+resource+".html";
	    writer = new BufferedWriter(new OutputStreamWriter(
	            new FileOutputStream(path), "utf-8"));
	    createFile();
	}
	
	public void createFile() throws IOException{
	
	 line = socket_in.readLine();
	 for (line = socket_in.readLine(); nullcount != 1; line = socket_in.readLine()) {
		 	System.out.println(line);
		   writer.write(line);	
		   writer.flush();
	       if (!socket_in.ready()){
	        	break;
	       }
	 }
	 ready = true;
	 System.exit(0);
	}
	
	
	
	public boolean ready(){
		return this.ready;
	}
	
}
