/*
 * Copyright (C) 2019 Daniel Saukel
 *
 * All rights reserved.
 */
package io.github.sataniel98.eu4autocolor.gui;

import io.github.sataniel98.eu4autocolor.data.Definition;
import io.github.sataniel98.eu4autocolor.data.Province;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.TextField;

public class ProvinceEditorGUI extends GUI {

    private TextField id = new TextField("<ID Number>");
    private TextField name = new TextField("<Default province name>");

    private Province prov;

    public ProvinceEditorGUI(Definition def, Province toChange, Choice provinces) {
        String old = toChange == null ? "" : toChange.toString();
        prov = toChange;// Why does Java suck?
        setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
        setTitle("Set ID and name");
        if (prov != null) {
            id.setText(String.valueOf(prov.getId()));
            name.setText(prov.getName());
        }
        add(id);
        add(name);
        add("Confirm", e -> {
            try {
                if (def.isIdInUse(Integer.parseInt(id.getText()), prov)) {
                    new NotifierGUI("Error", "\"" + id.getText() + "\" is already in use.");
                    id.setText(String.valueOf(def.randomId()));
                    return;
                }
            } catch (NumberFormatException exception) {
                new NotifierGUI("Error", "\"" + id.getText() + "\" is not a valid number.");
                id.setText(String.valueOf(def.randomId()));
                return;
            }
            if (prov == null) {
                prov = new Province();
                def.getProvinces().add(prov);
                byte[] color = def.randomColor();
                prov.setColor(color[0], color[1], color[2]);
            }
            prov.setId(Integer.parseInt(id.getText()));
            prov.setName(name.getText());
            if (!old.isEmpty()) {
                log("Changed province \"" + old + "\" to \"" + prov.toString() + "\".");
                int i = provinces.getSelectedIndex();
                provinces.remove(i);
                provinces.insert(prov.toString(), i);
            } else {
                log("Created the new province \"" + prov.toString() + "\".");
                provinces.add(prov.toString());
            }
            dispose();
        });
        add("Cancel", e -> dispose());
        pack();
        center();
        setCloseOnX();
        setVisible(true);
    }

}
