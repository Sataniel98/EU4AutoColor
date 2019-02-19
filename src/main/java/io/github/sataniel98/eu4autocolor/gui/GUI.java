/*
 * Copyright (C) 2019 Daniel Saukel
 *
 * All rights reserved.
 */
package io.github.sataniel98.eu4autocolor.gui;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends Frame {

    protected static final TextArea LOG = new TextArea();

    static {
        LOG.setEditable(false);
    }

    public static void log(String text) {
        LOG.setText(LOG.getText() + text + "\n");
        System.out.println(text);
    }

    public void add(String text) {
        add(new Label(text));
    }

    public void add(String buttonText, ActionListener... listeners) {
        add(new Button(buttonText), listeners);
    }

    public void add(Button button, ActionListener... listeners) {
        for (ActionListener listener : listeners) {
            button.addActionListener(listener);
        }
        add(button);
    }

    public void add(TextField field, boolean editable) {
        field.setEditable(editable);
        add(field);
    }

    public void center(int width, int height) {
        setSize(width, height);
        center();
    }

    public void center() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() / 2 - getWidth() / 2);
        int y = (int) (dimension.getHeight() / 2 - getHeight() / 2);
        setLocation(x, y);
    }

    public void setCloseOnX() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                dispose();
            }
        });
    }

}
