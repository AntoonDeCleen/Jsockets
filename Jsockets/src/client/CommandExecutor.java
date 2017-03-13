package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class CommandExecutor{
	Command command;
	Socket socket;
	PrintWriter socket_out;
    BufferedReader socket_in;
	public CommandExecutor(Command command, String URI, int sockNumber) throws IOException{
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
			System.out.println("Resource to get: ");
			//Scanner sc	= new Scanner(System.in);
			//String resource = sc.nextLine();
			//System.out.println(resource);
			
			 String message = "GET / HTTP/1.1\r\n\r\n";
			 socket_out.println(message);
			 
//			 String response;
//			    while (socket_in.readLine() != null) 
//			    {
//			        System.out.println(socket_in.readLine());
//			    }
			    
			 for (String line = socket_in.readLine(); line != null; line = socket_in.readLine()) {
			        System.out.println(line);
			 }
			 System.exit(0);
		;
		case HEAD: ;
		case PUT:;
		case POST:;
		}
		
	}
}
