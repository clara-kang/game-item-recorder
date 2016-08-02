package ui;

import com.google.inject.Inject;
import logic.DataUtil;
import logic.Item;
import logic.Utils;

import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

@Singleton
public class TodayPanel extends JPanel{
    private IndexFrame indexFrame;
    private Boolean tableCreated;
    private JTable table;
    private DataUtil dataUtil;

    @Inject
    public TodayPanel(IndexFrame indexFrame, DataUtil dataUtil1) {
        this.indexFrame = indexFrame;
        this.dataUtil = dataUtil1;

        this.setLayout(new GridLayout(1,1));
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(returnToMonthPanel);
            }
        });
        tableCreated = dataUtil.readDates(Utils.getTodayMonth()).contains("DAY" + Utils.getTodayDate());
        if (!tableCreated) {
            try {
                dataUtil.createDay(Utils.getTodayMonth(), Utils.getTodayDate());
                tableCreated = true;
            } catch (Exception e) {
                //todo show error
            }
        }
        String[] columnNames = {"ITEM", "PRICE", "QUANTITY"};
        Set<Item> queryResult = dataUtil.readDay(Utils.getTodayMonth(), "DAY" + Utils.getTodayDate());
        Object[][] rowData = Utils.itemSetToObjectArray(queryResult);
        table = new JTable(rowData, columnNames);

        table.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    String itemName = (String)table.getModel().getValueAt(e.getLastRow(), 0);
                    int newValue = Integer.parseInt((String)table.getModel().getValueAt(e.getLastRow(), e.getColumn()));
                    String column = table.getColumnName(e.getColumn());
                    dataUtil.updateDay(Utils.getTodayMonth(), Utils.getTodayDate(), column, newValue, itemName);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        add(returnButton);
    }

    Runnable returnToMonthPanel = new Runnable() {
        public void run() {
            indexFrame.switchComponent(TodayPanel.this, IndexPanel.class);
        }
    };
}
