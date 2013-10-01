package javaguru.pack;


public class GameMechanics {

    final int FIELD_SIZE = 3;
    char[][] field = new char[FIELD_SIZE][FIELD_SIZE];
    int iterator = 0;

    public void printField()
    {
        for(int i = 0 ; i < FIELD_SIZE ; i++)
        {
            for(int j = 0 ; j < FIELD_SIZE ; j++)
            {
                System.out.print(field[i][j]);
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
                field[i][j] = '+';
            }
        }
    }

    public boolean makeMove(Player player , int cordX , int cordY)
    {
        char symbol = player.getPlayerSymbol();

        if (field[cordX][cordY] == '+')
        {
            field[cordX][cordY] = symbol;
            return true;
        }else{
            return false;
        }
    }

    public boolean checkGameOver(Player player)
    {
        char symbol = player.getPlayerSymbol();
        boolean status = false;

        if((field[0][0] == symbol) && (field[1][0] == symbol) && (field[2][0] == symbol))
        {
            status = true;
        }

        if((field[0][1] == symbol) && (field[1][1] == symbol) && (field[2][1] == symbol))
        {
            status = true;
        }

        if((field[0][1] == symbol) && (field[1][1] == symbol) && (field[2][1] == symbol))
        {
            status = true;
        }

        if((field[0][0] == symbol) && (field[0][1] == symbol) && (field[0][2] == symbol))
        {
            status = true;
        }

        if((field[1][0] == symbol) && (field[1][1] == symbol) && (field[1][2] == symbol))
        {
            status = true;
        }

        if((field[2][0] == symbol) && (field[2][1] == symbol) && (field[2][2] == symbol))
        {
            status = true;
        }

        if((field[0][0] == symbol) && (field[1][1] == symbol) && (field[2][2] == symbol))
        {
            status = true;
        }

        if((field[0][2] == symbol) && (field[1][1] == symbol) && (field[2][0] == symbol))
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
