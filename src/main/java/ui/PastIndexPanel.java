package ui;

import logic.DataUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PastIndexPanel extends JPanel{

    private List<JButton> pastMonthsButtons;
    private IndexFrame indexFrame;
    private IndexPanel indexPanel;

    public PastIndexPanel(IndexFrame indexFrame, IndexPanel indexPanel){
        super();
        this.indexFrame = indexFrame;
        this.indexPanel = indexPanel;
        setLayout(new FlowLayout());
        addPastMonthsButtons();
    }

    private void addPastMonthsButtons() {
        pastMonthsButtons = new ArrayList<JButton>();
        try{
            List<String> monthTableNames = DataUtils.readMonthTableNames();
            Iterator<String> itr = monthTableNames.iterator();
            while(itr.hasNext()) {
                String id = itr.next();
                JButton month = new JButton(toMonth(id));
                this.add(month);
                pastMonthsButtons.add(month);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //todo make a popup window to display data not found
        }
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(returnToIndexPanel);
            }
        });
        this.add(returnButton);
    }

    private String toMonth(String number) {
        Integer month = Integer.parseInt(number);
        return new DateFormatSymbols().getMonths()[month-1];
    }

    Runnable returnToIndexPanel = new Runnable() {
        public void run() {
            indexFrame.switchComponent(PastIndexPanel.this, indexPanel);
        }
    };
}
