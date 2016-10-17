package com.company;

import javax.swing.*;

/**
 * Created by Александр on 17.10.2016.
 */
public class Expansion extends JFrame {
    private JPanel expPanel;
    public Expansion(CustomField person){
        this.getContentPane().add(expPanel);
        JOptionPane.showMessageDialog(null, person.name);
    }
}
