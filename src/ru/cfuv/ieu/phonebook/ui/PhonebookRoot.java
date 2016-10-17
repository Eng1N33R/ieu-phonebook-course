package ru.cfuv.ieu.phonebook.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhonebookRoot extends JFrame {
    private JButton addField;
    private JTable table1;
    private JButton editField;
    private JButton delField;
    private JPanel rootpanel;

    public PhonebookRoot() {
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
                exp.setSize(new Dimension(200, 200));
                exp.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        // write your code here
        PhonebookRoot test = new PhonebookRoot();
        test.pack();
        //test.setSize(new Dimension(200,200));
        test.setVisible(true);
    }
}
