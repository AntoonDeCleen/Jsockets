package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;;


public class HTMLFileWriter {
	String resource;
	BufferedReader socket_in;
	boolean ready;
	String line;
	int nullcount;
	BufferedWriter writer;
	ArrayList<String> imagesToFetch = new ArrayList<String>();

	
	public HTMLFileWriter(String URI, String resource, BufferedReader socket_in) throws IOException{
		this.resource = resource;
		this.socket_in = socket_in;
		String path = "C:/Users/Beheerder/Desktop/ClientResources/"+"filesname"+".html";
		File file = new File(path);
		//String path = "/home/r0464173/Desktop/CNresources/"+resource+".html";
	    writer = new BufferedWriter(new OutputStreamWriter(
	          new FileOutputStream(path), "utf-8"));
	}
	
	public ArrayList<String> createFile() throws IOException{
	
	 line = socket_in.readLine();
	 for (line = socket_in.readLine(); nullcount != 1; line = socket_in.readLine()) {
		 //System.out.println(line);
		  // writer.write(line);	
		 	Document doc =  Jsoup.parse(line);
		 	Element image = doc.select("img").first();
		 
		 	if (image != null){
		 		imagesToFetch.add(image.attr("src").toString());
		 		System.out.println(image.attr("src").toString());
		 	}
	       if (!socket_in.ready()){
	        	break;
	       }
	 }
	 return imagesToFetch;
	}
	
	
}
