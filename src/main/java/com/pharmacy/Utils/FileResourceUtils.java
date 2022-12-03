package com.pharmacy.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileResourceUtils {


    /**
     * Get a file from the resources folder
     * works everywhere, IDEA, Unit tests and JAR file.
     *
     * @param fileName file name including path (if nested)
     * @return input stream of provided file
     * @implNote <a href="https://mkyong.com/java/java-read-a-file-from-resources-folder/">Java – Read a file from resources folder</a>
     */
    public InputStream getFileFromResourceAsStream(String fileName) {
        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    /**
     * Get properties from a .properties file in resources folder
     *
     * @param key      key of the property
     * @param fileName file name including path (if nested)
     * @return value of the property
     */
    public String getProperty(String key, String fileName) {
        InputStream is = new FileResourceUtils().getFileFromResourceAsStream(fileName);
        Properties prop = new Properties();

        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop.getProperty(key);
    }
}
