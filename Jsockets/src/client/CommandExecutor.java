package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
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


			 socket_out.println("HEAD / HTTP/1.1");
			 socket_out.println("Host: "+URI);
			 socket_out.println("");

			line = socket_in.readLine();
			 for (line = socket_in.readLine(); nullcount != 1; line = socket_in.readLine()) {
			        System.out.println(line);
			        if (!socket_in.ready()){
			        	break;
			        }
			 }
			 socket.close();
			 System.exit(0);
			socket.close();
			System.exit(0);
		;
		case PUT:;
		case POST:
			
			path = "C:\\Users\\Beheerder\\Desktop\\ClientResources\\posttest.html";

			File postfile = new File(path);
			
			
							            
			
			
			 socket_out.println("POST /"+resource+" HTTP/1.1");
			 socket_out.println("Host: "+URI);
			 socket_out.println("Content-Length: " + postfile.length() + "\r\n");
			 socket_out.println("Content-Type: text/html");
			 socket_out.println("Date: "+new Date().toString());
			 
			 
			 FileInputStream fin = new FileInputStream(postfile);
			 
			 PrintWriter pw =new PrintWriter(socket.getOutputStream(), true);
			 BufferedReader reader = new BufferedReader(new InputStreamReader(fin, "utf-8"));
			 long sentLength = 1;
			 long totalLength = postfile.length();
			 String fileLine;
			 
		 
		    BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    String line;
		    while ((line = rd.readLine()) != null) {
		      System.out.println(line);
		    }
		 
			 while(sentLength < totalLength){
				 
				 fileLine = reader.readLine();
				 
				 if (fileLine == null){
					 System.out.println("ending filetransfer");
					 pw.println("\r\n");
					 pw.close();
					 socket.close();
					 return;
				 }
				 
				 System.out.println("filelinelength: "+fileLine.length());
				 sentLength += fileLine.length();
				 //System.out.println(sentLength);
				 //System.out.println(fileLine);
				 pw.println(fileLine);
				 pw.flush();
			 
			 }
			 
			 
			 
			 

		    pw.close();
		    rd.close();
			
			;
		}
		
	}
}
