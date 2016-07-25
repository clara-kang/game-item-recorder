package ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonthButton extends JButton{

    private IndexFrame indexFrame;
    private String month;
    private MonthPanel.MonthPanelFactory monthPanelFactory;

    public MonthButton(IndexFrame indexFrame, String display, String month, MonthPanel.MonthPanelFactory monthPanelFactory) {
        super(display);
        this.indexFrame = indexFrame;
        this.month = month;
        this.monthPanelFactory = monthPanelFactory;

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(goToMonthPanel);
            }
        });
    }

    Runnable goToMonthPanel = new Runnable() {
        public void run() {
            indexFrame.switchComponent(PastIndexPanel.class, monthPanelFactory.create(month));
        }
    };

    @Singleton
    public static class MonthButtonFactory {
        private IndexFrame indexFrame;
        private MonthPanel.MonthPanelFactory monthPanelFactory;

        @Inject
        public MonthButtonFactory(IndexFrame indexFrame, MonthPanel.MonthPanelFactory monthPanelFactory) {
            this.indexFrame = indexFrame;
            this.monthPanelFactory = monthPanelFactory;
        }

        public MonthButton create(String display, String month) {
            return new MonthButton(indexFrame, display, month, monthPanelFactory);
        }
    }
}
