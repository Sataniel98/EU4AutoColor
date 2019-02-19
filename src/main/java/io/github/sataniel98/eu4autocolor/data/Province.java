/*
 * Copyright (C) 2019 Daniel Saukel
 *
 * All rights reserved.
 */
package io.github.sataniel98.eu4autocolor.data;

import io.github.sataniel98.eu4autocolor.util.Unsigned;

public class Province {

    private int id;
    private byte lastRed, lastGreen, lastBlue, red, green, blue;
    private String name;
    private boolean used;

    public Province() {
    }

    public static Province deserialize(String line) {
        String[] args = line.split(";");
        if (args.length != 5 && args.length != 6) {
            return null;
        }
        Province prov = new Province();
        try {
            prov.id = Integer.parseInt(args[0]);
            prov.red = Unsigned.parseByte(args[1]);
            prov.green = Unsigned.parseByte(args[2]);
            prov.blue = Unsigned.parseByte(args[3]);
        } catch (NumberFormatException exception) {
            return null;
        }
        prov.lastRed = prov.red;
        prov.lastGreen = prov.green;
        prov.lastBlue = prov.blue;
        prov.name = args[4];
        prov.used = args.length > 5;
        return prov;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getRed() {
        return red;
    }

    public byte getGreen() {
        return green;
    }

    public byte getBlue() {
        return blue;
    }

    public void setColor(byte red, byte green, byte blue) {
        lastRed = this.red;
        lastGreen = this.green;
        lastBlue = this.blue;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void restoreColor() {
        red = lastRed;
        green = lastGreen;
        blue = lastBlue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String serialize() {
        return id + ";" + Unsigned.toString(red) + ";" + Unsigned.toString(green) + ";" + Unsigned.toString(blue) + ";" + name + (used ? ";x" : "");
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }

}
