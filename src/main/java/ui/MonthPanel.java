package ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import logic.DataUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class MonthPanel extends JPanel{
    private IndexFrame indexFrame;

    public String month;

    public MonthPanel(IndexFrame indexFrame, String month, DayButton.DayButtonFactory dayButtonFactory) {
        this.indexFrame = indexFrame;

        this.month = month;
        try{
            Map<Long, String> dates = DataUtils.readDates(month);
            Iterator<Long> itr = dates.keySet().iterator();
            while(itr.hasNext()) {
                Long key = itr.next();
                String date = dates.get(key);
                DayButton dateButton = dayButtonFactory.create(key, date, month, this);
                this.add(dateButton);
            }

        } catch (IOException e) {
            e.printStackTrace();
            //todo make a popup window to display data not found
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

        @Inject
        public MonthPanelFactory(IndexFrame indexFrame, DayButton.DayButtonFactory dayButtonFactory) {
            this.indexFrame = indexFrame;
            this.dayButtonFactory = dayButtonFactory;
        }

        public MonthPanel create(String month) {
            return new MonthPanel(indexFrame, month, dayButtonFactory);
        }
    }
}
