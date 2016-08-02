package ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import logic.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Singleton
public class PastIndexPanel extends JPanel{

    private List<JButton> pastMonthsButtons;
    private IndexFrame indexFrame;
    private MonthButton.MonthButtonFactory monthButtonFactory;

    @Inject
    public PastIndexPanel(IndexFrame indexFrame, MonthButton.MonthButtonFactory monthButtonFactory){
        super();
        this.indexFrame = indexFrame;
        this.monthButtonFactory = monthButtonFactory;
        setLayout(new FlowLayout());
        addPastMonthsButtons();
    }

    private void addPastMonthsButtons() {
        pastMonthsButtons = new ArrayList<JButton>();
        try{
            List<String> monthTableNames = Utils.readMonths();
            Iterator<String> itr = monthTableNames.iterator();
            while(itr.hasNext()) {
                String id = itr.next();
                MonthButton month = monthButtonFactory.create(toMonth(id), id);
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

    //todo move to utils
    private String toMonth(String month) {
        try {
            int num = Integer.parseInt(month);
            return new DateFormatSymbols().getMonths()[num-1];
        } catch (NumberFormatException e){
            //todo
            return null;
        }
    }

    Runnable returnToIndexPanel = new Runnable() {
        public void run() {
            indexFrame.switchComponent(PastIndexPanel.class, IndexPanel.class);
        }
    };
}
