/*
 * Copyright (C) 2019 Daniel Saukel
 *
 * All rights reserved.
 */
package io.github.sataniel98.eu4autocolor.util;

public class Unsigned {

    public static String toString(byte unsigned) {
        return String.valueOf(Byte.toUnsignedInt(unsigned));
    }

    public static byte parseByte(String string) {
        return (byte) Integer.parseInt(string);
    }

    public static String rgb(byte red, byte green, byte blue) {
        return String.format("#%02X%02X%02X", Byte.toUnsignedInt(red), Byte.toUnsignedInt(green), Byte.toUnsignedInt(blue));
    }

}
