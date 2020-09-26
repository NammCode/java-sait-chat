package ex;
/**
 * EchoServer
 */


/**
 * @author dwatson, kitty
 * @version 1.1
 * Sep 5, 2012
 */

import java.net.*;
import java.util.ArrayList;

import java.io.*;

public class Server
{
	/**
	 * @param args
	 */
	private static ServerSocket listener = null;
	private static Socket client = null;
	private static ArrayList<Socket> socketList = null;
	
	public static void main( String[] args )
	{
		
		try {
			listener = new ServerSocket(5555);
			socketList = new ArrayList<Socket>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			try {
				client = listener.accept();
				socketList.add(client);
				if(socketList.size() == 2) {
					ClientHandler clientHandler = new ClientHandler(socketList);
					Thread th = new Thread(clientHandler);
					th.start();
					socketList.clear();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}
	
}
