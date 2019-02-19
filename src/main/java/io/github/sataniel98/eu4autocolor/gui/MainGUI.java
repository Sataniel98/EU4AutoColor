/*
 * Copyright (C) 2019 Daniel Saukel
 *
 * All rights reserved.
 */
package io.github.sataniel98.eu4autocolor.gui;

import io.github.sataniel98.eu4autocolor.EU4AutoColor;
import io.github.sataniel98.eu4autocolor.data.Province;
import io.github.sataniel98.eu4autocolor.util.Unsigned;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ItemEvent;
import java.io.IOException;

public class MainGUI extends GUI {

    private EU4AutoColor main;

    private Choice provinces;
    private TextField red = new TextField(), blue = new TextField(), green = new TextField(), hex = new TextField();
    private Checkbox used = new Checkbox();

    public MainGUI(EU4AutoColor main) {
        this.main = main;

        setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
        center(480, 335);
        setCloseOnX();

        setTitle("EU4 AutoColor");

        // Select province
        add("Select a province:");
        provinces = main.getModded().toChoice();
        add(provinces);
        add("Select", e -> update());

        // Province info
        add("RGB:");
        add(red, false);
        add(green, false);
        add(blue, false);
        update();
        add("Generate random unused color", e -> {
            byte[] random = main.getModded().randomColor();
            getEditedProvince().setColor(random[0], random[1], random[2]);
            log("Changed color of province \"" + getEditedProvince().getName() + "\" to "
                    + Unsigned.toString(getEditedProvince().getRed()) + "|"
                    + Unsigned.toString(getEditedProvince().getGreen()) + "|"
                    + Unsigned.toString(getEditedProvince().getBlue()) + ".");
            update();
        });
        add("Revert", e -> {
            getEditedProvince().restoreColor();
            log("Reverted color of province \"" + getEditedProvince().getName() + "\" to "
                    + Unsigned.toString(getEditedProvince().getRed()) + "|"
                    + Unsigned.toString(getEditedProvince().getGreen()) + "|"
                    + Unsigned.toString(getEditedProvince().getBlue()) + ".");
            update();
        });
        add("Hex:");
        add(hex);
        add("Edit...", e -> new ProvinceEditorGUI(main.getModded(), getEditedProvince(), provinces));
        add("Activated:");
        used.addItemListener(e -> {
            boolean use = e.getStateChange() == ItemEvent.SELECTED;
            getEditedProvince().setUsed(use);
            log((use ? "A" : "Dea") + "ctivated province \"" + getEditedProvince().getName() + "\".");
        });
        add(used);

        add("Delete", e -> new ConfirmerGUI("Are you sure you want to delete " + getEditedProvince().toString() + "?", a -> {
            Province edited = getEditedProvince();
            provinces.remove(edited.toString());
            main.getModded().getProvinces().remove(edited);
            log("Deleted province \"" + edited.getName() + "\".");
            update();
        }));
        add("Create", e -> new ProvinceEditorGUI(main.getModded(), null, provinces));

        add("Save", e -> {
            try {
                getEditedProvince().setColor(Unsigned.parseByte(red.getText()), Unsigned.parseByte(green.getText()), Unsigned.parseByte(blue.getText()));
                log("Saved!");
                main.getModded().serialize();
            } catch (NumberFormatException exception) {
                log("Invalid input");
            } catch (IOException exception) {
                log("Could not save to file!");
            }
        });

        add(LOG);
        if (main.isUnmoddedFound()) {
            log("An unmodded reference file was found and loaded successfully.");
        } else {
            log("Could not find an unmodded reference file.\n"
                    + "You can drop a \"definition.csv\" file from the map directory of EU4\nin the same directory as EU4 AutoColor.\n"
                    + "The default one that is currently used is in the state of version " + EU4AutoColor.EU4_VERSION + ".");
        }
        if (main.isModdedFound()) {
            log("A modded file was found and loaded successfuly.");
        } else {
            log("To modify a file of an existing mod project, name it\n\"definition.csv_modded\" and put it in the same directory as EU4 AutoColor.");
        }

        setVisible(true);
    }

    public Province getEditedProvince() {
        return main.getModded().getProvince(provinces.getSelectedItem());
    }

    public void update() {
        red.setText(Unsigned.toString(getEditedProvince().getRed()));
        green.setText(Unsigned.toString(getEditedProvince().getGreen()));
        blue.setText(Unsigned.toString(getEditedProvince().getBlue()));
        hex.setText(Unsigned.rgb(getEditedProvince().getRed(), getEditedProvince().getGreen(), getEditedProvince().getBlue()));
        used.setState(getEditedProvince().isUsed());
    }

}
