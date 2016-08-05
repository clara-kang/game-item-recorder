package ui;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import logic.DataUtil;
import logic.Item;
import logic.Utils;

import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
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
    private String month;
    private String date;

    @Inject
    public TodayPanel(IndexFrame indexFrame, DataUtil dataUtil1) {
        this.indexFrame = indexFrame;
        this.dataUtil = dataUtil1;
        this.month = Utils.getTodayMonth();
        this.date = Utils.getTodayDate();
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(returnToMonthPanel);
            }
        });
        tableCreated = dataUtil.readDates(month).contains("DAY" + date);
        if (!tableCreated) {
            try {
                dataUtil.createDay(month, date);
                tableCreated = true;
            } catch (Exception e) {
                //todo show error
            }
        }
        String[] columnNames = {"ITEM", "PRICE", "QUANTITY"};
        Set<Item> queryResult = dataUtil.readDay(month, "DAY" + date);
        Object[][] rowData = Utils.itemSetToObjectArray(queryResult);
        table = new JTable();
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);

        model.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    String itemName = (String)table.getModel().getValueAt(e.getLastRow(), 0);
                    int newValue = Integer.parseInt((String)table.getModel().getValueAt(e.getLastRow(), e.getColumn()));
                    String column = table.getColumnName(e.getColumn());
                    dataUtil.updateDay(month, date, column, newValue, itemName);
                }
            }
        });

        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton insertButton = new JButton("INSERT ITEM");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String itemName =  JOptionPane.showInputDialog(null,"Enter a message");
                boolean itemInserted = false;
                if(StringUtils.isNotBlank(itemName)) {
                    dataUtil.insertItem(month, date, itemName);
                    Object[] row = {itemName, 0, 0};
                    ((DefaultTableModel) table.getModel()).addRow(row);
                    JOptionPane.showMessageDialog(null, "registered");
                } else {
                    JOptionPane.showMessageDialog(null, "item could not be blank");
                }
            }
        });

        JButton deleteButton = new JButton("DELETE ITEM");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String itemName =  JOptionPane.showInputDialog(null,"Enter a message");
                boolean itemDeleted = false;
                for(int i = 0; i < table.getModel().getRowCount(); i ++) {
                    System.out.println(table.getValueAt(i, 0));
                    if(table.getValueAt(i, 0).equals(itemName)) {
                        ((DefaultTableModel) table.getModel()).removeRow(i);
                        JOptionPane.showMessageDialog(null, "deleted");
                        dataUtil.deleteItem(month, date, itemName);
                        itemDeleted = true;
                        break;
                    }
                }
                if(!itemDeleted) {
                    JOptionPane.showMessageDialog(null, "item not found");
                }
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(insertButton, BorderLayout.PAGE_END);
        add(deleteButton, BorderLayout.PAGE_END);
        add(returnButton, BorderLayout.PAGE_END);
    }

    Runnable returnToMonthPanel = new Runnable() {
        public void run() {
            indexFrame.switchComponent(TodayPanel.this, IndexPanel.class);
        }
    };
}
