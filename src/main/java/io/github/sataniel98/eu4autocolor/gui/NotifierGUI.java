/*
 * Copyright (C) 2019 Daniel Saukel
 *
 * All rights reserved.
 */
package io.github.sataniel98.eu4autocolor.gui;

import java.awt.FlowLayout;

public class NotifierGUI extends GUI {

    public NotifierGUI(String title, String text) {
        setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
        setTitle(title);
        add(text);
        add("OK", e -> dispose());
        pack();
        center();
        setCloseOnX();
        setVisible(true);
    }

}
