package assignment_4;
//customerID of course would be an encoded one in actual deployment(if that may be) of this system -- unlike the simulation provided in this code

import java.util.*;//We type cast both from TreeSet to ArrayList and from ArrayList to TreeSet
import java.util.List;

/*
	We clear the input buffer when:
	switching from taking numeric to string input
	we alternate between reading numbers and strings in loops
*/

/*
												  -----NOTE-----
	THIS IS AN AP COURSE, SO WE DO NOT DEAL WITH DATABASES TO STORE INFORMATION ACROSS DIFFERENT RUNS OF THIS PROGRAM.
	WE PROCEED WITH THIS UNDERSTANDING HEREIN.
*/

class MenuItem implements Comparable<MenuItem>{
    private String itemName;
    private int price;
    private boolean isAvailable;
    private String category;
    private List<String> reviews;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    MenuItem(String itemName, int price, boolean isAvailable, String category){
        this.itemName = itemName;
        this.price = price;
        this.isAvailable = isAvailable;
        this.category = category;
        reviews = new ArrayList<>();
    }

    MenuItem(String itemName){this.itemName = itemName;}//To help in File I/O

    @Override
    public int compareTo(MenuItem o) {
        // TODO Auto-generated method stub
        return this.price > o.price ? 1 : -1;
    }

    @Override
    public String toString() {
        return "MenuItem{" + "itemName = '" + this.itemName + "'" + ", price = " + this.price + ", category = '" + this.category + "'}";
    }
}


//class MyTable extends JFrame{
//    JTable table;
//    Object[][] data;
//    String[] columnNames;
//    MyTable(String purpose, Customer customer){
//        this.setSize(500, 300);
//        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        //this.setVisible(true);
//
//        if(purpose.equals("Menu")) {
//            this.data = new Object[][]{{"Kozhukatta", 17, "Not available", "Snacks"},
//                    {"Ada Leaf", 25, "Available", "Snacks"},
//                    {"Upma", 60, "Not available", "Meal"},
//                    {"Payar", 60, "Available", "Breakfast"},
//                    {"Pongal", 60, "Not available", "Breakfast"},
//                    {"Puttu-Kadala", 90, "Available", "Meal"},
//                    {"Kadai Paneer", 170, "Available", "Meal"}};
//            this.columnNames = new String[]{"Item", "Price", "Availability", "Category"};
//            this.setTitle("Byte Me Menu");
//        }else if(purpose.equalsIgnoreCase("Pending Orders")){
//            this.data = new Object[customer.getPendingOrders().size()][];
//            this.columnNames = new String[]{"Order Number", "Order Status", "Items Ordered"};
//            int i = 0;
//            for(MenuItem item : customer.getPendingOrders()) {
//                if (i == 0) {
//                    data[i] = new Object[]{customer.getOrderNumber(), customer.viewOrderStatus(), item.getItemName()};
//                    i++;
//                } else {
//                    data[i] = new Object[]{"", "", item.getItemName()};
//                    i++;
//                }
//            }
//            this.setTitle("Orders Pending");
//        }else{
//            this.data = new Object[][]{{'!', '!', '!', '!'}};
//            this.columnNames = new String[]{"nil", "nil", "nil", "nil"};
//            this.setTitle("Default Name");
//        }
//        DefaultTableModel model = new DefaultTableModel(data, columnNames);
//
//        this.table = new JTable(model);
//        this.add(new JScrollPane(table));
//        //this.validate();
//        this.setLocationRelativeTo(null);
//        this.setVisible(true);
//    }
//}

public class FoodOrderingSystem {
    protected static Scanner sc = new Scanner(System.in);
    protected static int totalOrders;//sentinel value of int = 0
    protected static List<Customer> orders;//These all customers have ordered -- sentinel value of List = null
    protected static Collection<MenuItem> menu;//A Collection with reference to TreeSet
    protected static List<String> orderStatuses = Arrays.asList("Order Received", "Delivered", "Denied", "Preparing", "Out for delivery");
    private static int integerInput;
    private static String stringInput;
    private static String stringInput1;
    protected static int orderNumber = 1;
    private static Random ran = new Random();//This is meant solely for any help that may be needed while simulating

    protected static Map<Integer, String> customerIDusernameMapping;
    protected static Map<Integer, String> customerIDpasswordMapping;

