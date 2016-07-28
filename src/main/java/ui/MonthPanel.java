package ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import logic.DataUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

public class MonthPanel extends JPanel{
    private IndexFrame indexFrame;

    public String month;

    public MonthPanel(IndexFrame indexFrame, String month, DayButton.DayButtonFactory dayButtonFactory, DataUtil dataUtil) {
        this.indexFrame = indexFrame;

        this.month = month;
        List<String> dates = dataUtil.readDates(month);
        Iterator<String> itr = dates.iterator();
        while (itr.hasNext()) {
            DayButton dateButton = dayButtonFactory.create(itr.next(), month, this);
            this.add(dateButton);
        }

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(returnToIndexPanel);
            }
        });

        add(returnButton);
    }

    Runnable returnToIndexPanel = new Runnable() {
        public void run() {
            indexFrame.switchComponent(MonthPanel.this, PastIndexPanel.class);
        }
    };

    @Singleton
    public static class MonthPanelFactory {
        private IndexFrame indexFrame;
        private DayButton.DayButtonFactory dayButtonFactory;
        private DataUtil dataUtil;

        @Inject
        public MonthPanelFactory(IndexFrame indexFrame, DayButton.DayButtonFactory dayButtonFactory, DataUtil dataUtil) {
            this.indexFrame = indexFrame;
            this.dayButtonFactory = dayButtonFactory;
            this.dataUtil = dataUtil;
        }

        public MonthPanel create(String month) {
            return new MonthPanel(indexFrame, month, dayButtonFactory, dataUtil);
        }
    }
}
