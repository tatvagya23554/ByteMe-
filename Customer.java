package assignment_4;

import java.util.*;

public class Customer implements Comparable<Customer>{
    private int customerID;
    private String userName = "customer@IIITD";//Simulation purpose
    private String password = "customer_2024iiitd";//Simulation purpose
    private int orderNumber;
    private String orderStatus = "";//Sentinel value of String = null
    private boolean isVip;
    private Map<String, Integer> myOrders;//Item name - quantity mapping
    private List<MenuItem> pastOrders; //= Arrays.asList(new MenuItem("Chilli Paneer", 280, true, "Meal"),
            //new MenuItem("Dosai", 150, true, "Meal"));//Hardcode as we do not go into databases here -- simulation purpose
    private List<MenuItem> pendingOrders = Arrays.asList(new MenuItem("Chole", 30, true, "Meal"),
            new MenuItem("Bhature", 40, true, "Breads"));//Hardcode as we do not go into databases here -- simulation purpose
    private String specialRequest;

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOrderNumber() {//Function meant for simulation purpose
        Random ran = new Random();
        return ran.nextInt(1, 1000);
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderStatus() {//Rather use viewOrderStatus method below
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean isVip) {
        this.isVip = isVip;
    }

    public Map<String, Integer> getMyOrders() {
        return myOrders;
    }

    public void setMyOrders(Map<String, Integer> myOrders) {
        this.myOrders = myOrders;
    }

    public List<MenuItem> getPastOrders() {
        return pastOrders;
    }

    public void setPastOrders(List<MenuItem> pastOrders) {
        this.pastOrders = pastOrders;
    }

    public List<MenuItem> getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(List<MenuItem> pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    Customer(int customerID, int orderNumber, boolean isVip){
        this.customerID = customerID;
        this.orderNumber = orderNumber;
        this.isVip = isVip;
        this.myOrders = new HashMap<>();
        this.pastOrders = new ArrayList<>();
    }

    Customer(int customerID, String userName, String password, boolean isVip, int orderNumber){
        this.customerID = customerID;
        this.userName = userName;
        this.password = password;
        this.isVip = isVip;
        this.myOrders = new HashMap<>();
        this.pastOrders = new ArrayList<>();
    }

    @Override
    public int compareTo(Customer c) {
        // TODO Auto-generated method stub
        if(this.isVip && !c.isVip) {//swapping based on status
            return 1;
        }else if(this.isVip && c.isVip && this.orderNumber > c.orderNumber) {//swapping among VIPs
            return 1;
        }else if(!this.isVip && !c.isVip && this.orderNumber > c.orderNumber) {//swapping among regular customers
            return 1;
        }
        return -1;
    }

    public static boolean login(int customerID, String userName, String password){
        return (!userName.equals(FoodOrderingSystem.customerIDusernameMapping.get(customerID)) || !password.equals(FoodOrderingSystem.customerIDpasswordMapping.get(customerID))) ? false : true;
    }//If customer not registered(customerID > 1000), get method above returns null

    //Browse Menu

    public void viewMenu() {
        System.out.printf("%-15s %-10s %-15s %-15s\n", "Item", "Price", "Availability", "Category");
        for(MenuItem item : FoodOrderingSystem.menu) {
            String availability = item.isAvailable() ? "Available" : "Not available";
            System.out.printf("%-15s %-10s %-15s %-15s\n", item.getItemName(), item.getPrice(), availability, item.getCategory());
        }
    }

    public boolean searchMenu(String itemName) {
        for(MenuItem item : FoodOrderingSystem.menu) {
            if(item.getItemName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public void filterByCategory(String category) {
        System.out.println("Items of category " + category + ":");
        boolean anyItemPresent = false;
        for(MenuItem item : FoodOrderingSystem.menu) {
            if(item.getCategory().equals(category)) {
                anyItemPresent = true;
                String availability = item.isAvailable() ? "Available" : "Not available";
                System.out.println(item.getItemName() + "(Rs. " + item.getPrice() + ") - " + availability);
            }
        }
        if(!anyItemPresent) {
            System.out.println("No item for this category");
        }
    }

    public ArrayList<MenuItem> sortByPrice() {
        Collection<MenuItem> menuCopy = FoodOrderingSystem.menu;
        ArrayList<MenuItem> result = new ArrayList<>(menuCopy);//Casting a TreeSet to ArrayList
        Collections.sort(result);//.sort takes List type arguments
        return result;
    }

    //Cart Options

    public void addItem(String itemName, int quantity) {
        int finalQuantity = (!this.myOrders.isEmpty() && this.myOrders.containsKey(itemName)) ? this.myOrders.get(itemName) + quantity : quantity;
        this.myOrders.put(itemName, finalQuantity);
        this.setOrderStatus("Order Received");
        String grammar = quantity > 1 ? "plates" : "plate";
        System.out.println("Added " + itemName + "(" + quantity + " " + grammar + ") to your cart");
    }

    public void modifyItemQuantity(String itemName, int alterBy) {
        if(alterBy == 0) {
            System.out.println("Cannot alter quantity by zero");
            return;
        }
        if(myOrders.containsKey(itemName) && myOrders.get(itemName) + alterBy >= 0) {
            myOrders.put(itemName, myOrders.get(itemName) + alterBy);
            String word = alterBy > 0 ? "Added " : "Removed ";
            String grammar = (alterBy > 1 || alterBy < -1) ? " plates" : " plate";
            System.out.println(word + " " + Math.abs(alterBy) + grammar + " of " + itemName + " from your cart");
        }else if(myOrders.containsKey(itemName) && !(myOrders.get(itemName) + alterBy >= 0)){
            String grammar = myOrders.get(itemName) > 1 ? " plates" : " plate";
            System.out.println("Please enter a valid alteration factor - can remove upto " + myOrders.get(itemName) + grammar);
        }else {
            System.out.println("Seems like you have not yet added this item to cart");
        }
    }

    public void removeItem(String itemName, int quantity) {
        if(myOrders.containsKey(itemName)) {
            myOrders.put(itemName, myOrders.get(itemName) - quantity);
            String grammar = quantity > 1 ? "plates" : "plate";
            if(myOrders.get(itemName) == 0) {
                myOrders.remove(itemName);
            }
            System.out.println("Removed " + quantity + " " + grammar + " of "+ itemName + " from your cart");
        }else {
            System.out.println("No such item to remove");
        }
    }

    private int getItemCost(String itemName) {//being a helper function, itemName is by default ensured to be in the menu
        for(MenuItem item : FoodOrderingSystem.menu) {
            if(item.getItemName().equals(itemName)) {
                return item.getPrice();
            }
        }
        return -1;//to avoid error in function implementation
    }

    public int viewFinalCost() {
        int finalPrice = 0;//Local int variables are not set to 0 by default -- no sentinel value
        for(String item : myOrders.keySet()) {
            finalPrice += myOrders.get(item) * this.getItemCost(item);
        }
        System.out.println("Your total bill is Rs. " + finalPrice);
        return finalPrice;
    }

    public void checkout(String paymentMode, String deliveryAddress) {
        System.out.println("You have made the payment via " + paymentMode + " and provided the address for delivery as " + deliveryAddress);
        System.out.println("Thank you for shopping with us. We hope you enjoy your Bytes!");
    }

    //Order Tracking

    public String viewOrderStatus() {
        Random ran = new Random();
        int randomIndex = ran.nextInt(FoodOrderingSystem.orderStatuses.size());
        this.orderStatus = this.orderStatus == "" ? FoodOrderingSystem.orderStatuses.get(randomIndex) : null;
        System.out.println("Order status: " + this.orderStatus);
        return this.orderStatus;
    }

    public void cancelOrder() {
        if(!this.orderStatus.equals("Delivered") && !this.orderStatus.equals("")) {
            this.orderNumber = 0;
            this.orderStatus = "Cancelled";
            this.myOrders.clear();
            System.out.println("Your order has been cancelled");
        }else if(!this.orderStatus.equals("Delivered") && this.orderStatus.equals("")){
            System.out.println("No order made - cancellation cannot be made");
        }else {
            System.out.println("Order already prepared - you cannot cancel");
        }
    }

    public void reOrder(String itemName, int quantity) {//Extra method I implemented
        if(!this.myOrders.isEmpty()) {
            for(String order : this.myOrders.keySet()) {
                if(order.equals(itemName)) {
                    this.myOrders.put(itemName, this.myOrders.get(itemName) + quantity);
                    String grammar = (quantity > 1) ? "plates" : "plate";
                    String grammar1 = this.myOrders.get(itemName) > 1 ? "plates" : "plate";
                    System.out.println("Added " + quantity + " " + grammar + " of " + itemName + " to order(You already had "
                            + this.myOrders.get(itemName) + " " + grammar1 + " of " + itemName + ")");
                }
            }
        }else {
            this.myOrders.put(itemName, quantity);
            String grammar = (quantity > 1) ? "plates" : "plate";
            System.out.println("Added " + quantity + " " + grammar + " of " + itemName + " to order");
        }
    }

    public void viewOrderHistory() {
        if(!this.pastOrders.isEmpty()) {
            System.out.println("Past orders: ");
            for(MenuItem item : this.pastOrders) {
                System.out.println(item.getItemName());
            }
        }else {
            System.out.println("No orders made previously");
        }
    }

    //Item Reviews

    public boolean isReviewFeasible(String itemName) {
        boolean reviewFeasible = false;
        for(MenuItem item : FoodOrderingSystem.menu) {
            if(this.myOrders.containsKey(itemName) && this.orderStatus.equals("Delivered") && item.getItemName().equals(itemName)) {
                reviewFeasible = true;
            }
        }
        return reviewFeasible;
    }

    public void provideReview(String itemName, String review) {
        for(MenuItem item : FoodOrderingSystem.menu) {
            if(this.isReviewFeasible(itemName)) {
                item.getReviews().add(review);
            }
        }
    }

    public void viewReviews(String itemName) {
        boolean itemInMenu = false;
        for(MenuItem item : FoodOrderingSystem.menu) {
            if(item.getItemName().equals(itemName)) {
                itemInMenu = true;
                System.out.println("Reviews for " + item.getItemName() + ": ");
                if(!item.getReviews().isEmpty()) {
                    for(String review : item.getReviews()) {
                        System.out.println(review);
                    }
                }else {
                    System.out.println("No reviews, as yet, available for this item");
                }
            }
        }
        if(!itemInMenu) {
            System.out.println("Item not a part of the menu - no reviews available");
        }
    }


    //File I/O

    public String toFileString() {return this.customerID + "," + this.userName + "," + this.password + "," + this.isVip;}

    public static Customer fromFileString(String line) {//Pass a line in our Users.txt file
        String[] customerData = line.split(",");
        int customerID = Integer.parseInt(customerData[0]);
        String username = customerData[1];
        String password = customerData[2];
        boolean isVIP = Boolean.parseBoolean(customerData[3]);
        return new Customer(customerID, username, password, isVIP, FoodOrderingSystem.orderNumber);
    }
}