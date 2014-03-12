package evolutiongaming;

import java.util.Scanner;

public class Symmetrical {

	/*
	  	If you want to Exit please type 'exit' !
		Please input word : 
		asd
		Word is Symmetrical : false
		If you want to Exit please type 'exit' !u
		Please input word : 
		dsd
		Word is Symmetrical : true
		If you want to Exit please type 'exit' !
		Please input word : 
		assa
		Word is Symmetrical : true
		If you want to Exit please type 'exit' !
		Please input word : 
		assd
		Word is Symmetrical : false
		If you want to Exit please type 'exit' !
		Please input word : 
		exit
	 */
	
	private final static String exit = "exit";
	
	
	public static String reverse(String str) {
	    if ((null == str) || (str.length()  <= 1)) {
	        return str;
	    }
	    return reverse(str.substring(1)) + str.charAt(0);
	}
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String line;
		do{
			System.out.println("If you want to Exit please type 'exit' !");
			System.out.println("Please input word : ");
			line = scanner.nextLine();
			if(!line.equals(exit)){
				int prefix = (line.length()%2==0)? 0 : 1;
				String firstPart = line.substring(0 ,line.length()/2);
				String secondPart = reverse(line.substring(line.length()/2+prefix , line.length()));
				System.out.println("Word is Symmetrical : " + firstPart.equals(secondPart));
			}
		}while(!line.equals(exit));
		scanner.close();
	}
}
