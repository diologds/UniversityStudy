package com.evolutiongaming;

public class Reverse{
	
	public static String reverse(String str) {
	    if ((null == str) || (str.length()  <= 1)) {
	        return str;
	    }
	    return reverse(str.substring(1)) + str.charAt(0);
	}

	public static void main(String[] args) {
		String forReverse = "hello";
	    System.out.println("Reverting : " +  forReverse);
	    System.out.println("result: " + reverse(forReverse));
	}
	
}

;