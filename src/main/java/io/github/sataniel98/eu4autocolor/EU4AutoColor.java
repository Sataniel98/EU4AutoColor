/*
 * Copyright (C) 2019 Daniel Saukel
 *
 * All rights reserved.
 */
package io.github.sataniel98.eu4autocolor;

import io.github.sataniel98.eu4autocolor.data.Definition;
import io.github.sataniel98.eu4autocolor.gui.MainGUI;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class EU4AutoColor {

    public static final String EU4_VERSION = "1.28.3.0";

    private Definition unmodded;
    private Definition modded;
    private boolean unmoddedFound = false;
    private boolean moddedFound = false;

    public EU4AutoColor() {
        File unmoddedFile = new File("definition.csv");
        try {
            if (!unmoddedFile.exists()) {
                unmoddedFound = false;
                Files.copy(getClass().getResourceAsStream("/definition.csv"), unmoddedFile.toPath());
            }
            unmodded = Definition.deserialize(unmoddedFile);
        } catch (IOException exception) {
        }

        File moddedFile = new File("definition.csv_modded");
        if (moddedFile.exists()) {
            try {
                modded = Definition.deserialize(moddedFile);
            } catch (IOException exception) {
            }
        }
        if (modded == null && unmodded != null) {
            modded = new Definition(unmodded);
        }

        new MainGUI(this);
    }

    public boolean isUnmoddedFound() {
        return unmoddedFound;
    }

    public boolean isModdedFound() {
        return moddedFound;
    }

    public Definition getUnmodded() {
        return unmodded;
    }

    public Definition getModded() {
        return modded;
    }

    public static void main(String[] args) {
        new EU4AutoColor();
    }

}
