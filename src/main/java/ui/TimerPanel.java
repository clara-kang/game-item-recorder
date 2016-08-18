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
    private JTextField channelName;
    private String[] phaseNames;
    private Date[] phaseDurations;
    final SimpleDateFormat parserSDF = new SimpleDateFormat("mm:ss");

    public TimerPanel() {
        channelName = new JTextField();
        channelName.setPreferredSize(new Dimension(100, 20));
        newTimer = new JButton("new timer");

        String[] phaseNames = {"WAITING", "CAPTURING", "MINING", "BATTLE"};
        this.phaseNames = phaseNames;
        this.phaseDurations = new Date[phaseNames.length];
        try {
            phaseDurations[0] = parserSDF.parse("11:00");
            phaseDurations[1] = parserSDF.parse("01:35");
            phaseDurations[2] = parserSDF.parse("34:00");
            phaseDurations[3] = parserSDF.parse("17:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newTimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(doAddTimerPanel);
            }
        });

        add(channelName, BorderLayout.PAGE_END);
        add(newTimer, BorderLayout.PAGE_END);
    }

    Runnable doAddTimerPanel = new Runnable() {
        public void run() {
            final JPanel timerPanel = new JPanel();
            timerPanel.setPreferredSize(new Dimension(575, 80));
            timerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), channelName.getText()));
            final JComboBox phaseBox = new JComboBox(phaseNames);
            phaseBox.setPreferredSize(new Dimension(100,40));
            final JTextField initCountDown = new JTextField();
            initCountDown.setPreferredSize(new Dimension(100, 40));
            initCountDown.setFont(new Font("Monospaced", Font.PLAIN, 20));
            initCountDown.setText("00:00");
            initCountDown.setHorizontalAlignment(JTextField.CENTER);
            final JButton startCountDown = new JButton("start");
            final JButton stopCountDown = new JButton("stop");
            final JButton nextPhase = new JButton("next phase");
            final JButton delete = new JButton("delete");

            timerPanel.add(phaseBox);
            timerPanel.add(initCountDown);
            timerPanel.add(startCountDown);
            timerPanel.add(stopCountDown);
            timerPanel.add(nextPhase);
            timerPanel.add(delete);
            stopCountDown.setEnabled(false);

            final Calendar cl = Calendar.getInstance();
            final Timer timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cl.add(Calendar.SECOND, -1);
                    initCountDown.setText(parserSDF.format(cl.getTime()));
                    if((parserSDF.format(cl.getTime()).equals("00:00"))){
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

            startCountDown.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    startCountDown.setEnabled(false);
                    stopCountDown.setEnabled(true);
                    String time = initCountDown.getText();
                    try {
                        Date date = parserSDF.parse(time);
                        cl.setTime(date);
                        timer.setRepeats(true);
                        timer.start();
                    } catch (ParseException e1) {
                        //todo popup?
                    }
                }
            });

            stopCountDown.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    timer.stop();
                    stopCountDown.setEnabled(false);
                    startCountDown.setEnabled(true);
                }
            });

            nextPhase.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int currentPhaseIndex = phaseBox.getSelectedIndex();
                    int nextPhaseIndex = 0;
                    if(currentPhaseIndex != phaseNames.length - 1) {
                        nextPhaseIndex = currentPhaseIndex + 1;
                    }
                    phaseBox.setSelectedIndex(nextPhaseIndex);
                    cl.setTime(phaseDurations[nextPhaseIndex]);
                }
            });

            delete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    timer.stop();
                    TimerPanel.this.remove(timerPanel);
                    TimerPanel.this.repaint();
                    TimerPanel.this.revalidate();
                }
            });

            TimerPanel.this.add(timerPanel, BorderLayout.AFTER_LAST_LINE);
            TimerPanel.this.remove(newTimer);
            TimerPanel.this.remove(channelName);
            TimerPanel.this.add(channelName, BorderLayout.PAGE_END);
            TimerPanel.this.add(newTimer, BorderLayout.PAGE_END);
        }
    };

}
