package ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DayPanel extends JPanel{
    private IndexFrame indexFrame;
    private String month;
    private MonthPanel monthPanel;

    public DayPanel(IndexFrame indexFrame, String date, Long rowNumber, String month, MonthPanel monthPanel) {
        this.indexFrame = indexFrame;
        this.month = month;
        this.monthPanel = monthPanel;

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(returnToMonthPanel);
            }
        });
        add(returnButton);
    }

    Runnable returnToMonthPanel = new Runnable() {
        public void run() {
            indexFrame.switchComponent(DayPanel.this, monthPanel);
        }
    };

    @Singleton
    public static class DayPanelFactory {
        private IndexFrame indexFrame;

        @Inject
        public DayPanelFactory(IndexFrame indexFrame) {
            this.indexFrame = indexFrame;
        }

        public DayPanel create(String date, Long rowNumber, String month, MonthPanel monthPanel) {
            return new DayPanel(indexFrame, date, rowNumber, month, monthPanel);
        }
    }
}
