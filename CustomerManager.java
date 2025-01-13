package assignment_4;

import java.io.*;
import java.util.*;

public class CustomerManager {
    public static boolean isAlreadyUser(Customer user){
        List<Customer> customers = loadUsers();
        for (Customer customer : customers) {
            if (customer.getUserName().equals(user.getUserName()) && customer.getPassword().equals(user.getPassword()) && (customer.getCustomerID() == user.getCustomerID())) {
                return true;
            }
        }
        return false;
    }

    public static List<Customer> loadUsers() {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/tatvagyanahar/Desktop/Customers.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {customers.add(Customer.fromFileString(line));}
        } catch (Exception e) {e.printStackTrace();}
        return customers;
    }

    public static void saveUser(Customer user) {
        List<Customer> customers = loadUsers();
        for (Customer customer : customers) {
            if (customer.getUserName().equals(user.getUserName()) && customer.getPassword().equals(user.getPassword()) &&  (customer.getCustomerID() == user.getCustomerID())) {
                customers.remove(customer);
                customers.add(user);
                writeUsersToFile(customers);
                return;
            }
        }
        customers.add(user);
        writeUsersToFile(customers);
    }

    private static void writeUsersToFile(List<Customer> customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/Users/tatvagyanahar/Desktop/Customers.txt")))) {
            for (Customer customer : customers) {
                writer.write(customer.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {e.printStackTrace();}
    }
}