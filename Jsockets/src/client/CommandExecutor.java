package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;



public class CommandExecutor{
	Command command;
	Socket socket;
	PrintWriter socket_out;
    BufferedReader socket_in;
    int nullcount = 0;
    String line;
    HTMLFileWriter filecreator;
    String path;
    String resource = null;
    Scanner sc = new Scanner(System.in);
    
    
	public CommandExecutor(Command command, String URI, int sockNumber) throws IOException, InterruptedException{
		this.command = command;
		if (URI.contains("/")){
			int splitindex = URI.indexOf("/");
			resource = URI.substring(splitindex, URI.length());
			URI = URI.substring(0, splitindex);
			System.out.println("new URI: "+ URI);
			System.out.println("resource found: "+resource );
		}
		try {
			this.socket = new Socket(URI, sockNumber);
			//writer for socket
            socket_out = new PrintWriter( socket.getOutputStream(), true);
            //reader for socket
            socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.out.println("Unknown host");
			System.exit(0);
		}
		
		switch(command){
		case GET:
		
			if (resource == null){
				resource = "index.html";
			}
			//resource = "index.html?gfe_rd=cr&amp;ei=OuXHWLyLGoTc8AfJuoXABg";
			 socket_out.println("GET /"+resource+" HTTP/1.1");
			 socket_out.println("Host: "+URI);
			 socket_out.println("");
			
			 
			 filecreator = new HTMLFileWriter(URI, resource, socket_in);
			 filecreator.createFile();
			 while (!filecreator.succes()){
				 System.out.println("Request failed, retrying in 1 second");
				 TimeUnit.SECONDS.sleep(1);
				 socket_out.println("GET /"+resource+" HTTP/1.1");
				 socket_out.println("Host: "+URI);
				 socket_out.println("");
				 filecreator.createFile();
			 }
			 System.exit(0);

		;
		case HEAD: 			
			if (resource == null){
				resource = "index.html";
			}
			
			 socket_out.println("HEAD /"+resource+" HTTP/1.1");
			 socket_out.println("Host: "+URI);
			 socket_out.println("");

			 
			TimeUnit.SECONDS.sleep(1);
			line = socket_in.readLine();
			while(socket_in.ready()) {
				System.out.println(line);
			    	line = socket_in.readLine();
			        if (!socket_in.ready()){
			        	break;
			        }
			 }
			System.out.println("ending");
			 socket.close();
			 System.exit(0);
		;
		case PUT:			
			System.out.println("Name of the putkey: ");
			
			String putkey1 = sc.nextLine();
			System.out.println("Name of the putvalue: ");
			String putval1 = sc.nextLine();
			if(resource == null){
				resource = "";
			}
			
			String putdata = URLEncoder.encode(putkey1, "UTF-8") + "=" + URLEncoder.encode(putval1, "UTF-8");
			TimeUnit.SECONDS.sleep(1);
			//Sending request
			System.out.println("Putting to URI: "+URI);
			System.out.println("At location: "+resource);
			 socket_out.println("POST /"+resource+" HTTP/1.1");
			 socket_out.println("Host: "+URI);
			 socket_out.println("Content-Type: application/x-www-form-urlencoded");
			 socket_out.println("Date: "+new Date().toString());
			 socket_out.println("Content-Length: " + putdata.length());
			 socket_out.println("\r\n");
			 socket_out.println(putdata);
			 socket_out.flush();
			
			
			 //reading server response
			String line;
		    System.out.println(socket_in.ready());
		    line = socket_in.readLine();
		    System.out.println(line);
		    while (socket_in.ready()) {
		      line = socket_in.readLine();
		      System.out.println(line);
		      if (!socket_in.ready()){
		    	  break;
		      }
		    }
		    
		    socket_out.close();
		    socket_in.close();
			socket.close();
			
			;
		case POST:
			
			System.out.println("Name of the postkey: ");
			String postkey1 = sc.nextLine();
			System.out.println("Name of the postvalue: ");
			String postval1 = sc.nextLine();
			if(resource == null){
				resource = "";
			}
			
			String postdata = URLEncoder.encode(postkey1, "UTF-8") + "=" + URLEncoder.encode(postval1, "UTF-8");
			TimeUnit.SECONDS.sleep(1);
			//Sending request
			System.out.println("Posting to URI: "+URI);
			System.out.println("At location: "+resource);
			 socket_out.println("POST /"+resource+" HTTP/1.1");
			 socket_out.println("Host: "+URI);
			 socket_out.println("Content-Type: application/x-www-form-urlencoded");
			 socket_out.println("Date: "+new Date().toString());
			 socket_out.println("Content-Length: " + postdata.length());
			 socket_out.println("\r\n");
			 socket_out.println(postdata);
			 socket_out.flush();
			
			
			 //reading server response
		    
		    System.out.println(socket_in.ready());
		    line = socket_in.readLine();
		    System.out.println(line);
		    while (socket_in.ready()) {
		      line = socket_in.readLine();
		      System.out.println(line);
		      if (!socket_in.ready()){
		    	  break;
		      }
		    }
		    
		    socket_out.close();
		    socket_in.close();
			socket.close();
			;
		}
		
	}
}
