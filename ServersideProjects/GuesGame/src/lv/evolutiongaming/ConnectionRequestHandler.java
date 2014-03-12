package lv.evolutiongaming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionRequestHandler implements Runnable{
	private Socket _socket = null;
	private PrintWriter _out = null;
	private BufferedReader _in = null;
	
	final static String playerMessage = "player";
	final static String serverMessage = "server";
	final static String finalExitMessage = "exit game";

	public ConnectionRequestHandler(Socket socket) {
		_socket = socket;
	}

	public void run() {

		System.out.println("Client connected to socket: " + _socket.toString());

		try {
			
			_out = new PrintWriter(_socket.getOutputStream(), true);
			_in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));

			String startMenuDecision = null;
			GameLogic gameLogic = new GameLogic( _out, _in);

			_out.println("Connection established");
			
		do{
			_out.println("Please choose game type :  1. Player Guess (type : player);  2: Server Guess (type : server) ; 3: Exit game (type : exit game);");
			startMenuDecision = _in.readLine();
			_out.println("You choose :" + startMenuDecision);
			
			switch(startMenuDecision.toLowerCase()){
				case playerMessage: gameLogic.playerGuess();  break;
				case serverMessage: gameLogic.serverGuess(); break;
			}
			
		}while(!startMenuDecision.equals(finalExitMessage));		
				
			
			System.out.println("Player decided to close game!");
			System.out.println("Server is closing socket for client:" + _socket.getLocalSocketAddress());
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally { 
			try {
				_out.close();
				_in.close();
				_socket.close();
			} catch(Exception e) { 
				System.out.println("Couldn't close I/O streams");
			}
		}
	}

}