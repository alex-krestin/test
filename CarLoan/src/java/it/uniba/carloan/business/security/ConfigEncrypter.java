package it.uniba.carloan.business.security;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static it.uniba.carloan.business.constants.Constants.DB_CONFIG_FILE;

public class ConfigEncrypter<T extends Serializable> extends SystemConfigurator {

    public void createConfig(T object) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {
        createEmptyConfigFile();
        writeToFile(encrypt(object));
    }

    private boolean createEmptyConfigFile() {
        boolean isCreated = false;

        try {
            File f = new File(DB_CONFIG_FILE);
            isCreated = f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isCreated;
    }


    private static void writeToFile(byte[] object) throws IOException {
        FileOutputStream stream = new FileOutputStream(DB_CONFIG_FILE);
        stream.write(object);
        stream.close();
    }

    private byte[] encrypt(Serializable object) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        getKeyFromTimestamp(getFileCreationTime());
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







}
