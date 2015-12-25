package it.uniba.carloan.business.security;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Logger;

import static it.uniba.carloan.business.constants.Constants.DB_CONFIG_FILE;

public class SystemConfigurator {
    private static final Logger log = Logger.getLogger(SystemConfigurator.class.getName());
    protected static final String transformation = "AES";
    protected static final String salt = "e21f15af2e1e9d342e7aed6206589e29";
    protected static byte[] key;

    public static boolean isExists() {
        File f = new File(DB_CONFIG_FILE);
        return f.exists() && !f.isDirectory();
    }

    protected static void getKeyFromTimestamp(String timestamp) throws NoSuchAlgorithmException, IOException {
        byte[] partialKey = (timestamp + salt).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        partialKey = sha.digest(partialKey);
        key = Arrays.copyOf(partialKey, 16);
    }

    protected static String getFileCreationTime() throws IOException {
        Path file = Paths.get(DB_CONFIG_FILE);
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
        return Long.toString(attr.creationTime().toMillis());
    }
}