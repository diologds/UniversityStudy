package javaguru.pack;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class gameMain {

    static final int NUMBER_MIN = 0;
    static final int NUMBER_MAX = 100;
    static final int NUMBER_INIT = 100;
    JFrame frame =  new JFrame("Guess Number The Game");
    JButton comp = new JButton("Computer Guess");
    JButton player = new JButton("Player Guess");
    JButton answerYes = new JButton("Correct Guess");
    JButton answerBigger = new JButton("Bigger");
    JButton answerLower = new JButton("Lower");
    JButton submitNumber = new JButton("Submit");
    JLabel labeledNumber = new JLabel("");
    JSlider playerGuess = new JSlider(JSlider.HORIZONTAL, NUMBER_MIN, NUMBER_MAX, NUMBER_INIT);
    int status = -1,answer= -1,guessNumber = 100,lLimit = 0,hLimit = 100,randomNumber = 0,playerGuessResult = 0;
    int guessCounter = 0;


    public void createFrame()
    {

        frame.setSize(300,150);
        frame.setResizable(false);
        frame.setLayout( new FlowLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Dimension panelD = new Dimension(300,60);

        playerGuess.setPreferredSize(panelD);
        playerGuess.setMajorTickSpacing(10);
        playerGuess.setMinorTickSpacing(1);
        playerGuess.setPaintTicks(true);
        playerGuess.setPaintLabels(true);
        labeledNumber.setFont(new Font("Serif", Font.PLAIN, 48));

        answerYes.setVisible(false);
        answerBigger.setVisible(false);
        answerLower.setVisible(false);
        playerGuess.setVisible(false);
        labeledNumber.setVisible(false);
        submitNumber.setVisible(false);

        comp.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                status = 1;
                labeledNumber.setText(String.valueOf(guessNumber));
                comp.setVisible(false);
                player.setVisible(false);
                answerYes.setVisible(true);
                answerBigger.setVisible(true);
                answerLower.setVisible(true);
                labeledNumber.setVisible(true);
            }

        });


        player.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                status = 2;
                frame.setSize(400,150);
                comp.setVisible(false);
                player.setVisible(false);
                playerGuess.setVisible(true);
                submitNumber.setVisible(true);
                labeledNumber.setVisible(true);
                randomNumber = generateRandomNumber();
            }

        });

        submitNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            playerGuessResult = playerGuessCheck(playerGuess.getValue());
            if (playerGuessResult == 1)
            {
                comp.setVisible(true);
                player.setVisible(true);
                playerGuess.setVisible(false);
                submitNumber.setVisible(false);
                labeledNumber.setVisible(true);
            }
            }
        });

        answerYes.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                answer = 0;
                if (status == 1)
                {
                    labeledNumber.setText(String.valueOf(compGuess(answer)));
                    setDefaultFormConfiguration();
                    gameDefaultConfig();
                    labeledNumber.setText("Computer Wins");
                }
            }

        });

        answerBigger.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                answer = 1;
                if (status == 1)
                {
                    if (guessCounter != 6)
                    {
                        labeledNumber.setText(String.valueOf(compGuess(answer)));
                    } else {
                        setDefaultFormConfiguration();
                        gameDefaultConfig();
                        labeledNumber.setText("You Win");
                    }
                }
            }

        });

        answerLower.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                answer = 2;
                if (status == 1)
                {
                    if (guessCounter != 6)
                    {
                        labeledNumber.setText(String.valueOf(compGuess(answer)));
                    } else {
                        setDefaultFormConfiguration();
                        gameDefaultConfig();
                        labeledNumber.setText("You Win");
                    }
                }
            }

        });

        frame.add(comp);
        frame.add(player);
        frame.add(answerYes);
        frame.add(answerBigger);
        frame.add(answerLower);
        frame.add(playerGuess);
        frame.add(submitNumber);
        frame.add(labeledNumber);
        frame.setVisible(true);

    }

    public int compGuess(int answer)
    {

        guessCounter++;

        if (answer == 0)
        {
            guessNumber = -1;
        }

        if (answer == 1)
        {
            lLimit = guessNumber;
            guessNumber = Math.round((hLimit-lLimit)/2 + lLimit);
        }

        if (answer == 2)
        {
            hLimit = guessNumber;
            guessNumber = Math.round((hLimit-lLimit)/2 + lLimit);
        }

        return guessNumber;
    }

    public int playerGuessCheck(int guess)
    {
        int res = 0;
        if (guessCounter != 6)
        {
            guessCounter++;
            if (guess == randomNumber)
            {
                res = 1;
                labeledNumber.setText("Correct , you win");
            }

            if (guess < randomNumber)
            {
                labeledNumber.setText("Bigger");
            }

            if (guess > randomNumber)
            {
                labeledNumber.setText("Lower");
            }
            return res;
        } else {
            labeledNumber.setText("You Lost");
            gameDefaultConfig();
            setDefaultFormConfiguration();
            res = -1;
        }

        return res;
    }

    public void gameDefaultConfig()
    {
        status = -1;
        answer= -1;
        guessNumber = 100;
        lLimit = 0;
        hLimit = 100;
        guessCounter = 0;
    }

    public int generateRandomNumber()
    {
        Random rand = new Random();
        return (rand.nextInt(99) + 1 );
    }

    public void setDefaultFormConfiguration()
    {
        comp.setVisible(true);
        player.setVisible(true);
        answerYes.setVisible(false);
        answerBigger.setVisible(false);
        answerLower.setVisible(false);
        playerGuess.setVisible(false);
        submitNumber.setVisible(false);
    }

    public static void main(String[] args) {

        gameMain guess = new gameMain();
        guess.setDefaultFormConfiguration();
        guess.gameDefaultConfig();
        guess.createFrame();

    }

}
