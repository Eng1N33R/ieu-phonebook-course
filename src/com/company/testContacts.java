package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Александр on 17.10.2016.
 */
public class testContacts extends JFrame {
    private JButton addField;
    private JTable table1;
    private JButton editField;
    private JButton delField;
    private JPanel rootpanel;

    public testContacts(){
        this.getContentPane().add(rootpanel);
        this.editField.setEnabled(false);
        this.delField.setEnabled(false);
        addField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomField person = new CustomField();
                person.id = 1;
                person.name = "Max";
                Expansion exp = new Expansion(person);
                exp.pack();
                exp.setSize(new Dimension(200,200));
                exp.setVisible(true);
            }
        });
    }
}
