package dark.paster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

public class Window extends JFrame implements MouseListener {

    private Canvas canvas;
    Graphics graphics;
    public Window(){
        canvas = new Canvas();
        JFrame frame = new JFrame("NN");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation(700, 400);
        Dimension dimension = new Dimension();
        dimension.setSize(600, 600);
        canvas.setSize(dimension);
        canvas.setBackground(Color.BLACK);
        frame.setSize(dimension);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
        frame.pack();

        frame.addMouseListener(this);
        canvas.addMouseListener(this);

        graphics = canvas.getGraphics();
        graphics.setColor(Color.RED);
        graphics.create();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        graphics.fillRect(e.getX(), e.getY(), 2, 2);
        System.out.println("1");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("2");

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println("3");

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("4");

    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("5");

    }

}
