package com.pharmacy.controllers;

import com.pharmacy.Controllers.UserController;
import com.pharmacy.Models.UserModel;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestCustomerController {

    /**
     * This test will create a new user in the database
     * <p>
     * Very useful if you get locked out due to encryption,
     * since manually adding a user to the database won't work
     */
    @Test
    public void addUser() {
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
