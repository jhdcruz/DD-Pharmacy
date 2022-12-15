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
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    val users: ResultSet?
        get() {
            val resultSet: ResultSet?

            try {
                val query = "SELECT id, name, username, phone, user_type FROM users"
                resultSet = statement!!.executeQuery(query)
            } catch (e: SQLException) {
                throw SQLException(e)
            }

            return resultSet
        }

    // Methods to add new user
    fun addUser(userModel: UserModel) {
        val resultSet: ResultSet?

        try {
            val duplicateQuery = """
                SELECT * FROM users
                WHERE name=?
                AND phone=?
                AND user_type=?
                """.trimIndent()

            val duplicateStatement = connection!!.prepareStatement(duplicateQuery)
            duplicateStatement.setString(1, userModel.name)
            duplicateStatement.setString(2, userModel.phone)
            duplicateStatement.setString(3, userModel.type)

            resultSet = duplicateStatement.executeQuery()
            if (resultSet!!.next()) {
                JOptionPane.showMessageDialog(null, "User already exists")
            } else {
                val encryptionUtils = EncryptionUtils()
                val secretKey = encryptionUtils.generateKeyBytes()

                val insertQuery = """
                    INSERT INTO users (name,phone,username,password,user_type,secret_key)
                    VALUES(?,?,?,?,?,?);
                    """.trimIndent()

                val prepStatement: PreparedStatement = connection!!.prepareStatement(insertQuery)
                prepStatement.setString(1, userModel.name)
                prepStatement.setString(2, userModel.phone)
                prepStatement.setString(3, userModel.username)
                prepStatement.setBytes(4, encryptionUtils.encrypt(userModel.password!!, secretKey))
                prepStatement.setString(5, userModel.type)
                prepStatement.setBytes(6, secretKey)

                prepStatement.executeUpdate()
                LogsController().addLogEntry(
                    logId,
                    "Added new user: ${userModel.name} (${userModel.username})"
                )
            }
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    /**
     * Update existing user (password excluded!)
     * See [.updatePass] for updating password
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
            LogsController().addLogEntry(
                logId,
                "Updated user: ${userModel.name} (${userModel.username})"
            )
        } catch (e: SQLException) {
            throw SQLException(e)
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
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }


    fun searchUsers(search: String): ResultSet? {
        val resultSet: ResultSet?

        try {
            val query = """
                SELECT id, name, username, phone, user_type
                FROM users
                WHERE name LIKE ?
                OR username LIKE ?
                OR phone LIKE ?
                OR user_type LIKE ?;
                """.trimIndent()

            val prepStatement: PreparedStatement = connection!!.prepareStatement(query)
            prepStatement.setString(1, "%$search%")
            prepStatement.setString(2, "%$search%")
            prepStatement.setString(3, "%$search%")
            prepStatement.setString(4, "%$search%")

            resultSet = prepStatement.executeQuery()
        } catch (e: SQLException) {
            throw SQLException(e)
        }

        return resultSet
    }

    fun getUserId(username: String): Int {
        val resultSet: ResultSet?
        var id = 0

        try {
            val q = """
                SELECT id
                FROM users
                WHERE username=?
                """.trimIndent()

            val prepStatement: PreparedStatement = connection!!.prepareStatement(q)
            prepStatement.setString(1, username)

            resultSet = prepStatement.executeQuery()
            if (resultSet!!.next()) {
                id = resultSet.getInt("id")
            }
        } catch (e: SQLException) {
            throw SQLException(e)
        }

        return id
    }

    fun findUser(username: String): ResultSet? {
        val resultSet: ResultSet?

        try {
            val q = """
                SELECT id, username, name, phone, user_type
                FROM users
                WHERE username=?
                """.trimIndent()

            val prepStatement: PreparedStatement = connection!!.prepareStatement(q)
            prepStatement.setString(1, username)

            resultSet = prepStatement.executeQuery()
        } catch (e: SQLException) {
            throw SQLException(e)
        }

        return resultSet
    }

    fun matchPasswords(username: String, password: String): Boolean {
        val resultSet: ResultSet?

        try {
            val q = """
                SELECT password, secret_key
                FROM users
                WHERE username=?
                """.trimIndent()

            val prepStatement: PreparedStatement = connection!!.prepareStatement(q)
            prepStatement.setString(1, username)

            resultSet = prepStatement.executeQuery()
            if (resultSet!!.next()) {
                val secretKey = resultSet.getBytes("secret_key")
                val encryptedPassword = resultSet.getBytes("password")

                val decryptedPass = EncryptionUtils().decrypt(encryptedPassword, secretKey)
                return decryptedPass == password
            }
        } catch (e: SQLException) {
            throw SQLException(e)
        }

        return false
    }

    fun updatePass(id: Int, username: String, password: String?) {
        try {
            // encryption
            val encryptionUtils = EncryptionUtils()
            val secretKey = encryptionUtils.generateKeyBytes()
            val encryptedPass = encryptionUtils.encrypt(password!!, secretKey)

            // db query
            val query = "UPDATE users SET password=?, secret_key=? WHERE id=?"

            val prepStatement: PreparedStatement = connection!!.prepareStatement(query)
            prepStatement.setBytes(1, encryptedPass)
            prepStatement.setBytes(2, secretKey)
            prepStatement.setInt(3, id)

            prepStatement.executeUpdate()
            LogsController().addLogEntry(logId, "Password updated for user: $username")
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }
}
