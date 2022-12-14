package com.pharmacy.database

import com.pharmacy.utils.EncryptionUtils
import com.pharmacy.utils.FileResourceUtils
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import javax.swing.JOptionPane

/**
 * Retrieves connection for database and login verification.
 */
class DatabaseInstance {
    init {
        // datasource resource file
        val fileResourceUtils = FileResourceUtils()
        val resourceFile = "database/datasource.properties"

        // Get database properties
        DB_USERNAME = fileResourceUtils.getProperty("datasource.username", resourceFile)
        DB_PASSWORD = fileResourceUtils.getProperty("datasource.password", resourceFile)
        DB_DRIVER = fileResourceUtils.getProperty("datasource.driver", resourceFile)
        DB_URL = fileResourceUtils.getProperty("datasource.url", resourceFile)

        try {
            // initialize database driver
            Class.forName(DB_DRIVER)
        } catch (e: ClassNotFoundException) {
            JOptionPane.showMessageDialog(
                null,
                "Cannot load database driver properly.",
                "Database error",
                JOptionPane.ERROR_MESSAGE
            )
        }
    }

    /**
     * Get database connection.
     *
     * @return Database connection session
     */
    fun getConnection(): Connection? {
        var connection: Connection? = null

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)
        } catch (e: SQLException) {
            JOptionPane.showMessageDialog(
                null,
                "Cannot establish a connection to the database.\nInform IT Personnel",
                "Database error",
                JOptionPane.ERROR_MESSAGE
            )
        }

        return connection
    }

    /**
     * Login verification method
     *
     * @param username user login
     * @param password user password
     * @return user role / user type
     */
    fun authUser(username: String, password: String): String? {
        try {
            val query = ("SELECT username, password, user_type,secret_key FROM users WHERE username='"
                + username
                + "'")

            val statement = getConnection()!!.createStatement()
            val resultSet: ResultSet = statement.executeQuery(query)

            if (resultSet.next()) {
                val encryptionUtils = EncryptionUtils()

                val secretKey: ByteArray = resultSet.getBytes("secret_key")
                val passwordHash: ByteArray = resultSet.getBytes("password")
                val decryptedPass = encryptionUtils.decrypt(passwordHash, secretKey)

                if (resultSet.getString("username") == username && decryptedPass == password) {
                    return resultSet.getString("user_type")
                }
            }
        } catch (ex: SQLException) {
            JOptionPane.showMessageDialog(
                null,
                "Cannot establish a connection to the database.\nInform IT Personnel",
                "Database error",
                JOptionPane.ERROR_MESSAGE
            )
        }

        return null
    }

    companion object {
        private lateinit var DB_USERNAME: String
        private lateinit var DB_PASSWORD: String
        private lateinit var DB_DRIVER: String
        private lateinit var DB_URL: String
    }
}
