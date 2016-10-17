package ru.cfuv.ieu.phonebook.ui;

import javax.swing.*;

public class Expansion extends JFrame {
    private JPanel expPanel;
    public Expansion(CustomField person){
        this.getContentPane().add(expPanel);
        JOptionPane.showMessageDialog(null, person.name);
    }
}
