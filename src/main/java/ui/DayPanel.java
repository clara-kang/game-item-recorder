package ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import logic.DataUtils;
import logic.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DayPanel extends JPanel{
    private IndexFrame indexFrame;
    private String month;
    private MonthPanel monthPanel;

    public DayPanel(IndexFrame indexFrame, String date, Long rowNumber, String month, MonthPanel monthPanel) {
        this.indexFrame = indexFrame;
        this.month = month;
        this.monthPanel = monthPanel;

        this.setLayout(new GridLayout(1,1));
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(returnToMonthPanel);
            }
        });
        JEditorPane editorPane = new JEditorPane(){
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };
        editorPane.setEditable(false);
        editorPane.setContentType("text/html");
        try {
            editorPane.setText(Utils.getItemHtmlPage(DataUtils.readItems(month, rowNumber)));
        }catch (IOException e){

        }
        JScrollPane scrollpane = new JScrollPane(editorPane);
        add(scrollpane);
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
