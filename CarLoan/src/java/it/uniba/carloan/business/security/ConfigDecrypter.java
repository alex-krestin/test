package it.uniba.carloan.business.security;


import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

import static it.uniba.carloan.business.constants.Constants.DB_CONFIG_FILE;

public class ConfigDecrypter extends SystemConfigurator {

    public static DatabaseConfig readConfig() {
        Path path = Paths.get(DB_CONFIG_FILE);
        byte[] data = new byte[0];
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (DatabaseConfig) decrypt(data);
    }

    private static Object decrypt(byte[] bytes) {
        try {
            getKeyFromTimestamp(getFileCreationTime());

            Cipher cipher = Cipher.getInstance(transformation);
            SecretKeySpec sks = new SecretKeySpec(key, transformation);
            cipher.init(Cipher.DECRYPT_MODE, sks);

            ByteArrayInputStream istream = new ByteArrayInputStream(bytes);

            CipherInputStream cipherInputStream = new CipherInputStream(istream, cipher);
            ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);

            SealedObject sealedObject = (SealedObject) inputStream.readObject();
            inputStream.close();
            return sealedObject.getObject(cipher);

        } catch (GeneralSecurityException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
                //log.log(Level.SEVERE, "Error: ", e);
        }

        return null;
    }

}
