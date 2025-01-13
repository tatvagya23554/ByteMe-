package assignment_4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//Note we do not have any main method
public class LoginTestCases {
    @BeforeEach
    public void setUp() {new FoodOrderingSystem();}

    @Test
    public void testValidLogin() {assertTrue(Customer.login(11, "customer@IIITD", "customer_2024iiitd"));}
    @Test
    public void testInvalidLoginIncorrectUsername() {assertFalse(Customer.login(11, "wrongUser", "customer_2024iiitd"));}
    @Test
    public void testInvalidLoginIncorrectPassword() {assertFalse(Customer.login(11, "customer@IIITD", "wrongPassword"));}
    @Test
    public void testInvalidLoginIncorrectUsernameAndPassword() {assertFalse(Customer.login(11, "wrongUser", "wrongPassword"));}
    @Test
    public void testInvalidLoginNonExistentCustomerID() {assertFalse(Customer.login(1500, "customer@IIITD", "customer_2024iiitd"));}
}