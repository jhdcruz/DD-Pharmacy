package com.pharmacy.utils

import java.io.IOException
import java.io.InputStream
import java.util.*

class FileResourceUtils {
    /**
     * Get a file from the resources folder
     * works everywhere, IDEA, Unit tests and JAR file.
     *
     * @param fileName file name including path (if nested)
     * @return input stream of provided file
     * @implNote [Java – Read a file from resources folder](https://mkyong.com/java/java-read-a-file-from-resources-folder/)
     */
    private fun getFileFromResourceAsStream(fileName: String): InputStream {
        // The class loader that loaded the class
        val classLoader = javaClass.classLoader
        val inputStream = classLoader.getResourceAsStream(fileName)

        // the stream holding the file content
        return inputStream ?: throw IllegalArgumentException("file not found! $fileName")
    }

    /**
     * Get properties from a .properties file in resources folder
     *
     * @param key      key of the property
     * @param fileName file name including path (if nested)
     * @return value of the property
     */
    fun getProperty(key: String?, fileName: String): String {
        val `is` = FileResourceUtils().getFileFromResourceAsStream(fileName)
        val prop = Properties()
        try {
            prop.load(`is`)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return prop.getProperty(key)
    }
}
