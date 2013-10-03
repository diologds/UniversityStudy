package javaguru.pack;

import javax.swing.*;

public class GameForm {

    public GameForm()  {

        JFrame frame = new JFrame("TicTakToe");
        frame.setSize(250, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel drawingPanel = new GraphicsForm();
        frame.add(drawingPanel);
        frame.setVisible(true);
    }
}

