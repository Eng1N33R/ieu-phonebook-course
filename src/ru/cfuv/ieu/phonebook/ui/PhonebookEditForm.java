package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.model.PhonebookContact;

import javax.swing.*;

public class PhonebookEditForm extends JFrame {
    private JPanel expPanel;
    public PhonebookEditForm(PhonebookContact person){
        this.getContentPane().add(expPanel);
        JOptionPane.showMessageDialog(null, person.getName());
    }
}
