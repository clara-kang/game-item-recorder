package ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
                    final SimpleDateFormat parserSDF = new SimpleDateFormat("mm:ss");
                    try {
                        Date date = parserSDF.parse(time);
                        final Calendar cl = Calendar.getInstance();
                        cl.setTime(date);
                        Timer timer = new Timer(1000, new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                cl.add(Calendar.SECOND, -1);
                                initCountDown.setText(parserSDF.format(cl.getTime()));
                            }
                        });
                        timer.setRepeats(true);
                        timer.start();
                    } catch (ParseException e1) {

                    }
                }
            });


            TimerPanel.this.add(timerPanel, BorderLayout.AFTER_LAST_LINE);
            TimerPanel.this.remove(newTimer);
            TimerPanel.this.add(newTimer, BorderLayout.PAGE_END);
        }
    };

}
