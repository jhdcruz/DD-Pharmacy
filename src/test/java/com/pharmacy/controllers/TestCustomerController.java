package com.pharmacy.controllers;

import com.pharmacy.models.UserModel;

import static org.junit.jupiter.api.Assertions.fail;

public class TestCustomerController {

    /**
     * This test will create a new user in the database
     * <p>
     * Very useful if you get locked out due to encryption,
     * since manually adding a user to the database won't work
     * <p>
     * Just add @Test to the method and run the test
     * make sure database is running and able to connect
     */
    public void addUserFallback() {
        UserModel userModel = new UserModel();
        userModel.setName("Admin");
        userModel.setPhone("000000000000");
        userModel.setUsername("admin");
        userModel.setPassword("admin");
        userModel.setType("Administrator");

        try {
            new UserController(0).addUser(userModel);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
}
