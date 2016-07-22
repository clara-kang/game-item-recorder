package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IndexPanel extends JPanel {

    private IndexFrame indexFrame;

    public IndexPanel(IndexFrame indexFrame){
        this.indexFrame = indexFrame;
        setLayout(new FlowLayout());
        JButton pastRecordsButton = new JButton("View Past Records");
        JButton todayRecordButton = new JButton("Edit Today's Record");
        pastRecordsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(doAddPastIndexPanel);
            }
        });
        add(pastRecordsButton);
        add(todayRecordButton);
    }

    Runnable doAddPastIndexPanel = new Runnable() {
        public void run() {
            indexFrame.switchComponent(IndexPanel.this, new PastIndexPanel(indexFrame, IndexPanel.this));
        }
    };
}
