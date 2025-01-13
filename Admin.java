package assignment_4;

import java.util.*;

public class Admin {//Single Admin object in main function
    Admin(){

    }

    //Menu Management

    public void addToMenu(MenuItem menuItem) {
        FoodOrderingSystem.menu.add(menuItem);
        System.out.println("Added " + menuItem.getItemName() + "(Rs. " + menuItem.getPrice() + ") to the menu");
    }

    public void updateItemAvailability(String itemName, boolean availability) {
        boolean itemInMenu = false;
        for(MenuItem item : FoodOrderingSystem.menu) {
            if(item.getItemName().equals(itemName)) {
                itemInMenu = true;
                item.setAvailable(availability);
            }
        }
        if(!itemInMenu) {
            System.out.println("Item not in menu - cannot update availability");
        }
    }

    public void removeItemFromMenu(String itemName) {//Remove item when it is discontinued
        boolean itemInMenu = false;
        for(MenuItem item : FoodOrderingSystem.menu) {
            if(item.getItemName().equals(itemName)) {
                itemInMenu = true;
                FoodOrderingSystem.menu.remove(item);
                System.out.println(item.getItemName() + " discontinued and therefore removed from menu");
            }
        }
        if(!itemInMenu) {
            System.out.println("Item not in menu or already discontinued - cannot remove");
        }
    }

    //Order Management

    public void viewPendingOrders() {
        if(!FoodOrderingSystem.orders.isEmpty()) {
            System.out.println("The following orders are pending:");
            for(Customer c : FoodOrderingSystem.orders) {
                if(!c.getOrderStatus().equals("Delivered") && !c.getOrderStatus().equals("Denied") && !c.getOrderStatus().equals("Cancelled")) {
                    String vipStatus = c.isVip() ? "VIP" : "Regular";
                    System.out.println("Customer with ID " + c.getCustomerID() + "(" + vipStatus + ") has following orders: ");
                    System.out.printf("%-15s %-5s\n", "Item", "Quantity");
                    for(String item : c.getMyOrders().keySet()) {
                        System.out.printf("%-15s %-5s\n", item, Integer.toString(c.getMyOrders().get(item)));
                    }
                    System.out.println();
                }else {
                    continue;
                }
            }
        }else {
            System.out.println("No orders pending");
        }
    }

    public void updateOrderStatus(String orderStatus, int customerID) {
        for(Customer c : FoodOrderingSystem.orders) {
            if(c.getCustomerID() == customerID) {
                c.setOrderStatus(orderStatus);
                System.out.println("Order status for customer with ID " + customerID + " updated to - " + orderStatus);
            }
        }
    }

    public void processRefund(int customerID) {
        for(Customer c : FoodOrderingSystem.orders) {
            if(c.getCustomerID() == customerID) {
                if(c.getOrderStatus().equals("Cancelled")) {
                    System.out.println("Refund made");
                }else {
                    System.out.println("Order not cancelled - cannot process refund");
                }
            }
        }
    }

    public void handleSpecialRequest(String specialRequest, int customerID) {
        for(Customer c : FoodOrderingSystem.orders) {
            if(c.getCustomerID() == customerID && (!c.getOrderStatus().equals("Cancelled") || !c.getOrderStatus().equals("Delivered")  || !c.getOrderStatus().equals("Denied"))) {
                c.setSpecialRequest(specialRequest);
            }
        }
    }

    public void manageOrderPriorities() {
        if(!FoodOrderingSystem.orders.isEmpty()) {
            Collections.sort(FoodOrderingSystem.orders);
            System.out.println("Orders have been prioritised");
        }else {
            System.out.println("Waiting for orders");
        }
    }

    //Report Generation

    public void generateSalesReportForToday() {
        int totalSales = 0;//Total customers who ordered and their order was neither cancelled nor denied
        List<String> mostPopularItems = new ArrayList<>();
        int totalOrdersMade = 0;

        Map<String, Integer> itemPopularityMapping = new HashMap<>();

        for(Customer c : FoodOrderingSystem.orders) {
            if(!c.getOrderStatus().equals("Cancelled") && !c.getOrderStatus().equals("Denied")) {
                totalSales++;
            }
            for(String order : c.getMyOrders().keySet()) {
                totalOrdersMade += c.getMyOrders().get(order);
                if(itemPopularityMapping.containsKey(order)) {
                    int popularity = itemPopularityMapping.get(order);
                    itemPopularityMapping.put(order, popularity + c.getMyOrders().get(order));
                }else {
                    itemPopularityMapping.put(order, c.getMyOrders().get(order));
                }
            }
        }

        for(String item : itemPopularityMapping.keySet()) {
            String mostPopularItem;
            int mostPopularItemPopularity = 0;
            if(itemPopularityMapping.get(item) > Integer.MIN_VALUE) {
                mostPopularItem = item;
                mostPopularItemPopularity = itemPopularityMapping.get(item);
                mostPopularItems.add(mostPopularItem);
            }else if(itemPopularityMapping.get(item) == mostPopularItemPopularity) {
                mostPopularItems.add(item);
            }
        }

        System.out.println("Total sales for the day: " + totalSales);
        System.out.println("Total orders made today: " + totalOrdersMade);
        System.out.print("Most popular item(s) of the day: ");
        if(!mostPopularItems.isEmpty()) {
            for(String item : mostPopularItems) {
                System.out.print(item + " ");
            }
        }else {
            System.out.println("No sale today :|");
        }
    }
}