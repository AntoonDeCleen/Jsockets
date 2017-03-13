package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Timer;

public class CommandExecutor{
	Command command;
	Socket socket;
	PrintWriter socket_out;
    BufferedReader socket_in;
    int nullcount = 0;
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
			Scanner sc	= new Scanner(System.in);
			String resource = sc.nextLine();
			
			 socket_out.println("GET /"+resource+" HTTP/1.1\r\n\r\n");
			 
			 for (String line = socket_in.readLine(); nullcount != 2; line = socket_in.readLine()) {
				 if (nullcount == 2){
					 break;
				 }
				 if (line.length() == 0){
					 nullcount += 1;
				 }
			        System.out.println(line);
			 }
			 socket.close();
			 System.exit(0);
		;
		case HEAD: 			
			System.out.println("Resource to get: ");
			//Scanner sc	= new Scanner(System.in);
			//String resource = sc.nextLine();
			//System.out.println(resource);

			socket_out.println("HEAD / HTTP/1.1\r\n\r\n");

			for (String line = socket_in.readLine(); line.length() != 0; line = socket_in.readLine()) {
				System.out.println(line);
			}
			socket.close();
			System.exit(0);
		;
		case PUT:;
		case POST:;
		}
		
	}
}
