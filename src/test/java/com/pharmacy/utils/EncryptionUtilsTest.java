package com.pharmacy.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EncryptionUtilsTest {

    @Test
    void testGenerateKeyBytes() {
        EncryptionUtils encryptionUtils = new EncryptionUtils();
        byte[] keyBytes = encryptionUtils.generateKeyBytes();

        assertEquals(16, keyBytes.length);
    }

    @Test
    void testEncrypt() {
        EncryptionUtils encryptionUtils = new EncryptionUtils();
        byte[] secretKey = encryptionUtils.generateKeyBytes();

        String message = "admin";
        byte[] cipherText = encryptionUtils.encrypt(message, secretKey);

        assertEquals(33, cipherText.length);
    }

    @Test
    void testDecrypt() {
        EncryptionUtils encryptionUtils = new EncryptionUtils();
        byte[] secretKey = encryptionUtils.generateKeyBytes();

        String message = "admin";
        byte[] cipherText = encryptionUtils.encrypt(message, secretKey);
        String decrypted = encryptionUtils.decrypt(cipherText, secretKey);

        assertEquals(message, decrypted);
    }
}
