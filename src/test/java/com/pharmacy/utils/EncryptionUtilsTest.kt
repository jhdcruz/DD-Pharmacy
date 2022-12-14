package com.pharmacy.utils

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

internal class EncryptionUtilsTest {
    @Test
    fun `test key bytes generation`() {
        val encryptionUtils = EncryptionUtils()
        val keyBytes = encryptionUtils.generateKeyBytes()

        assertEquals(16, keyBytes.size)
    }

    @Test
    fun `test encryption`() {
        val encryptionUtils = EncryptionUtils()
        val secretKey = encryptionUtils.generateKeyBytes()

        val message = "admin"
        val cipherText = encryptionUtils.encrypt(message, secretKey)

        assertEquals(33, cipherText.size)
    }

    @Test
    fun `test decryption`() {
        val encryptionUtils = EncryptionUtils()
        val secretKey = encryptionUtils.generateKeyBytes()

        val message = "admin"
        val cipherText = encryptionUtils.encrypt(message, secretKey)
        val decrypted = encryptionUtils.decrypt(cipherText, secretKey)

        assertEquals(message, decrypted)
    }
}
