package utility;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static utility.Constants.DB_CONFIG_FILE;
import static utility.Constants.MYSQL;

public class DBConfig {
    private static final String salt = "e21f15af2e1e9d342e7aed6206589e29";
    private static byte[] key;
    private static final String transformation = "AES";
    //TODO get dynamic db change
    private static byte[] databaseType = "1".getBytes();


    private static String detectDatabase(byte[] bytes) {
        return new String(new byte[]{bytes[0]});
    }


    public static boolean isExists() {
        File f = new File(DB_CONFIG_FILE);
        return f.exists() && !f.isDirectory();
    }

    private static String getTimestamp() throws IOException {
        Path file = Paths.get(DB_CONFIG_FILE);
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
        return Long.toString(attr.creationTime().toMillis());
    }


    private static void createCofigFile() {
        File f = new File(DB_CONFIG_FILE);

        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void createConfig(DBConfigObject object) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        createCofigFile();

        byte[] encryptedObject = encrypt(object);
        FileOutputStream stream = new FileOutputStream(DB_CONFIG_FILE);

        // create a destination array that is the size of the two arrays
        byte[] configuration = new byte[1 + encryptedObject.length];

        // copy ciphertext into start of destination (from pos 0, copy ciphertext.length bytes)
        System.arraycopy(databaseType, 0, configuration, 0, 1);

        // copy mac into end of destination (from pos ciphertext.length, copy mac.length bytes)
        System.arraycopy(encryptedObject, 0, configuration, 1, encryptedObject.length);
        stream.write(configuration);
        stream.close();
    }

    public static DBConfigObject readConfig() {
        Path path = Paths.get(DB_CONFIG_FILE);
        byte[] data = new byte[0];
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String dbType = detectDatabase(data);
        byte[] byteObject = Arrays.copyOfRange(data, 1, data.length);

        switch (dbType) {
            case MYSQL:
                return MySQL_DBConfig.class.cast(decrypt(byteObject));
            default:
                return null;
        }
    }

    private static void getKeyFromTimestamp(String timestamp) throws NoSuchAlgorithmException, IOException {
        byte[] key = (timestamp + salt).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        DBConfig.key = Arrays.copyOf(key, 16);
    }

    private static byte[] encrypt(Serializable object) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        getKeyFromTimestamp(getTimestamp());
        ByteArrayOutputStream ostream = null;
        try {
            ostream = new ByteArrayOutputStream();

            // Length is 16 byte
            SecretKeySpec sks = new SecretKeySpec(key, transformation);

            // Create cipher
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            SealedObject sealedObject = new SealedObject(object, cipher);

            // Wrap the output stream
            CipherOutputStream cos = new CipherOutputStream(ostream, cipher);
            ObjectOutputStream outputStream = new ObjectOutputStream(cos);
            outputStream.writeObject(sealedObject);
            outputStream.close();

        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return ostream.toByteArray();
    }


    private static Object decrypt(byte[] bytes) {
        try {
            getKeyFromTimestamp(getTimestamp());
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        SecretKeySpec sks = new SecretKeySpec(key, transformation);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(transformation);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            assert cipher != null;
            cipher.init(Cipher.DECRYPT_MODE, sks);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        ByteArrayInputStream istream = new ByteArrayInputStream(bytes);

        CipherInputStream cipherInputStream = new CipherInputStream(istream, cipher);
        ObjectInputStream inputStream;
        try {
            inputStream = new ObjectInputStream(cipherInputStream);
        } catch (IOException e) {
            System.out.println("BAD CONFIG FILE");
            return null;
        }
        SealedObject sealedObject;
        try {
            sealedObject = (SealedObject) inputStream.readObject();
            return sealedObject.getObject(cipher);
        } catch (ClassNotFoundException | IllegalBlockSizeException | BadPaddingException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}