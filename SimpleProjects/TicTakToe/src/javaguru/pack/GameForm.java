package javaGuru.pack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GameForm {
	
	JFrame frame = new JFrame("TicTakToe");
    Dimension paintingField = new Dimension(250 , 300);
    GameDrawingTable table=  new GameDrawingTable(paintingField);
    int cordXFirst = 0 ,cordYFirst = 0 , cordXSecond = 0 ,cordYSecond = 0; 
    int oldCordX =0 , oldCordY = 0;
    boolean gameStatus = false , moveStatus = true;   
    
    public boolean checkBorders(int cordX , int cordY , char symbol) 
    {
    	boolean status = false;
    	System.out.println("1" + cordX + " " + cordY + " ");
    	for(int i = 0; i < 3  ; i++)
		{
    		System.out.println("2");
			for(int j = 0; j < 3  ; j++)
			{
				System.out.println("3");
				cordXFirst = GameMechanics.table[i][j].getfirstCorrectionPointCordX();
            	cordYFirst = GameMechanics.table[i][j].getfirstCorrectionPointCordY();
            	cordXSecond = GameMechanics.table[i][j].getsecondCorrectionPointCordX();
            	cordYSecond = GameMechanics.table[i][j].getsecondCorrectionPointCordY();
            	System.out.println(cordXFirst + " " + cordYFirst + " " + cordXSecond + " " + cordYSecond );
            	
            	if ((cordX > cordXFirst) && (cordX < cordXSecond) && (cordY < cordYFirst) && (cordY > cordYSecond))
            	{
            		System.out.println("4");
            		if(GameMechanics.table[i][j].getSymbol() == '*')
            		{
            			System.out.println("5");
            			status = TicTakToe.mechanic.makeMove(TicTakToe.mainPlayer , i , j);
            			GameMechanics.table[i][j].setSymbol(symbol);
            			System.out.println(status);
            		}
            		
            	}
            	
			}
		}
    	System.out.println("6");
    	return status; 
    }
    
    
    public GameForm()  {

        
        frame.setSize(200, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        table.setBackground(Color.WHITE);
        table.addMouseListener(new MouseAdapter()
        {
        	public void mouseClicked(MouseEvent e) 
        	{
        		if (!gameStatus)
        		{
        			if(moveStatus)
        				TicTakToe.mainPlayer = TicTakToe.mechanic.changePlayer(TicTakToe.firstPlayer,TicTakToe.secondPlayer);
	        	    

			        System.out.println("Make a move");
			        oldCordX = e.getX();
			        oldCordY = e.getY();
			        moveStatus = checkBorders(oldCordX , oldCordY , TicTakToe.mainPlayer.playerSymbol);
		        
			        if(moveStatus)
			        {
		        	    TicTakToe.mechanic.printField();
		        	    
		        	    gameStatus = TicTakToe.mechanic.checkGameOver(TicTakToe.mainPlayer);
		        	    
		        	    if(gameStatus)
		        	       System.out.println( TicTakToe.mainPlayer.getName() +" " + TicTakToe.mainPlayer.getSurName()+"  Win");
		        	    moveStatus = true;
		        	    
		        	    table.repaint();
	        		}
        		}
        	}
        });
        
        table.addMouseMotionListener(new MouseAdapter(){
        	
        	 public void mouseMoved(MouseEvent e) {
        		 //System.out.println(e.getX() + " " + e.getY());
        	    }
        	
        });
        

        frame.add(table);

        frame.setVisible(true);
    }
}
