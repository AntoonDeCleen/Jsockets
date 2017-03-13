package main;

import client.Command;
import client.CommandExecutor;

public class Main {
	public static void main(String[] args ) throws Exception{
		int sockNumber = 80;
		if (args.length < 2){
			System.out.println("Invalid number of arguments");
			return;
		}
		
		if (args.length != 2){
			sockNumber = Integer.parseInt(args[2]);
		}
		
		Command command = Command.valueOf(args[0]);
		String URI = args[1];

		
		System.out.println("command: "+command);
		System.out.println("URI: "+ URI);
		System.out.println("portNumber: "+ sockNumber);

		CommandExecutor ce = new CommandExecutor(command, URI, sockNumber);

		

	}
}
