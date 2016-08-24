package ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import logic.DataUtil;
import logic.Item;
import logic.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Set;

public class DayPanel extends JPanel{
    private IndexFrame indexFrame;
    private MonthPanel monthPanel;

    public DayPanel(IndexFrame indexFrame, String date, String month, MonthPanel monthPanel, DataUtil dataUtil) {
        this.indexFrame = indexFrame;
        this.monthPanel = monthPanel;
        JTextField total = new JTextField();
        total.setPreferredSize(new Dimension(200, 24));

        Double val = dataUtil.getTotalValue(month, date);
        Double valTruncated = new BigDecimal(val).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        String text = Double.toString(valTruncated);
        total.setText(text);

        JLabel totalLabel = new JLabel("TOTAL VALUE");
        JPanel totalPanel = new JPanel();
        totalPanel.add(totalLabel);
        totalPanel.add(total);

        setLayout(new FlowLayout());
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(returnToMonthPanel);
            }
        });

        String[] columnNames = {"ITEM", "PRICE", "QUANTITY", "ITEMTOTAL"};
        Set<Item> queryResult = dataUtil.readDay(month, date);
        Object[][] rowData = Utils.itemSetToObjectArray(queryResult);
        JTable table = new JTable(rowData, columnNames);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        add(returnButton);
        add(totalPanel);
    }

    Runnable returnToMonthPanel = new Runnable() {
        public void run() {
            indexFrame.switchComponent(DayPanel.this, monthPanel);
        }
    };

    @Singleton
    public static class DayPanelFactory {
        private IndexFrame indexFrame;
        private DataUtil dataUtil;

        @Inject
        public DayPanelFactory(IndexFrame indexFrame, DataUtil dataUtil) {
            this.indexFrame = indexFrame;
            this.dataUtil = dataUtil;
        }

        public DayPanel create(String date, String month, MonthPanel monthPanel) {
            return new DayPanel(indexFrame, date, month, monthPanel, dataUtil);
        }
    }
}
