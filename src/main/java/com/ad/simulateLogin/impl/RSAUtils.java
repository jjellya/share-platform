package com.ad.simulateLogin.impl;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtils {
    private KeyPair keyPair;
    private byte[]     keyword;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Cipher cipher;

    public RSAUtils(byte[] keyword) {
        this.keyword = keyword;
    }

    private synchronized void initCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
        if (cipher == null) {
            cipher = Cipher.getInstance("RSA");
        }
    }

    private synchronized void initKey() throws NoSuchAlgorithmException {
        if (keyPair == null) {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom secureRandom = new SecureRandom(keyword);
            keyPairGenerator.initialize(1024, secureRandom);
            keyPair = keyPairGenerator.genKeyPair();
        }
    }

    public byte[] getPublicKeyBytes() throws NoSuchAlgorithmException {
        if (keyPair == null) {
            initKey();
        }
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        return publicKeyBytes;
    }

    public static byte[] getPublicKeyBytes(byte[] keyword) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(keyword);
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        return publicKeyBytes;
    }

    public synchronized PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (publicKey == null) {
            byte[] keyBytes = getPublicKeyBytes();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(spec);
        }
        return publicKey;
    }

    public static PublicKey getPublicKey(byte[] keyword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = RSAUtils.getPublicKeyBytes(keyword);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(spec);
        return publicKey;
    }

    public static PublicKey getPublicKeyByKeyBytes(byte[] keyBytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(spec);
        return publicKey;
    }

    public byte[] getPrivateKeyBytes() throws NoSuchAlgorithmException {
        if (keyPair == null) {
            initKey();
        }
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        return privateKeyBytes;
    }

    public static byte[] getPrivateKeyBytes(byte[] keyword) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(keyword);
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        return privateKeyBytes;
    }

    public synchronized PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (privateKey == null) {
            byte[] keyBytes = getPrivateKeyBytes();
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(spec);
        }
        return privateKey;
    }

    public static PrivateKey getPrivateKeyByKeyBytes(byte[] keyBytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(spec);
        return privateKey;
    }

    public static PrivateKey getPrivateKey(byte[] keyword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = getPrivateKeyBytes(keyword);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(spec);
        return privateKey;
    }

    public Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
        if (cipher == null) {
            initCipher();
        }
        return cipher;
    }

    public byte[] encryptData(byte[] data) throws Exception {
        Cipher cipher = getCipher();
        RSAPublicKey pubKey = (RSAPublicKey) getPublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        // byte[] cipherText = cipher.doFinal(data);
        // return cipherText;
        byte[] re = null;
        if (data.length > 117) {
            re = longEncrypt(data, cipher);
        } else {
            re = cipher.doFinal(data);
        }
        return re;

    }

    private static byte[] longEncrypt(byte[] data, Cipher cipher)
            throws IOException, IllegalBlockSizeException, BadPaddingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);// System.out.println("Available
                                                                    // bytes:" +
                                                                    // in.available());

        int b = data.length % 117;
        double len1 = data.length / 117.0;
        // System.out.println();
        for (int i = 0, k = (int) Math.floor(len1); i <= k; i++) {
            int len2 = i == k ? b : 117;
            if (len2 == 0) {
                break;
            }
            byte[] byte1 = new byte[len2];
            // System.out.println(i+":"+k+":"+len2+":");
            System.arraycopy(data, i * 117, byte1, 0, len2);
            // System.out.println(Arrays.toString(byte1));
            // String n=new String(byte1);
            // System.out.println(n);
            // System.out.println();
            byte[] cipherText = cipher.doFinal(byte1);
            out.write(cipherText);
        }
        return out.toByteArray();
    }

    public static byte[] encryptData(byte[] data, byte[] publicKeyBytes) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        RSAPublicKey pubKey = (RSAPublicKey) getPublicKeyByKeyBytes(publicKeyBytes);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] re = null;
        if (data.length > 117) {
            re = longEncrypt(data, cipher);
        } else {
            re = cipher.doFinal(data);
        }
        return re;

    }

    private static byte[] longDeEncrypt(byte[] data, Cipher cipher)
            throws IOException, IllegalBlockSizeException, BadPaddingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);// System.out.println("Available
                                                                    // bytes:" +
                                                                    // in.available());
        byte[] byte1 = new byte[128];
        // System.out.println();
        for (int i = 0; i < data.length; i += 128) {
            // System.out.println(i);
            System.arraycopy(data, i, byte1, 0, 128);
            // System.out.println(Arrays.toString(byte1));
            byte[] cipherText = cipher.doFinal(byte1);
            // System.out.println(Arrays.toString(cipherText));
            // String n=new String(cipherText);
            // System.out.println(n);
            // System.out.println();
            out.write(cipherText);
        }
        return out.toByteArray();
    }

    public byte[] deEncryptData(byte[] data) throws Exception {
        Cipher cipher = getCipher();
        RSAPrivateKey privKey = (RSAPrivateKey) getPrivateKey();
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        // byte[] plainText = cipher.doFinal(data);
        // return plainText;
        byte[] re = null;
        if (data.length > 117) {
            re = longDeEncrypt(data, cipher);
        } else {
            re = cipher.doFinal(data);
        }
        return re;
    }

    public static byte[] deEncryptData(byte[] data, byte[] privateKeyBytes) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        RSAPrivateKey privKey = (RSAPrivateKey) getPrivateKeyByKeyBytes(privateKeyBytes);
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        byte[] re = null;
        if (data.length > 117) {
            re = longDeEncrypt(data, cipher);
        } else {
            re = cipher.doFinal(data);
        }
        return re;
    }
}
