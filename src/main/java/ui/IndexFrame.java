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

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton pastRecordsButton = new JButton("View Past Records");
        JButton todayRecordButton = new JButton("Edit Today's Record");

        pastRecordsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(doAddPastIndexPanel);
            }
        });

        controlPanel.add(pastRecordsButton);
        controlPanel.add(todayRecordButton);
        add(controlPanel);
    }

    Runnable doAddPastIndexPanel = new Runnable() {
        public void run() {
            remove(controlPanel);
            add(new PastIndexPanel());
            revalidate();
            repaint();
        }
    };

}
