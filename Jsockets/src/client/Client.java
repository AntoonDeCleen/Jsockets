package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void Main(String args[]) throws Exception{
		
		Command command = Command.valueOf(args[0]);
		String URI = args[1];
		int sockNumber = Integer.parseInt(args[2]);
		


		Socket socket = new Socket(URI, sockNumber);

		

	}
}
