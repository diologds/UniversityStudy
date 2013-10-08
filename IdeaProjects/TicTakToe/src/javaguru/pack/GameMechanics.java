package javaguru.pack;


public class GameMechanics {

    final int FIELD_SIZE = 3;
    //char[][] field = new char[FIELD_SIZE][FIELD_SIZE];
    TableRows[][] table = new TableRows[FIELD_SIZE][FIELD_SIZE];
    int iterator = 0;

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
                table[i][j]  = new TableRows( 0, 0);
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
        boolean status = false;

        if((table[0][0].getSymbol() == symbol) && (table[1][0].getSymbol() == symbol) && (table[2][0].getSymbol() == symbol))
        {
            status = true;
        }

        if((table[0][1].getSymbol() == symbol) && (table[1][1].getSymbol() == symbol) && (table[2][1].getSymbol() == symbol))
        {
            status = true;
        }

        if((table[0][1].getSymbol() == symbol) && (table[1][1].getSymbol() == symbol) && (table[2][1].getSymbol() == symbol))
        {
            status = true;
        }

        if((table[0][0].getSymbol() == symbol) && (table[0][1].getSymbol() == symbol) && (table[0][2].getSymbol() == symbol))
        {
            status = true;
        }

        if((table[1][0].getSymbol() == symbol) && (table[1][1].getSymbol() == symbol) && (table[1][2].getSymbol() == symbol))
        {
            status = true;
        }

        if((table[2][0].getSymbol() == symbol) && (table[2][1].getSymbol() == symbol) && (table[2][2].getSymbol() == symbol))
        {
            status = true;
        }

        if((table[0][0].getSymbol() == symbol) && (table[1][1].getSymbol() == symbol) && (table[2][2].getSymbol() == symbol))
        {
            status = true;
        }

        if((table[0][2].getSymbol() == symbol) && (table[1][1].getSymbol() == symbol) && (table[2][0].getSymbol() == symbol))
        {
            status = true;
        }

        return status;
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
