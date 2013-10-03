package javaguru.pack;


import java.util.Scanner;

public class TicTakToe {


    public static void main(String []args)
    {
        GameForm form = new  GameForm();
        boolean gameStatus = false , moveStatus = false;
        Scanner scan = new Scanner(System.in);
        int cordX  , cordY ;

        Player firstPlayer = new Player("Teodor" , "Fo-Vintalevskis" ,'X');
        Player secondPlayer = new Player("Midora" , "Visauhova" ,'O');
        Player mainPlayer;
        GameMechanics mechanic = new GameMechanics();

        while (!gameStatus)
        {
            mainPlayer = mechanic.changePlayer(firstPlayer,secondPlayer);
            while (!moveStatus)
            {
                System.out.println("Input row and colum numbers ");
                cordX = scan.nextInt();
                cordY = scan.nextInt();
                moveStatus = mechanic.makeMove(mainPlayer , cordX , cordY);
            }

            mechanic.printField();
            gameStatus = mechanic.checkGameOver(mainPlayer);
            if(gameStatus)
                System.out.println( mainPlayer.getName() +" " + mainPlayer.getSurName()+"  Win");
            moveStatus = false;

        }

    }

}
