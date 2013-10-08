package javaguru.pack;

import javax.swing.*;
import java.awt.*;

public class GameRows extends  JComponent{

        public GameRows(Dimension dimension) {
            setPreferredSize(dimension);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //Draw rows
            g.drawLine(100,50,100,200);
            g.drawLine(150,50,150,200);
            g.drawLine(50,100,200,100);
            g.drawLine(50,150,200,150);

        }

}
