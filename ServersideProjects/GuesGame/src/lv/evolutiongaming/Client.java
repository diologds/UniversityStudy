package lv.evolutiongaming;

import java.io.*;
import java.net.*;

/*
	Received string: 'Connection established
	Please choose game type :  1. Player Guess (type : player);  2: Server Guess (type : server) ; 3: Exit game (type : exit game);
	player
	You choose :player
	Number is generated ! You can start guess ;)
	50
	Bigger
	Next Round
	90
	Smaller
	Next Round
	60
	Bigger
	Next Round
	80
	Bigger
	You lose. Sorry . Server you Win  :)
	Please choose game type :  1. Player Guess (type : player);  2: Server Guess (type : server) ; 3: Exit game (type : exit game);
	server
	You choose :server
	Computer guess :50
	smaller
	Next Round
	Computer guess :25
	smaller
	Next Round
	Computer guess :12
	bigger
	Next Round
	Computer guess :18
	bigger
	You Win. :( Server you lose Sorry
	Please choose game type :  1. Player Guess (type : player);  2: Server Guess (type : server) ; 3: Exit game (type : exit game);
	exit
	You choose :exit
	Please choose game type :  1. Player Guess (type : player);  2: Server Guess (type : server) ; 3: Exit game (type : exit game);
	exit game
	You choose :exit game
*/

class Client {
	
	final static String exitMessage = "exit game";
	
	public static void main(String args[]) {
		try {
			
			final Socket _socket = new Socket("localhost", 8000);
			final PrintWriter _out = new PrintWriter(_socket.getOutputStream(), true);
			final BufferedReader _in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("Received string: '");
			String  outputMessage = null;
			
			Thread t = new Thread(){
				public void run(){
					while(!_socket.isClosed()){
						try {
							System.out.println(_in.readLine());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			};
			
			t.start();
			
			do{
				try{
		        	_out.println(outputMessage=bufferRead.readLine());
		        }catch(IOException e){
		        	e.printStackTrace();
		        }
			}while(!outputMessage.equals(exitMessage) );
			
			_in.close();
			_out.close();
			_socket.close();
		}catch(Exception e) {
			System.out.print("Whoops! It didn't work!\n");
		}
	}
}