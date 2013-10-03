package javaguru.pack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GraphicsForm extends  JPanel{

        public GraphicsForm() {
            setPreferredSize(new Dimension(300, 300));

            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {

                }

                public void mouseReleased(MouseEvent evt) {

                }
            });
            this.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent evt) {

                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawLine(100,50,100,200);
            g.drawLine(150,50,150,200);
            g.drawLine(50,100,200,100);
            g.drawLine(50,150,200,150);
        }
}
