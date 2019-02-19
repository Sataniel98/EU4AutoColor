/*
 * Copyright (C) 2019 Daniel Saukel
 *
 * All rights reserved.
 */
package io.github.sataniel98.eu4autocolor.data;

import java.awt.Choice;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Definition {

    private Random random = new Random();
    private List<Province> provinces;

    public Definition(Definition def) {
        provinces = new ArrayList<>(def.provinces);
    }

    private Definition() {
        provinces = new ArrayList<>();
    }

    public static Definition deserialize(File file) throws FileNotFoundException, IOException {
        Definition def = new Definition();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();// 1st = head line; not a province
        while (line != null) {
            line = reader.readLine();
            if (line != null) {
                Province prov = Province.deserialize(line);
                System.out.println("Loading: " + line);
                def.provinces.add(prov);
            }
        }
        reader.close();
        return def;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public Province getProvince(String toString) {
        for (Province prov : provinces) {
            if (prov.toString().equals(toString)) {
                return prov;
            }
        }
        return null;
    }

    public byte[] randomColor() {
        byte[] rgb = new byte[]{(byte) random.nextInt(256), (byte) random.nextInt(256), (byte) random.nextInt(256)};
        for (Province prov : provinces) {
            if (prov.getRed() == rgb[0] && prov.getGreen() == rgb[1] && prov.getBlue() == rgb[2]) {
                return randomColor();
            }
        }
        return rgb;
    }

    public int randomId() {
        int id = random.nextInt(10000);
        for (Province prov : provinces) {
            if (prov.getId() == id) {
                return randomId();
            }
        }
        return id;
    }

    public boolean isIdInUse(int id, Province except) {
        for (Province prov : provinces) {
            if (prov.getId() == id && prov != except) {
                return true;
            }
        }
        return false;
    }

    public Choice toChoice() {
        Choice choice = new Choice();
        provinces.forEach(p -> choice.add(p.toString()));
        return choice;
    }

    public void serialize() throws IOException {
        File modded = new File("definition.csv_modded");
        modded.delete();
        modded.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(modded));
        writer.write("province;red;green;blue;x;x");
        writer.newLine();
        for (Province prov : provinces) {
            writer.write(prov.serialize());
            writer.newLine();
        }
        writer.close();
        File backups = new File("backups");
        backups.mkdir();
        File backup = new File(backups, "definition.csv_" + System.currentTimeMillis());
        Files.copy(modded.toPath(), backup.toPath());
    }

}