    FoodOrderingSystem(){
        //Three categories simulated in menu: Breakfast, Snacks, Meal
        menu = new TreeSet<>(Arrays.asList(new MenuItem("Pongal", 60, false, "Breakfast"), new MenuItem("Payar", 60, true, "Breakfast"),
                new MenuItem("Ada Leaf", 25, true, "Snacks"), new MenuItem("Kozhukatta", 17, false, "Snacks"),
                new MenuItem("Puttu-Kadala", 90, true, "Meal"), new MenuItem("Kadai Paneer", 170, true, "Meal"),
                new MenuItem("Upma", 60, false, "Meal")));
        Customer c1 = new Customer(1, 2, true);
        Customer c2 = new Customer(3, 1, false);//Hardcode 3 customers initially
        Customer c3 = new Customer(2, 3, true);
        orders = Arrays.asList(c1, c2, c3);
        c1.getMyOrders().put("Halwa", 3);
        c2.getMyOrders().put("Chole Bhature", 2);
        c3.setOrderStatus("Delivered");//Solely for simulating viewPendingOrders method of Admin class

        customerIDusernameMapping = new HashMap<>();
        customerIDpasswordMapping = new HashMap<>();

        for(int i = 0; i < 1000; i++){//Simulation purpose -- assuming 1000 registered customers(Being a college canteen)
            customerIDusernameMapping.put(i + 1, "customer@IIITD");
            customerIDpasswordMapping.put(i + 1,  "customer_2024iiitd");
        }
    }

