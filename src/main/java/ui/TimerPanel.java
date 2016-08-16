package ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

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
            timerPanel.setPreferredSize(new Dimension(350, 50));
            timerPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            final JTextField initCountDown = new JTextField();
            initCountDown.setPreferredSize(new Dimension(100, 40));
            initCountDown.setFont(new Font("Monospaced", Font.PLAIN, 20));
            initCountDown.setText("00:00");
            initCountDown.setHorizontalAlignment(JTextField.CENTER);
            JButton startCountDown = new JButton("start");
            timerPanel.add(initCountDown);
            timerPanel.add(startCountDown);

            startCountDown.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String time = initCountDown.getText();
                    SimpleDateFormat parserSDF = new SimpleDateFormat("mm:ss);
                    System.out.println("time: "+ time);
                }
            });


            TimerPanel.this.add(timerPanel, BorderLayout.AFTER_LAST_LINE);
            TimerPanel.this.remove(newTimer);
            TimerPanel.this.add(newTimer, BorderLayout.PAGE_END);
        }
    };

}
