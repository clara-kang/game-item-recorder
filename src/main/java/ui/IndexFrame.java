package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IndexFrame extends Frame {

    private JPanel controlPanel;

    public IndexFrame(){
        super("Java AWT Examples");
        prepareGUI();
    }

    public static void main(String[] args){
        IndexFrame awtGraphicsDemo = new IndexFrame();
        awtGraphicsDemo.add(new IndexPanel(awtGraphicsDemo));
        awtGraphicsDemo.setVisible(true);
    }

    private void prepareGUI(){
        setSize(400,400);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        ImageIcon img = new ImageIcon(getClass().getResource("icon.png"));
        setIconImage(img.getImage());
    }

    public void switchComponent(JComponent componentToRemove, JComponent componentToAdd) {
        remove(componentToRemove);
        add(componentToAdd);
        revalidate();
        repaint();
    }


}
