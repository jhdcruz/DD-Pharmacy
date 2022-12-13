package com.pharmacy.utils;

import org.jetbrains.annotations.NotNull;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;


/**
 * Encryption and Decryption with AES + GCM
 * <p>
 * See <a href="https://gist.github.com/patrickfav/7e28d4eb4bf500f7ee8012c4a0cf7bbf">Reference</a>
 * by <i>Patrick Favre-Bulle</i>
 */
public class EncryptionUtils {
    private final SecureRandom secureRandom = new SecureRandom();
    private static final int GCM_IV_LENGTH = 12;
    private static final String ALGORITHM = "AES/GCM/NoPadding";


    /**
     * This generates a random byte array of 16 bytes
     * <p>
     * Specifically used for generating a random key for AES encryption
     * <p>
     * See {@link #encrypt(String, byte[])},
     * <p>
     * See {@link #decrypt(byte[], byte[])}.
     *
     * @return random 16 byte array
     */
    public byte[] generateKeyBytes() {
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);

        return key;
    }

    /**
     * Encrypt a string.
     *
     * @param plaintext to encrypt (UTF-8)
     * @return encrypted message
     */
    public byte[] encrypt(String plaintext, byte[] secretKey) {
        try {
            // NEVER REUSE THIS IV WITH SAME KEY
            byte[] iv = new byte[GCM_IV_LENGTH];
            secureRandom.nextBytes(iv);

            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv); //128 bit auth tag length
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secretKey, "AES"), parameterSpec);

            byte[] cipherText = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

            ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
            byteBuffer.put(iv);
            byteBuffer.put(cipherText);

            return byteBuffer.array();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Decrypts encrypted message (see {@link #encrypt(String, byte[])}).
     *
     * @param encrypted iv with ciphertext
     * @return original plaintext
     */
    public @NotNull String decrypt(byte[] encrypted, byte[] secretKey) {
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);

            // use first 12 bytes for iv
            AlgorithmParameterSpec gcmIv = new GCMParameterSpec(128, encrypted, 0, GCM_IV_LENGTH);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey, "AES"), gcmIv);

            // Use everything from 12 bytes on as ciphertext
            byte[] plainText = cipher.doFinal(encrypted, GCM_IV_LENGTH, encrypted.length - GCM_IV_LENGTH);

            return new String(plainText, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
