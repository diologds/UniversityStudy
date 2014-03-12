package javaGuru.pack;


import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class GameDrawingTable extends  JComponent{

		char symbol;
		int caunter = 0;
		int cordX , cordY;
	
        public GameDrawingTable(Dimension dimension) {
            setPreferredSize(dimension);
        }

        public void paintComponent(Graphics g) {
        	
            super.paintComponent(g);

            g.drawLine(100,50,100,200);
            g.drawLine(150,50,150,200);
            g.drawLine(50,100,200,100);
            g.drawLine(50,150,200,150);
            
            for(int i = 0; i < 3  ; i++)
    		{
    			for(int j = 0; j < 3  ; j++)
    			{
    				
    				try{
    					
    					cordX = GameMechanics.table[i][j].getCordX();
    					cordY = GameMechanics.table[i][j].getCordY();		
	    				symbol = GameMechanics.table[i][j].getSymbol();
    				
	    				
	    				if(symbol != '*')
	    				{
	    					if(symbol == 'X')
	    					{
	    						g.drawLine(cordX + 15,cordY - 15,cordX - 15, cordY + 15);
	    			            g.drawLine(cordX-15,cordY - 15,cordX+15,cordY + 15);
	    					}
	    					
	    					if(symbol == 'O')
	    					{
	    						g.drawOval(cordX-15, cordY - 15, 30, 30);
	    					}
	    				}
	    				
    				} catch(NullPointerException e){
    					System.out.println("Table eliments wasnt enicialized in time");
    				}
    				
    			}
    		}

        }

}
