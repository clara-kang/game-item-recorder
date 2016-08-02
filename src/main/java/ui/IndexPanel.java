package ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Singleton
public class IndexPanel extends JPanel {

    private IndexFrame indexFrame;

    @Inject
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
        todayRecordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(doAddTodayPanel);
            }
        });
        add(pastRecordsButton);
        add(todayRecordButton);
    }

    Runnable doAddPastIndexPanel = new Runnable() {
        public void run() {
            indexFrame.switchComponent(IndexPanel.class, PastIndexPanel.class);
        }
    };

    Runnable doAddTodayPanel = new Runnable() {
        public void run() {
            indexFrame.switchComponent(IndexPanel.class, TodayPanel.class);
        }
    };
}