    public static boolean menuContainsItem(String itemName) {
        if(itemName == null) return false;
        for(MenuItem item : menu) {
            if(itemName.equals(item.getItemName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCustomer(int customerID) {
        for(Customer c : orders) {
            if(c.getCustomerID() == customerID) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPastOrder(String itemName, Customer customer) {
        for(MenuItem item : customer.getPastOrders()) {
            if(itemName.equals(item.getItemName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isItemAvailable(String itemName){
        if(itemName == null) return false;
        if(menuContainsItem(itemName)){
            for(MenuItem item : menu) {
                if (item.getItemName().equals(itemName) && item.isAvailable()) return true;
            }
            return false;
        }else {
            return  false;
        }
    }
    //The below function solely helps us in getting a MenuItem object from the item's name -- obtained as input from user
    public static MenuItem getMenuItemObjectFromItemName(String itemName){//Function used at a point where itemName item would of course be in menu -- simulation purpose
        for(MenuItem menuItem : FoodOrderingSystem.menu){
            if(menuItem.getItemName().equals(itemName)){
                return new MenuItem(menuItem.getItemName(), menuItem.getPrice(), menuItem.isAvailable(), menuItem.getCategory());
            }
        }
        return null;//We would rather not reach here
    }

    public void someCommonCode(String stringInput, Customer customer) {
        //sc.nextLine();//Clearing input buffer
        while(!stringInput.equals("Done")) {
            System.out.print("Enter item name to add to cart(Enter \"Done\" if no more items to add): ");
            stringInput = sc.nextLine();
            if(!stringInput.equals("Done") && menuContainsItem(stringInput) && isItemAvailable(stringInput)) {
                //This would be just a slight simulation of maintaining order history -- in no way its actual supposed manner of working
                customer.getPastOrders().add(getMenuItemObjectFromItemName(stringInput));
                System.out.print("Enter item quantity: ");
                integerInput = sc.nextInt();
                sc.nextLine();//Clearing input buffer


                customer.addItem(stringInput, integerInput);
            }else if(!stringInput.equals("Done") && menuContainsItem(stringInput) && !isItemAvailable(stringInput)){
                System.out.println("Cannot add item - item is not available now");
            }else if(!stringInput.equals("Done") && !menuContainsItem(stringInput)){
                System.out.println("Cannot add item - menu does not have this item");
            }
        }
        System.out.println();
        System.out.println("Before checking out would you\n[1] Like to modify any item quantities\n[2] Remove item(s) from cart\n[3] View final bill and checkout");
        integerInput = sc.nextInt();
        sc.nextLine();//Clearing input buffer


        if(integerInput == 1) {
            System.out.print("Enter item name: ");
            stringInput1 = sc.nextLine();
            System.out.print("Enter alteration factor: ");
            integerInput = sc.nextInt();


            customer.modifyItemQuantity(stringInput1, integerInput);
        }else if(integerInput == 2) {
            System.out.print("Enter name of item to remove: ");
            stringInput1 = sc.nextLine();
            System.out.print("Enter number of plate(s) to remove: ");
            integerInput = sc.nextInt();


            customer.removeItem(stringInput1, integerInput);
        }else if(integerInput == 3) {
            int finalPrice = customer.viewFinalCost();
            if(!(finalPrice == 0)) {
                System.out.print("Enter your payment mode: ");
                stringInput = sc.nextLine();
                System.out.print("Enter address for delivery: ");
                stringInput1 = sc.nextLine();


                System.out.println();
                customer.checkout(stringInput, stringInput1);
                //We add order to the history for a customer only when he checks out upon ordering items
                //Simulation
                OrderHistoryManager.saveOrderHistory(customer);
            }else {
                System.out.println("Stay tuned - delicious dishes could all be on your way!");
            }
        }
    }

    public static void main(String[] args) {//two new line in below code before starting to deal with input
        // TODO Auto-generated method stub
		/*

									-----DEMONSTRATION OF A RUN-----
		Welcome to Byte Me! - the IIIT-D college canteen

Identify yourself(Enter string):
[1] Customer
[2] Admin
Customer

Welcome!
Enter your ID number: 11
Enter username: customer@IIITD
Enter password: customer_2024iiitd
Your account was created. UserID: 11
Login successful!

What would you like to do today?
[1] View menu
[2] Search item
[3] Track order
[4] Enter reviews section
[5] View pending orders
1

Choose operation:
[1] Filter by category
[2] Sort by price
[3] Simply view
3

Item            Price      Availability    Category
Kozhukatta      17         Not available   Snacks
Ada Leaf        25         Available       Snacks
Upma            60         Not available   Meal
Payar           60         Available       Breakfast
Pongal          60         Not available   Breakfast
Puttu-Kadala    90         Available       Meal
Kadai Paneer    170        Available       Meal

Enter item name to add to cart(Enter "Done" if no more items to add): Ada Leaf
Enter item quantity: 2
Added Ada Leaf(2 plates) to your cart
Enter item name to add to cart(Enter "Done" if no more items to add): Payar
Enter item quantity: 2
Added Payar(2 plates) to your cart
Enter item name to add to cart(Enter "Done" if no more items to add): Done

Before checking out would you
[1] Like to modify any item quantities
[2] Remove item(s) from cart
[3] View final bill and checkout
3
Your total bill is Rs. 170
Enter your payment mode: Cash
Enter address for delivery: IIITD H1 Boys Hostel Room No. 302

You have made the payment via Cash and provided the address for delivery as IIITD H1 Boys Hostel Room No. 302
Thank you for shopping with us. We hope you enjoy your Bytes!


Aside, here are the Customers.txt:
7,customer@IIITD,customer_2024iiitd,true
11,customer@IIITD,customer_2024iiitd,false

and Orders History.txt files:
Customer with ID 7 has previously ordered -- MenuItem{itemName = 'Payar', price = 60, category = 'Breakfast'}

Customer with ID 11 has previously ordered -- MenuItem{itemName = 'Ada Leaf', price = 25, category = 'Snacks'}; MenuItem{itemName = 'Payar', price = 60, category = 'Breakfast'}

Also, you will see the corresponding GUI's as you proceed in the manner
The JUnit test cases public classes are in their respective files

		*/
        FoodOrderingSystem fos = new FoodOrderingSystem();
        System.out.println("Welcome to Byte Me! - the IIIT-D college canteen");
        System.out.println();
        System.out.println("Identify yourself(Enter string):\n[1] Customer\n[2] Admin");
        stringInput = sc.nextLine();
        System.out.println();


        if(stringInput.equals("Customer")) {
            System.out.println("Welcome!");
            System.out.print("Enter your ID number: ");
            integerInput = sc.nextInt();
            sc.nextLine();//Clearing input buffer
            System.out.print("Enter username: ");
            stringInput = sc.nextLine();//customer@IIITD
            System.out.print("Enter password: ");
            stringInput1 = sc.nextLine();//customer_2024iiitd
            //sc.nextLine();//Clearing input buffer
            if(!Customer.login(integerInput, stringInput, stringInput1)){
                System.out.println("Invalid login credentials -- please try again");
                return;
            }

            //Customer customer = new Customer(integerInput, orderNumber++, true);//System checks VIP status using ID number and assign so
            Customer customer = new Customer(integerInput, "customer@IIITD", "customer_2024iiitd", ran.nextBoolean(), orderNumber++);//VIP status for simulation purpose
            if(CustomerManager.isAlreadyUser(customer)){
                System.out.println("Login successful!");
            }else{
                System.out.println("Your account was created. UserID: " + integerInput);
                System.out.println("Login successful!");
            }
            System.out.println();

            CustomerManager.saveUser(customer);
            System.out.println("What would you like to do today?\n[1] View menu\n[2] Search item\n[3] Track order\n[4] Enter reviews section\n[5] View pending orders");
            integerInput = sc.nextInt();
            sc.nextLine();//Clearing input buffer
            System.out.println();


            if(integerInput == 1) {
                System.out.println("Choose operation:\n[1] Filter by category\n[2] Sort by price\n[3] Simply view");
                integerInput = sc.nextInt();
                sc.nextLine();//Clearing input buffer
                System.out.println();


                if(integerInput == 1) {
                    System.out.print("Enter category(Breakfast, Snacks, Meal): ");
                    stringInput = sc.nextLine();
                    customer.filterByCategory(stringInput);
                    System.out.println();
                    fos.someCommonCode(stringInput, customer);
                }else if(integerInput == 2) {
                    System.out.println("Menu sorted based on price is:");
                    ArrayList<MenuItem> result = customer.sortByPrice();
                    for(MenuItem item : result) {
                        String availability = item.isAvailable() ? "Available" : "Not available";
                        System.out.println(item.getItemName() + "(Rs. " + item.getPrice() + ") - " + availability);
                    }
                    System.out.println();
                    fos.someCommonCode(stringInput, customer);
                }else if(integerInput == 3) {
                    customer.viewMenu();
                    new MenuTableWithButtonImplementation(customer);
                    System.out.println();
                    fos.someCommonCode(stringInput, customer);
                }
            }else if(integerInput == 2) {
                System.out.print("Enter item name: ");
                stringInput = sc.nextLine();


                if(customer.searchMenu(stringInput)) {
                    System.out.println(stringInput + " item is present in the menu");
                    System.out.print("Add item to orders?(Yes/No): ");
                    stringInput1 = sc.nextLine();
                    if(stringInput1.equals("Yes")) {
                        System.out.print("Enter item quantity: ");
                        integerInput = sc.nextInt();
                        customer.addItem(stringInput, integerInput);
                    }
                }else {
                    System.out.println(stringInput + " item is not present in the menu");
                }
            }else if(integerInput == 3) {
                System.out.println("Choose operation:\n[1] View order status\n[2] Cancel order\n[3] View orders history");
                integerInput = sc.nextInt();
                sc.nextLine();//Clearing input buffer
                System.out.println();


                if(integerInput == 1) {
                    customer.viewOrderStatus();
                }else if(integerInput == 2) {
                    customer.cancelOrder();
                }else if(integerInput == 3) {
                    customer.viewOrderHistory();
                    System.out.println();
                    System.out.print("Do you want to make any reorders?(Yes/No): ");
                    stringInput = sc.nextLine();
                    if(stringInput.equals("Yes")) {
                        while(!stringInput.equals("Done")) {
                            System.out.print("Enter name of item to reorder(Enter \"Done\" if no more items to reorder): ");
                            stringInput = sc.nextLine();
                            if(isPastOrder(stringInput, customer) && !stringInput.equals("Done")) {
                                System.out.print("Enter item quantity: ");
                                integerInput = sc.nextInt();
                                if(integerInput > 0) {
                                    customer.reOrder(stringInput, integerInput);
                                    sc.nextLine();//Clearing input buffer
                                }else {
                                    System.out.println("Please enter a valid quantity");
                                    sc.nextLine();//Clearing input buffer
                                }
                            }else if(!isPastOrder(stringInput, customer) && !stringInput.equals("Done")){
                                System.out.println("Cannot reorder an item not ordered previously");
                            }
                        }
                    }else if(stringInput.equals("No")) {
                        System.out.println("You are not making any reorders");
                    }
                }
            }else if(integerInput == 4) {
                System.out.println("Choose operation:\n[1] Give review\n[2] View reviews");
                integerInput = sc.nextInt();
                sc.nextLine();//Clearing input buffer
                System.out.println();


                if(integerInput == 1) {
                    System.out.print("Enter name of item to review: ");
                    stringInput = sc.nextLine();
                    if(customer.isReviewFeasible(stringInput)) {
                        System.out.print("Enter review: ");
                        stringInput1 = sc.nextLine();


                        customer.provideReview(stringInput, stringInput1);
                    }else {
                        System.out.println("Item not ordered/delivered - cannot provide review");
                    }
                }else if(integerInput == 2) {
                    System.out.print("Enter name of item whose reviews you want to view: ");
                    stringInput = sc.nextLine();


                    customer.viewReviews(stringInput);
                }
            }else if(integerInput == 5){
                new PendingOrdersTableWithButtonImplementation(customer);
            }
        }else if(stringInput.equals("Admin")) {
            System.out.println("Welcome!");
            Admin admin = new Admin();
            System.out.println("What would you like to do today?\n[1] Manage menu\n[2] Manage orders\n[3] Generate report");
            integerInput = sc.nextInt();
            System.out.println();


            if(integerInput == 1) {
                System.out.println("Choose operation:\n[1] Add new item\n[2] Update item availability\n[3] Remove item");
                integerInput = sc.nextInt();
                sc.nextLine();//Clearing input buffer
                System.out.println();


                if(integerInput == 1) {
                    System.out.print("Enter name of new item: ");
                    stringInput = sc.nextLine();
                    if(!menuContainsItem(stringInput)) {
                        System.out.print("Enter price of new item: ");
                        integerInput = sc.nextInt();
                        sc.nextLine();//Clearing input buffer
                        System.out.print("Enter category for new item: ");
                        stringInput1 = sc.nextLine();
                        admin.addToMenu(new MenuItem(stringInput, integerInput, true, stringInput1));
                    }else {
                        System.out.println("The menu already contains this item");
                    }
                }else if(integerInput == 2) {
                    System.out.print("Enter item name: ");
                    stringInput = sc.nextLine();
                    System.out.print("Enter item availability(Available/Not available): ");
                    stringInput1 = sc.nextLine();
                    if(stringInput1.equals("Available")) {
                        admin.updateItemAvailability(stringInput, true);
                        System.out.println(stringInput + " is now made available");
                    }else if(stringInput1.equals("Not available")) {
                        admin.updateItemAvailability(stringInput, false);
                        System.out.println(stringInput + " is now made unavailable");
                    }
                }else if(integerInput == 3) {
                    System.out.print("Enter name of item to remove(item discontinued): ");
                    stringInput = sc.nextLine();
                    admin.removeItemFromMenu(stringInput);
                }
            }else if(integerInput == 2) {
                System.out.println("Choose operation:\n[1] View pending orders\n[2] Update order status\n[3] Process refund\n[4] Handle special requests\n[5] Manage priority order");
                integerInput = sc.nextInt();
                System.out.println();


                if(integerInput == 1) {
                    admin.viewPendingOrders();
                }else if(integerInput == 2) {
                    if(!FoodOrderingSystem.orders.isEmpty()) {
                        System.out.print("Enter customerID for concerned customer: ");
                        integerInput = sc.nextInt();
                        sc.nextLine();//Clearing input buffer
                        if(isCustomer(integerInput)) {
                            System.out.print("Enter status to update order to: ");
                            stringInput = sc.nextLine();
                            admin.updateOrderStatus(stringInput, integerInput);
                        }else {
                            System.out.println("Customer not found - no order to alter status");
                        }
                    }else {
                        System.out.println("Waiting for customers - no order to alter status");
                    }

                }else if(integerInput == 3) {
                    if(!orders.isEmpty()) {
                        System.out.print("Enter customerID for concerned customer: ");
                        integerInput = sc.nextInt();
                        if(isCustomer(integerInput)) {
                            admin.processRefund(integerInput);
                        }else {
                            System.out.println("Customer not found - cannot process refund");
                        }
                    }else {
                        System.out.println("Waiting for customers - cannot process refund");
                    }
                }else if(integerInput == 4) {
                    if(!orders.isEmpty()) {
                        System.out.print("Enter customerID for concerned customer: ");
                        integerInput = sc.nextInt();
                        if(isCustomer(integerInput)) {
                            System.out.println("WARNING - No implementation asked for getting special request from customers");//admin.handleSpecialRequest(stringInput, integerInput);
                        }else {
                            System.out.println("Customer not found - no special request");
                        }

                    }else {
                        System.out.println("Waiting for customers - no special request(s)");
                    }
                }else if(integerInput == 5) {
                    admin.manageOrderPriorities();
                }
            }else if(integerInput == 3) {
                System.out.println("Choose operation:\n[1] Generate daily sales report");
                integerInput = sc.nextInt();
                System.out.println();


                if(integerInput == 1) {
                    admin.generateSalesReportForToday();
                }
            }
        }
    }
}