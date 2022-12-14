package com.pharmacy.utils

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.swing.JOptionPane

/**
 * Encryption and Decryption with AES + GCM
 *
 * See [Reference](https://gist.github.com/patrickfav/7e28d4eb4bf500f7ee8012c4a0cf7bbf)
 * by *Patrick Favre-Bulle*
 */
class EncryptionUtils {
    private val secureRandom = SecureRandom()

    /**
     * This generates a random byte array of 16 bytes
     * Specifically used for generating a random key for AES encryption
     *
     * @return random 16 byte array
     */
    fun generateKeyBytes(): ByteArray {
        val key = ByteArray(16)
        secureRandom.nextBytes(key)

        return key
    }

    /**
     * Encrypt a string.
     *
     * @param plaintext to encrypt (UTF-8)
     * @return encrypted message
     */
    fun encrypt(plaintext: String, secretKey: ByteArray): ByteArray {
        return try {
            // NEVER REUSE THIS IV WITH SAME KEY
            val iv = ByteArray(GCM_IV_LENGTH)
            secureRandom.nextBytes(iv)

            val cipher = Cipher.getInstance(ALGORITHM)
            val parameterSpec = GCMParameterSpec(128, iv) //128 bit auth tag length
            cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(secretKey, "AES"), parameterSpec)

            val cipherText = cipher.doFinal(plaintext.toByteArray(StandardCharsets.UTF_8))
            val byteBuffer = ByteBuffer.allocate(iv.size + cipherText.size)

            byteBuffer.put(iv)
            byteBuffer.put(cipherText)
            byteBuffer.array()
        } catch (e: Exception) {
            throw EncryptionException(e)
        }
    }

    /**
     * Decrypts encrypted message (see [.encrypt]).
     *
     * @param encrypted iv with ciphertext
     * @return original plaintext
     */
    fun decrypt(encrypted: ByteArray, secretKey: ByteArray): String {
        return try {
            val cipher = Cipher.getInstance(ALGORITHM)

            // use first 12 bytes for iv
            val gcmIv: AlgorithmParameterSpec = GCMParameterSpec(128, encrypted, 0, GCM_IV_LENGTH)
            cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(secretKey, "AES"), gcmIv)

            // Use everything from 12 bytes on as ciphertext
            val plainText = cipher.doFinal(encrypted, GCM_IV_LENGTH, encrypted.size - GCM_IV_LENGTH)
            String(plainText, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            throw EncryptionException(e)
        }
    }

    fun EncryptionException(e: Exception): Throwable {
        when (e) {
            is NoSuchAlgorithmException -> JOptionPane.showMessageDialog(
                null,
                e.message,
                "IT Personnel Intervention Required",
                JOptionPane.ERROR_MESSAGE
            )

            is NoSuchPaddingException -> JOptionPane.showMessageDialog(
                null,
                e.message,
                "IT Personnel Intervention Required",
                JOptionPane.ERROR_MESSAGE
            )

            is InvalidKeyException -> JOptionPane.showMessageDialog(
                null,
                e.message,
                "IT Personnel Intervention Required",
                JOptionPane.ERROR_MESSAGE
            )

            is InvalidAlgorithmParameterException -> JOptionPane.showMessageDialog(
                null,
                e.message,
                "IT Personnel Intervention Required",
                JOptionPane.ERROR_MESSAGE
            )

            is IllegalBlockSizeException -> JOptionPane.showMessageDialog(
                null,
                e.message,
                "IT Personnel Intervention Required",
                JOptionPane.ERROR_MESSAGE
            )

            is BadPaddingException -> JOptionPane.showMessageDialog(
                null,
                e.message,
                "IT Personnel Intervention Required",
                JOptionPane.ERROR_MESSAGE
            )

            else -> JOptionPane.showMessageDialog(
                null,
                e.message,
                "IT Personnel Intervention Required",
                JOptionPane.ERROR_MESSAGE
            )
        }

        return e
    }

    companion object {
        private const val GCM_IV_LENGTH = 12
        private const val ALGORITHM = "AES/GCM/NoPadding"
    }
}
