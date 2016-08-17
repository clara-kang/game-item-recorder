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
    private String[] phaseNames;
    private Date[] phaseDurations;
    final SimpleDateFormat parserSDF = new SimpleDateFormat("mm:ss");

    public TimerPanel() {
        newTimer = new JButton("new timer");
        String[] phaseNames = {"PHASE ONE", "PHASE TWO", "PHASE THREE", "PHASE FOUR"};
        this.phaseNames = phaseNames;
        this.phaseDurations = new Date[phaseNames.length];
        try {
            phaseDurations[0] = parserSDF.parse("00:05");
            phaseDurations[1] = parserSDF.parse("00:04");
            phaseDurations[2] = parserSDF.parse("00:03");
            phaseDurations[3] = parserSDF.parse("00:02");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newTimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(doAddTimerPanel);
            }
        });
        add(newTimer, BorderLayout.PAGE_END);
    }

    Runnable doAddTimerPanel = new Runnable() {
        public void run() {
            JPanel timerPanel = new JPanel();
            timerPanel.setPreferredSize(new Dimension(475, 50));
            timerPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            final JComboBox phaseBox = new JComboBox(phaseNames);
            final JTextField initCountDown = new JTextField();
            initCountDown.setPreferredSize(new Dimension(100, 40));
            initCountDown.setFont(new Font("Monospaced", Font.PLAIN, 20));
            initCountDown.setText("00:00");
            initCountDown.setHorizontalAlignment(JTextField.CENTER);
            final JButton startCountDown = new JButton("start");
            final JButton stopCountDown = new JButton("stop");

            timerPanel.add(phaseBox);
            timerPanel.add(initCountDown);
            timerPanel.add(startCountDown);
            timerPanel.add(stopCountDown);

            startCountDown.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    startCountDown.setEnabled(false);
                    String time = initCountDown.getText();
                    try {
                        Date date = parserSDF.parse(time);
                        final Calendar cl = Calendar.getInstance();
                        cl.setTime(date);
                        Timer timer = new Timer(1000, new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                cl.add(Calendar.SECOND, -1);
                                initCountDown.setText(parserSDF.format(cl.getTime()));
                                if((parserSDF.format(cl.getTime()).equals("00:00"))){
                                    System.out.println("STOP");
                                    int currentPhaseIndex = phaseBox.getSelectedIndex();
                                    int nextPhaseIndex = 0;
                                    if(currentPhaseIndex != phaseNames.length - 1) {
                                        nextPhaseIndex = currentPhaseIndex + 1;
                                    }
                                    phaseBox.setSelectedIndex(nextPhaseIndex);
                                    cl.setTime(phaseDurations[nextPhaseIndex]);
                                }
                            }
                        });
                        timer.setRepeats(true);
                        timer.start();
                    } catch (ParseException e1) {
                        //todo popup?
                    }
                }
            });

            stopCountDown.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });

            TimerPanel.this.add(timerPanel, BorderLayout.AFTER_LAST_LINE);
            TimerPanel.this.remove(newTimer);
            TimerPanel.this.add(newTimer, BorderLayout.PAGE_END);
        }
    };

}
