package assignment_4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PendingOrdersTableWithButtonImplementation extends JFrame implements ActionListener {
    JTable table;
    Object[][] data;
    String[] columnNames;
    JFrame pendingOrders;
    JButton myButton2;
    Customer customer;

    PendingOrdersTableWithButtonImplementation(Customer customer){
        this.customer = customer;
        //this.pendingOrders = new MyTable("Pending Orders", customer);
        this.setSize(500, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.data = new Object[customer.getPendingOrders().size()][];
        this.columnNames = new String[]{"Order Number", "Order Status", "Items Ordered"};
        int i = 0;
        for(MenuItem item : customer.getPendingOrders()) {
            if (i == 0) {
                data[i] = new Object[]{customer.getOrderNumber(), customer.viewOrderStatus(), item.getItemName()};
                i++;
            } else {
                data[i] = new Object[]{"", "", item.getItemName()};
                i++;
            }
        }
        this.setTitle("Orders Pending");

        this.myButton2 = new JButton("View menu");
        myButton2.setBounds(400, 40, 200, 40);
        myButton2.addActionListener(this);

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        this.table = new JTable(model);
        this.add(myButton2);
        this.add(new JScrollPane(table));
        //this.validate();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.myButton2){
            this.pendingOrders.dispose();
            new MenuTableWithButtonImplementation(customer);
        }
    }
}