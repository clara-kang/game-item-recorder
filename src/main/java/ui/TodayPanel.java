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
import java.math.BigDecimal;
import java.util.Set;

@Singleton
public class TodayPanel extends JPanel{
    private IndexFrame indexFrame;
    private Boolean tableCreated;
    private JTable table;
    private DataUtil dataUtil;
    private String month;
    private String date;
    private JTextField total;

    @Inject
    public TodayPanel(IndexFrame indexFrame, DataUtil dataUtil1) {
        this.indexFrame = indexFrame;
        this.dataUtil = dataUtil1;
        this.month = Utils.getTodayMonth();
        this.date = Utils.getTodayDate();
        total = new JTextField();
        total.setPreferredSize(new Dimension(200, 24));
        setLayout(new FlowLayout());

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(returnToMonthPanel);
            }
        });
        tableCreated = dataUtil.readDates(month).contains(date);
        if (!tableCreated) {
            try {
                dataUtil.createDay(month, date);
                tableCreated = true;
            } catch (Exception e) {
                //todo show error
            }
        }

        String[] columnNames = {"ITEM", "PRICE", "QUANTITY", "ITEMTOTAL"};
        Set<Item> queryResult = dataUtil.readDay(month, date);
        Object[][] rowData = Utils.itemSetToObjectArray(queryResult);
        //todo add increase decrease button
        table = new JTable();
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };

        model.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                String column = table.getColumnName(e.getColumn());
                if (e.getType() == TableModelEvent.UPDATE && (column.equals("QUANTITY") ||column.equals("PRICE") )) {
                    //todo for unsuccessful changes, reset values
                    try {
                        String itemName = (String) table.getModel().getValueAt(e.getLastRow(), 0);
                        String newValue = (String) table.getModel().getValueAt(e.getLastRow(), e.getColumn());
                        dataUtil.updateDay(month, date, column, newValue, itemName);
                        updateTotal();
                        updateItemTotal(e.getLastRow());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }
                }
            }
        });

        table.setModel(model);
        JLabel totalLabel = new JLabel("TOTAL VALUE");
        JPanel totalPanel = new JPanel();
        totalPanel.add(totalLabel);
        updateTotal();
        totalPanel.add(total);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton insertButton = new JButton("INSERT ITEM");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String itemName =  JOptionPane.showInputDialog(null,"Enter a message");
                if(StringUtils.isNotBlank(itemName)) {
                    try {
                        dataUtil.insertItem(month, date, itemName);
                        Object[] row = {itemName, 0, 0};
                        ((DefaultTableModel) table.getModel()).addRow(row);
                        JOptionPane.showMessageDialog(null, "registered");
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }
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
                        try {
                            ((DefaultTableModel) table.getModel()).removeRow(i);
                            JOptionPane.showMessageDialog(null, "deleted");
                            dataUtil.deleteItem(month, date, itemName);
                            itemDeleted = true;
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage());
                        }
                        break;
                    }
                }
                if(!itemDeleted) {
                    JOptionPane.showMessageDialog(null, "item not found");
                }
            }
        });

        //todo fix layout, make sure scroll bar is functioning
        add(scrollPane);
        add(totalPanel);
        add(deleteButton);
        add(returnButton);
    }

    Runnable returnToMonthPanel = new Runnable() {
        public void run() {
            indexFrame.switchComponent(TodayPanel.this, IndexPanel.class);
        }
    };

    private void updateTotal () {
        Double val = dataUtil.getTotalValue(month, date);
        Double valTruncated = new BigDecimal(val).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        String text = Double.toString(valTruncated);
        total.setText(text);
    }

    private void updateItemTotal(int rowIndex) {
        double price = Double.parseDouble((String)table.getModel().getValueAt(rowIndex, 1));
        int quantity = Integer.parseInt((String)table.getModel().getValueAt(rowIndex, 2));
        table.getModel().setValueAt(price * quantity, rowIndex, 3);
    }
}
