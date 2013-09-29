package javaGuru.tsi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameNumberGuess {

    static final int NUMBER_MIN = 0;
    static final int NUMBER_MAX = 100;
    static final int NUMBER_INIT = 100;
    JFrame frame =  new JFrame("Guess Number The Game");
    JButton comp = new JButton("Computer Guess");
    JButton player = new JButton("Player Guess");
    JButton answerYes = new JButton("Correct Guess");
    JButton answerBigger = new JButton("Bigger");
    JButton answerLower = new JButton("Lower");
    JLabel lable = new JLabel("");
    JSlider playerGuess = new JSlider(JSlider.HORIZONTAL, NUMBER_MIN, NUMBER_MAX, NUMBER_INIT);
    JPanel imagePanel = new JPanel();
    int status = -1 ,answer= -1 , guesNumber = 100 , lLimit = 0 , hLimit = 100;


    public void createFrame()
    {

        frame.setSize(350,300);
        frame.setResizable(false);
        frame.setLayout( new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension panelD = new Dimension(300,60);

        playerGuess.setPreferredSize(panelD);
        playerGuess.setMajorTickSpacing(10);
        playerGuess.setMinorTickSpacing(1);
        playerGuess.setPaintTicks(true);
        playerGuess.setPaintLabels(true);
        lable.setFont(new Font("Serif", Font.PLAIN, 48));

        comp.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                status = 1;
                lable.setText(String.valueOf(guesNumber));
            }

        });


        player.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                status = 2;
            }

        });

        answerYes.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                answer = 0;
                if (status == 1)
                {
                    lable.setText(String.valueOf(compGuess(answer)));

                }
            }

        });

        answerBigger.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                answer = 1;
                if (status == 1)
                {
                    lable.setText(String.valueOf(compGuess(answer)));
                }
            }

        });

        answerLower.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                answer = 2;
                if (status == 1)
                {
                    lable.setText(String.valueOf(compGuess(answer)));
                }
            }

        });

        frame.add(comp);
        frame.add(player);
        frame.add(answerYes);
        frame.add(answerBigger);
        frame.add(answerLower);
        frame.add(playerGuess);
        frame.add(imagePanel);
        frame.add(lable);

        frame.setVisible(true);

    }

    public int compGuess(int answer)
    {
        if (answer == 0)
        {
            guesNumber = -1;
        }

        if (answer == 1)
        {
            lLimit = guesNumber;
            guesNumber = Math.round((hLimit-lLimit)/2 + lLimit);
        }

        if (answer == 2)
        {
            hLimit = guesNumber;
            guesNumber = Math.round((hLimit-lLimit)/2 + lLimit);
        }

        return guesNumber;
    }

    public void playerGuess()
    {

    }

    public static void main(String[] args) {

        GameNumberGuess guess = new GameNumberGuess();
        guess.createFrame();

    }

}
