package evolutiongaming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShopingList {

	static Scanner scanner = new Scanner(System.in);
	static List<String> itemList = new ArrayList<String>();
	
	public static void main(String[] args) {
		int message = 0;	
		do{
			
			printMenu();
			
			System.out.print("Chouse menu option : " );
			
			try{
				message = Integer.parseInt(scanner.nextLine());
			} catch(NumberFormatException e ) {
				System.out.println("Incorrect menu Option");
			}
			
			switch(message){
				case 1: add();
					break;
				case 2: show();
					break;
				case 3: delete();
					break;
				case 4: save();
					break;
				case 5: load();
					break;
			}
			
		}while(message != 6);
	}
	
	private static void load() {
		try {
			  System.out.println("#######################################");
	          File file = new File("shopList.txt");
	          BufferedReader input = new BufferedReader(new FileReader(file));
	          itemList.removeAll(itemList);
	          String data;
	          while((data = input.readLine()) != null){
	        	  itemList.add(data);
	          }
	          input.close();
	        } catch ( IOException e ) {
	           e.printStackTrace();
	        }	
		System.out.println("List was succesfuly loaded :) ");
	}

	private static void save() {
		System.out.println("#######################################");
		if(!itemList.isEmpty()){
		try {
	          File file = new File("shopList.txt");
	          BufferedWriter output = new BufferedWriter(new FileWriter(file));
	          System.out.println(itemList.toString());
	          System.out.println(itemList.toString().substring(1, itemList.toString().length()-1));
	          String[] array = itemList.toString().substring(1, itemList.toString().length()-1).replace(",","").split(" ");
	          for(String data : array){
	        	  output.write(data+"\n");
	          }
	          output.flush();
	          output.close();
	        } catch ( IOException e ) {
	           e.printStackTrace();
	        }
		} else {
			System.out.println("Nothing to save ;(");
		}
	}

	private static void delete() {
		System.out.println("#######################################");
		if(!itemList.isEmpty()){
			System.out.println("Last eliment will be delited ");
			itemList.remove(itemList.size()-1);
			System.out.println("Item delited");	
		} else {
			System.out.println("Nothing to delete ;(");
		}
	}

	private static void show() {
		System.out.println("#######################################");
		if(!itemList.isEmpty()){
			System.out.println("Items in list : ");
			System.out.println(itemList.toString());
		} else{
			System.out.println("List is Empty");
		}
	}

	private static void add() {
		System.out.println("#######################################");
		System.out.println("Please input new item name : ");
		String data = scanner.nextLine();
		if(!itemList.contains(data)){
			itemList.add(data);
			System.out.println("Item added");
		} else {
			System.out.println("This item already present in the List ");
		}
	}

	private static void printMenu() {
		System.out.println("#######################################");
		System.out.println("1 - add an item");
		System.out.println("2 - list items");
		System.out.println("3 - delete an item");
		System.out.println("4 - save list to file");
		System.out.println("5 - load list from file");
		System.out.println("6 - exit");
		System.out.println("#######################################");
	}

}
