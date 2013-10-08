package javaguru.pack;

import javax.swing.*;
import java.awt.*;

public class GameForm {

    public GameForm()  {

        JFrame frame = new JFrame("TicTakToe");
        frame.setSize(250, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension paintingField = new Dimension(200 , 300);

        GameRows rows=  new GameRows(paintingField);
        JPanel drawingPanel = new JPanel();

        frame.add(rows);
        frame.add(drawingPanel);
        frame.setVisible(true);
    }
}

