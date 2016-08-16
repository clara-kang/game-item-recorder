package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerPanel extends JPanel{
    private JButton newTimer;

    public TimerPanel() {
        newTimer = new JButton("new timer");
        newTimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(doAddTodayPanel);
            }
        });
        add(newTimer, BorderLayout.PAGE_END);
    }

    Runnable doAddTodayPanel = new Runnable() {
        public void run() {
            JPanel timerPanel = new JPanel();
            timerPanel.setPreferredSize(new Dimension(500, 100));
            TimerPanel.this.add(timerPanel, BorderLayout.AFTER_LAST_LINE);
        }
    };

}
