package assignment_4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/*If both the methods tested here are working correctly then our code works as expected  -- see
someCommonCode method of FoodOrderingSystem class for details -- very specifically,
its condition: else if(!stringInput.equals("Done") && menuContainsItem(stringInput) && !isItemAvailable(stringInput)){*/

/*The below  class also has test cases for null situations -- note in real life software scenarios, your
menu - a data structure of type List(here) may end up anyhow having a null entry*/
public class OutOfStockTestCases {//Here we check correctness of our implementation when dealing with ordering of item that are out of stock
    //in both assertTrue and assertFalse, the second argument is the message to be displayed in case our test fails
    @BeforeEach
    public void setUp() {new FoodOrderingSystem();}
    //menuContainsItem test cases
    @Test
    public void testMCI_ItemExists() {assertTrue(FoodOrderingSystem.menuContainsItem("Ada Leaf"), "Menu should contain Ada Leaf");}

    @Test
    public void testMCI_ItemDoesNotExist() {assertFalse(FoodOrderingSystem.menuContainsItem("Halwa"), "Menu should not contain Halwa");}

    @Test
    public void testMCI_EmptyString() {assertFalse(FoodOrderingSystem.menuContainsItem(""), "Menu should not contain an empty string");}

    @Test
    public void testMCI_Null() {assertFalse(FoodOrderingSystem.menuContainsItem(null), "Menu should not contain null");}

    //isItemAvailable test cases
    @Test
    public void testIA_ItemExistsAndAvailable() {assertTrue(FoodOrderingSystem.isItemAvailable("Puttu-Kadala"), "Puttu-Kadala should be available");}

    @Test
    public void testIA_ItemExistsAndNotAvailable() {assertFalse(FoodOrderingSystem.isItemAvailable("Kozhukatta"), "Kozhukatta should not be available");}

    @Test
    public void testIA_ItemDoesNotExist() {assertFalse(FoodOrderingSystem.isItemAvailable("Chow mein"), "Chow mein should not exist in menu and so should not be available");}

    @Test
    public void testIA_EmptyString() {assertFalse(FoodOrderingSystem.isItemAvailable(""), "Empty string should not be available");}

    @Test
    public void testIA_Null() {assertFalse(FoodOrderingSystem.isItemAvailable(null), "Null should not be available");}
}