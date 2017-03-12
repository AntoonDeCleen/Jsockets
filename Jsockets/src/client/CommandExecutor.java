package client;

import java.net.Socket;

public class CommandExecutor {
	Command command;
	Socket socket;
	public CommandExecutor(Command command, String URI, Socket socket){
		this.command = command;
		this.socket = socket;
		
		switch(command){
		case GET: ;
		case HEAD: ;
		case PUT:;
		case POST:;
		}
		
	}
}
