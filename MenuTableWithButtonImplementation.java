package assignment_4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuTableWithButtonImplementation extends JFrame implements ActionListener {
    JTable table;
    Object[][] data;
    String[] columnNames;
    JButton myButton1;
    Customer customer;

    MenuTableWithButtonImplementation(Customer customer){
        this.customer = customer;
        //this.menu = new MyTable("Menu", customer);
        this.setSize(500, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.data = new Object[][]{{"Kozhukatta", 17, "Not available", "Snacks"},
                {"Ada Leaf", 25, "Available", "Snacks"},
                {"Upma", 60, "Not available", "Meal"},
                {"Payar", 60, "Available", "Breakfast"},
                {"Pongal", 60, "Not available", "Breakfast"},
                {"Puttu-Kadala", 90, "Available", "Meal"},
                {"Kadai Paneer", 170, "Available", "Meal"}};
        this.columnNames = new String[]{"Item", "Price", "Availability", "Category"};
        this.setTitle("Byte Me Menu");

        this.myButton1 = new JButton("See pending orders");
        myButton1.setBounds(400, 40, 200, 40);
        myButton1.addActionListener(this);

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        this.table = new JTable(model);
        this.add(myButton1);
        this.add(new JScrollPane(table));
        //this.validate();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.myButton1){
            this.dispose();
            new PendingOrdersTableWithButtonImplementation(customer);
        }
    }
}