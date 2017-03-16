package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Scanner;


public class CommandExecutor{
	Command command;
	Socket socket;
	PrintWriter socket_out;
    BufferedReader socket_in;
    int nullcount = 0;
    String line;
    HTMLFileWriter filecreator;
    String path;
    
	public CommandExecutor(Command command, String URI, int sockNumber) throws IOException, InterruptedException{
		this.command = command;
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
			
			
			
			//System.out.println("Resource to get: ");
			//Scanner sc	= new Scanner(System.in);
			
			String resource;
			//resource = sc.nextLine();
			//resource = "index.html";
			resource = "index.html?gfe_rd=cr&amp;ei=OuXHWLyLGoTc8AfJuoXABg";
			 socket_out.println("GET /"+resource+" HTTP/1.1");
			 socket_out.println("Host: "+URI);
			 socket_out.println("");
			 
			 filecreator = new HTMLFileWriter(URI, resource, socket_in);
			 ArrayList<String> imagesToGet = filecreator.createFile();
			 System.out.println(imagesToGet.size());
			 for (String s:imagesToGet){
				 socket_out.println("GET /"+s+" HTTP/1.1");
				 socket_out.println("Host: "+URI);
				 socket_out.println("");
				 path = "C:\\Users\\Beheerder\\Desktop\\ClientResources\\test.png";
				 File f = new File(path);
				 FileOutputStream foutStream = new FileOutputStream(path);
				 byte[] mybytearray = new byte[1024];

				 InputStream in = socket.getInputStream();
//	            int count;
//	            
//	            InputStream is = socket.getInputStream();
//	            try{
//	            while ((count = is.read(mybytearray)) > 0) {
//	                foutStream.write(mybytearray, 0, mybytearray.length);
//	                System.out.println(mybytearray);
//	                System.out.println(count);
//	            }
//	            }finally{
//	            	System.out.println("finally");
//	            	foutStream.close();
//	            	socket.close();
//	            }
	            
	            int count;
	            byte[] buffer = new byte[8192]; // or 4096, or more
	            while ((count = in.read(buffer)) > 0)
	            {
	            	
	              .write(buffer, 0, count);
	            }
	            finStream.close();
	            in.close();
	            socket.close();
	
				// foutStream.write(socket_in.read());
				 System.out.println("x");
				// foutStream.flush();
			 }
			 System.out.println("x2");
			 System.exit(0);

		;
		case HEAD: 			
			System.out.println("Resource to get: ");
			//Scanner sc	= new Scanner(System.in);
			//String resource = sc.nextLine();
			//System.out.println(resource);

			 socket_out.println("GET / HTTP/1.1");
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
		case POST:;
		}
		
	}
}
