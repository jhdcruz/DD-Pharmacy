package com.pharmacy.controllers

import com.pharmacy.database.DatabaseInstance
import com.pharmacy.models.UserModel
import com.pharmacy.utils.EncryptionUtils
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import javax.swing.JOptionPane

class UserController(private var logId: Int) {
    private var connection: Connection? = null
    private var statement: Statement? = null

    init {
        try {
            connection = DatabaseInstance().getConnection()
            statement = connection!!.createStatement()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }

    val users: ResultSet?
        get() {
            var resultSet: ResultSet? = null

            try {
                val query = "SELECT id, name, username, phone, user_type FROM users"
                resultSet = statement!!.executeQuery(query)
            } catch (sqlException: SQLException) {
                sqlException.printStackTrace()
            }

            return resultSet
        }

    // Methods to add new user
    fun addUser(userModel: UserModel) {
        val resultSet: ResultSet?

        try {
            val duplicateQuery = ("SELECT * FROM users WHERE name='"
                + userModel.name
                + "' AND phone='"
                + userModel.phone
                + "' AND user_type='"
                + userModel.type
                + "'")
            resultSet = statement!!.executeQuery(duplicateQuery)

            if (resultSet!!.next()) {
                JOptionPane.showMessageDialog(null, "User already exists")
            } else {
                val encryptionUtils = EncryptionUtils()
                val secretKey = encryptionUtils.generateKeyBytes()

                val insertQuery = ("INSERT INTO users (name,phone,username,password,user_type,secret_key) "
                    + "VALUES(?,?,?,?,?,?)")

                val prepStatement: PreparedStatement = connection!!.prepareStatement(insertQuery)
                prepStatement.setString(1, userModel.name)
                prepStatement.setString(2, userModel.phone)
                prepStatement.setString(3, userModel.username)
                prepStatement.setBytes(4, encryptionUtils.encrypt(userModel.password!!, secretKey))
                prepStatement.setString(5, userModel.type)
                prepStatement.setBytes(6, secretKey)

                prepStatement.executeUpdate()
                LogsController().addLogEntry(logId, "Added new user: " + userModel.name)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    /**
     * Update existing user (password excluded!)
     *
     *
     * See [.updatePass] for updating
     * password
     *
     * @param userModel populated UserModel
     */
    fun updateUser(userModel: UserModel) {
        try {
            val query = "UPDATE users SET username=?,name=?,phone=?,user_type=? WHERE id=?"
            val prepStatement: PreparedStatement = connection!!.prepareStatement(query)

            prepStatement.setString(1, userModel.username)
            prepStatement.setString(2, userModel.name)
            prepStatement.setString(3, userModel.phone)
            prepStatement.setString(4, userModel.type)
            prepStatement.setInt(5, userModel.id!!)

            prepStatement.executeUpdate()
            LogsController().addLogEntry(logId, "User updated: " + userModel.id + " - " + userModel.name)
        } catch (throwables: SQLException) {
            throwables.printStackTrace()
        }
    }

    // Method to delete existing user
    fun deleteUser(id: Int, username: String) {
        try {
            val query = "DELETE FROM users WHERE id=?"
            val prepStatement: PreparedStatement = connection!!.prepareStatement(query)
            prepStatement.setInt(1, id)

            prepStatement.executeUpdate()
            LogsController().addLogEntry(logId, "Deleted user: $username")
        } catch (sqlException: SQLException) {
            sqlException.printStackTrace()
        }
    }


    fun searchUsers(search: String): ResultSet? {
        var resultSet: ResultSet? = null

        try {
            val query =
                "SELECT id, username, name, phone, user_type FROM users WHERE name LIKE '%$search%' OR phone LIKE '%$search%' OR user_type LIKE '%$search%'"
            resultSet = statement!!.executeQuery(query)
        } catch (sqlException: SQLException) {
            sqlException.printStackTrace()
        }

        return resultSet
    }

    fun getUserId(username: String): Int {
        val resultSet: ResultSet?
        var id = 0

        try {
            val query = "SELECT id FROM users WHERE username='$username'"
            resultSet = statement!!.executeQuery(query)

            if (resultSet!!.next()) {
                id = resultSet.getInt("id")
            }
        } catch (sqlException: SQLException) {
            sqlException.printStackTrace()
        }

        return id
    }

    fun findUser(username: String): ResultSet? {
        var resultSet: ResultSet? = null

        try {
            val query = "SELECT id, username, name, phone, user_type FROM users WHERE username='$username'"
            resultSet = statement!!.executeQuery(query)
        } catch (sqlException: SQLException) {
            sqlException.printStackTrace()
        }

        return resultSet
    }

    fun matchPasswords(username: String, password: String): Boolean {
        val resultSet: ResultSet?

        try {
            val query = ("SELECT password,secret_key FROM users WHERE username='"
                + username
                + "'")
            resultSet = statement!!.executeQuery(query)

            if (resultSet!!.next()) {
                val secretKey = resultSet.getBytes("secret_key")
                val encryptedPassword = resultSet.getBytes("password")

                val decryptedPass = EncryptionUtils().decrypt(encryptedPassword, secretKey)
                return decryptedPass == password
            }
        } catch (sqlException: SQLException) {
            sqlException.printStackTrace()
        }

        return false
    }

    fun updatePass(id: Int, username: String, password: String?) {
        try {
            // encryption
            val encryptionUtils = EncryptionUtils()
            val secretKey = encryptionUtils.generateKeyBytes()
            val encryptedPass = encryptionUtils.encrypt(password!!, secretKey)

            val query = "UPDATE users SET password=?, secret_key=? WHERE id='$id'"

            // save new encrypted password
            val prepStatement: PreparedStatement = connection!!.prepareStatement(query)
            prepStatement.setBytes(1, encryptedPass)
            prepStatement.setBytes(2, secretKey)

            prepStatement.executeUpdate()
            LogsController().addLogEntry(logId, "Password updated for user: $username")
        } catch (sqlException: SQLException) {
            sqlException.printStackTrace()
        }
    }
}
