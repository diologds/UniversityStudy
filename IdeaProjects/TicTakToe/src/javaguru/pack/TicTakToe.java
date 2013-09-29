package javaguru.pack;


public class TicTakToe {

    final int FIELD_SIZE = 3;
    char[][] field = new char[FIELD_SIZE][FIELD_SIZE];

    public void printField()
    {
        for(int i = 0 ; i < FIELD_SIZE ; i++)
        {
            for(int j = 0 ; j < FIELD_SIZE ; j++)
            {
                System.out.println(field[i][j]);
            }
        }
    }

    public void fillField()
    {
        for(int i = 0 ; i < FIELD_SIZE ; i++)
        {
            for(int j = 0 ; j < FIELD_SIZE ; j++)
            {
                field[i][j] = ' ';
            }
        }
    }

    public boolean makeMove(Player player , int cordX , int cordY)
    {
        char symbol = player.getPlayerSymbol();

        if (field[cordX][cordY] == ' ')
        {
            field[cordX][cordY] = symbol;
            return true;
        }else{
            return false;
        }
    }

    public static void main(String []args)
    {

    }

}
