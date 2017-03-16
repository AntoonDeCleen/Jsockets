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
    int nullcount = 0;
    String line;
    FileCreator filecreator;
    
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
			
			
			
			System.out.println("Resource to get: ");
			Scanner sc	= new Scanner(System.in);
			
			String resource;
			resource = sc.nextLine();
			//resource = "index.html";
			//resource = "index.html?gfe_rd=cr&amp;ei=OuXHWLyLGoTc8AfJuoXABg";
			 socket_out.println("GET /"+resource+" HTTP/1.1");
			 socket_out.println("Host: "+URI);
			 socket_out.println("");
			 
			 filecreator = new FileCreator(URI, resource, socket_in);
//			 line = socket_in.readLine();
//			 for (line = socket_in.readLine(); nullcount != 1; line = socket_in.readLine()) {
//			        System.out.println(line);
//			        if (!socket_in.ready()){
//			        	break;
//			        }
//			 }

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
