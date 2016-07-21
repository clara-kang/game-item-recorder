package ui;

import logic.DataUtils;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PastIndexPanel extends JPanel{

    private List<JButton> pastMonthsButtons;

    public PastIndexPanel(){
        super();
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
    }

    private String toMonth(String number) {
        Integer month = Integer.parseInt(number);
        return new DateFormatSymbols().getMonths()[month-1];
    }
}
