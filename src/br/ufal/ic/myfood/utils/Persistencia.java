package br.ufal.ic.myfood.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {
    public static <T> void save(String path, List<T> data) {
        try {
            // Garante que a pasta data existe
            new File("./data").mkdirs();

            XMLEncoder encoder = new XMLEncoder(new FileOutputStream(path));
            encoder.writeObject(data);
            encoder.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados em " + path + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> load(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return new ArrayList<>();
            }

            XMLDecoder decoder = new XMLDecoder(new FileInputStream(file));
            List<T> data = (List<T>) decoder.readObject();
            decoder.close();
            return data;
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados de " + path + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
