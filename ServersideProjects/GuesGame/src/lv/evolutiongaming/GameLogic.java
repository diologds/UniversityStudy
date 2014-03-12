package lv.evolutiongaming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GameLogic {
	
	final static String biggerMessage = "bigger";
	final static String smollerMessage = "smaller";
	final static String correctMessage = "correct";
	final static String exitMessage = "exit";
	final static String finalExitMessage = "exit game";
	final static int loseLimit = 4;
	
	private PrintWriter _out = null;
	private BufferedReader _in = null;
	private int guessLimitMax = 101;
	private int guessLimitMin = 0;
	private int guessNumber;
	
	String inputLine = null, outputLine = null; 
	
	GameLogic(PrintWriter _out , BufferedReader _in){
		this._out = _out;
		this._in= _in;
	}
	
	private int generateNumber(){
		Random random = new Random();
		return random.nextInt(100)+1;
	}
	
	public void playerGuess() {
		int loseCounter = 0;
		int guessNumber = generateNumber();
		_out.println("Number is generated ! You can start guess ;)");
		do{
			try {
				inputLine = _in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			switch(((Integer)Integer.parseInt(inputLine)).compareTo(guessNumber)){
				case -1: _out.println("Bigger"); break;
				case 0: _out.println("You Win :))) Great job");
					inputLine = exitMessage;
					break;
				case 1: _out.println("Smaller"); break;
			}
			
			loseCounter++;
			_out.println((loseCounter < loseLimit) ? "Next Round" : "You lose. Sorry . Server you Win  :)") ;
		}while(!inputLine.equals(exitMessage) && (loseCounter < loseLimit));
	}

	public void serverGuess() {
		int loseCounter = 0;
		do{
			guessNumber =(int)(((guessLimitMax - guessLimitMin)/2) + guessLimitMin); 
			
			_out.println("Computer guess :" + guessNumber);
			
			try {
				inputLine = _in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			switch(inputLine){
				case biggerMessage:
					guessLimitMin = guessNumber;
					if((guessLimitMax - guessLimitMin == 1 && guessLimitMax != 100) ||  guessNumber == 100){
						_out.println(" Cheater end of game ");
						inputLine = exitMessage;
					}
					break;
				case smollerMessage:
					guessLimitMax = guessNumber;
					if((guessLimitMax - guessLimitMin == 1 && guessLimitMin != 1) ||  guessNumber == 1){
						_out.println(" Cheater end of game ");
						inputLine = exitMessage;
					}
					break;
				case correctMessage:
					if(inputLine.equals(correctMessage)){
							_out.println("Server Win :)) ");
					}
					break;
			}
			
			loseCounter++;
			_out.println((loseCounter < loseLimit) ? "Next Round" : "You Win. :( Server you lose Sorry") ;
		}while((!inputLine.equals(exitMessage)&&!inputLine.equals(correctMessage)) && (loseCounter < loseLimit));
	}

}
