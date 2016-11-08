package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.model.PhonebookContact;

import javax.swing.*;

public class PhonebookEditForm extends JFrame {
    private JPanel expPanel;
    private JPanel additionalFields;
    private JPanel additionalFieldsContainer;
    private JTextField number1;
    private JPanel numberPanel;
    private JTextField nameField;
    private JButton добавитьНомерButton;
    private JButton добавитьПолеButton;

    public PhonebookEditForm(PhonebookContact person) {
        this.getContentPane().add(expPanel);
        JOptionPane.showMessageDialog(null, person.getName());
    }
}
