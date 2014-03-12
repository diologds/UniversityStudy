package javaGuru.pack;

public class GameMechanics {

    static final int FIELD_SIZE = 3;
    static TableRows[][] table = new TableRows[FIELD_SIZE][FIELD_SIZE];
    int iterator = 0;
    int cordBaseValue = 50 , cordXFirst = 0 ,cordYFirst = 0 , cordXSecond = 0 ,cordYSecond = 0; 
    int middeleCordX =0 , middeleCordY = 0;
	

    public void printField()
    {
        for(int i = 0 ; i < FIELD_SIZE ; i++)
        {
            for(int j = 0 ; j < FIELD_SIZE ; j++)
            {
                System.out.print(table[i][j].getSymbol());
            }
            System.out.println();
        }
    }

    public void fillField()
    {
        for(int i = 0 ; i < FIELD_SIZE ; i++)
        {
            for(int j = 0 ; j < FIELD_SIZE ; j++)
            {
            	cordXFirst = cordBaseValue+(j*cordBaseValue);
            	cordYFirst = cordBaseValue*2+(i*cordBaseValue);
            	cordXSecond = cordBaseValue*2+(j*cordBaseValue);
            	cordYSecond = cordBaseValue+(i*cordBaseValue);
	
            	middeleCordX = (int)((cordXFirst + cordXSecond)/2);
            	middeleCordY = (int)((cordYFirst + cordYSecond)/2);
            	
                table[i][j]  = new TableRows( middeleCordX, middeleCordY , cordXFirst, cordYFirst,cordXSecond, cordYSecond );
            }
        }
    }

    public boolean makeMove(Player player , int cordX , int cordY)
    {
        char symbol = player.getPlayerSymbol();

        if (table[cordX][cordY].getSymbol() == '*')
        {
            table[cordX][cordY].setSymbol(symbol);
            return true;
        }else{
            return false;
        }
    }

    public boolean checkGameOver(Player player)
    {
        char symbol = player.getPlayerSymbol();

        if((table[0][0].getSymbol() == symbol) && (table[1][0].getSymbol() == symbol) && (table[2][0].getSymbol() == symbol))
        {
        	return true;
        }

        if((table[0][1].getSymbol() == symbol) && (table[1][1].getSymbol() == symbol) && (table[2][1].getSymbol() == symbol))
        {
        	return true;
        }

        if((table[0][1].getSymbol() == symbol) && (table[1][1].getSymbol() == symbol) && (table[2][1].getSymbol() == symbol))
        {
        	return true;
        }

        if((table[0][0].getSymbol() == symbol) && (table[0][1].getSymbol() == symbol) && (table[0][2].getSymbol() == symbol))
        {
        	return true;
        }

        if((table[1][0].getSymbol() == symbol) && (table[1][1].getSymbol() == symbol) && (table[1][2].getSymbol() == symbol))
        {
        	return true;
        }

        if((table[2][0].getSymbol() == symbol) && (table[2][1].getSymbol() == symbol) && (table[2][2].getSymbol() == symbol))
        {
        	return true;
        }

        if((table[0][0].getSymbol() == symbol) && (table[1][1].getSymbol() == symbol) && (table[2][2].getSymbol() == symbol))
        {
        	return true;
        }

        if((table[0][2].getSymbol() == symbol) && (table[1][1].getSymbol() == symbol) && (table[2][0].getSymbol() == symbol))
        {
        	return true;
        }
        
        for(int i = 0 ; i< 3 ; i++)
        {
        	for(int j = 0 ; j< 3 ; j++)
        	{
        		if(table[i][j].getSymbol() == '*')
        			return false;
        	}
        }

        return true;
    }

    public Player changePlayer(Player first,  Player second)
    {
        if((iterator % 2) == 1)
        {
            iterator++;
            return first;
        } else {
            iterator++;
            return second;
        }
    }

    public GameMechanics()
    {
        fillField();
        printField();
        iterator = 1;
    }

}