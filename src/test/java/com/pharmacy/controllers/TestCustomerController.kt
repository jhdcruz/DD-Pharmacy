package com.pharmacy.controllers

import com.pharmacy.models.UserModel
import org.junit.jupiter.api.Assertions

class TestCustomerController {
    /**
     * This test will create a new user in the database
     *
     * Very useful if you get locked out due to encryption,
     * since manually adding a user to the database won't work
     *
     * Just add @Test to the method and run the test
     * make sure database is running and able to connect
     */
    fun addUserFallback() {
        val userModel = UserModel()

        userModel.name = "Admin"
        userModel.phone = "000000000000"
        userModel.username = "admin"
        userModel.password = "admin"
        userModel.type = "Administrator"

        try {
            UserController(0).addUser(userModel)
        } catch (ex: Exception) {
            Assertions.fail<String>(ex.message)
        }
    }
}
