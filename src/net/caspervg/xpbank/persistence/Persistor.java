package net.caspervg.xpbank.persistence;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class Persistor {

    @SuppressWarnings("unchecked")
    public static HashMap<UUID, Integer> loadBank(String path) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            Object result = ois.readObject();
            return (HashMap<UUID, Integer>) result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveBank(String path, HashMap<UUID, Integer> bank) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(bank);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
