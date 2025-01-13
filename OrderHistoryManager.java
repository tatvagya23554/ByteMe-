package assignment_4;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class OrderHistoryManager {
    public static void saveOrderHistory(Customer user) {
        try {
            //Step 0 -- load data; even when starting with an empty file
            List<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("/Users/tatvagyanahar/Desktop/Orders History.txt")));
            //First making the new line to be written -- orderHistory
            StringBuilder sb = new StringBuilder();
            sb.append("Customer with ID ").append(user.getCustomerID()).append(" has previously ordered -- ");
            for (MenuItem item : user.getPastOrders()) {
                sb.append(item.toString()).append("; ");//We use chained append for StringBuilder -- instead of append(item.toString() + "; ");
            }
            if (sb.length() > 0) {sb.setLength(sb.length() - 2);}
            sb.append("\n");
            String orderHistory = sb.toString();
            //Next setting up the content to be written in the file
            boolean userFound = false;
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                if (line.startsWith("Customer with ID " + user.getCustomerID() )) {
                    updatedLines.add(orderHistory);
                    userFound = true;
                    break;
                }else{
                    updatedLines.add(line);
                }
            }

            if (!userFound) {updatedLines.add(orderHistory);}
            //Finally, writing to file
            Files.write(Paths.get("/Users/tatvagyanahar/Desktop/Orders History.txt"), updatedLines);
        } catch (IOException e) {System.out.println(e); e.printStackTrace();}
    }
}