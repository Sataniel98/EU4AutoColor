/*
 * Copyright (C) 2019 Daniel Saukel
 *
 * All rights reserved.
 */
package io.github.sataniel98.eu4autocolor.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

public class ConfirmerGUI extends GUI {

    public ConfirmerGUI(String text, ActionListener onAccept) {
        setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
        setTitle("Warning");
        add(text);
        ActionListener dispose = e -> dispose();
        add("Yes", onAccept, dispose);
        add("No", dispose);
        setVisible(true);
        pack();
        center();
        setCloseOnX();
    }

}
