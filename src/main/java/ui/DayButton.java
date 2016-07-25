package ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DayButton extends JButton{
    private IndexFrame indexFrame;
    private Long rowNumber;
    private String date;
    private String month;
    private MonthPanel monthPanel;
    private DayPanel.DayPanelFactory dayPanelFactory;


    public DayButton(IndexFrame indexFrame, Long rowNumber, String date, String month, DayPanel.DayPanelFactory dayPanelFactor, MonthPanel monthPanel) {
        super(date);
        this.indexFrame = indexFrame;
        this.rowNumber = rowNumber;
        this.date = date;
        this.month = month;
        this.dayPanelFactory = dayPanelFactor;
        this.monthPanel = monthPanel;

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(displayItems);
            }
        });
    }

    Runnable displayItems = new Runnable() {
        public void run() {
            indexFrame.switchComponent((JPanel)DayButton.this.getParent(), dayPanelFactory.create(date, rowNumber, month, monthPanel));
        }
    };

    @Singleton
    public static class DayButtonFactory {
        private IndexFrame indexFrame;
        private DayPanel.DayPanelFactory dayPanelFactory;

        @Inject
        public DayButtonFactory(IndexFrame indexFrame, DayPanel.DayPanelFactory dayPanelFactory) {
            this.indexFrame = indexFrame;
            this.dayPanelFactory = dayPanelFactory;
        }

        public DayButton create(Long rowNumber, String date, String month, MonthPanel monthPanel) {
            return new DayButton(indexFrame, rowNumber, date, month, dayPanelFactory, monthPanel);
        }
    }
}
