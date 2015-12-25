package it.uniba.carloan.business.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MD5 {
    private static final Logger log = Logger.getLogger(MD5.class.getName());

    public static String getMD5(String st) {
        byte[] digest = new byte[0];

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        int md5Lenght = md5Hex.length();

        while (md5Lenght < 32) {
            md5Hex = "0" + md5Hex;
            md5Lenght++;
        }

        return md5Hex;
    }

}
