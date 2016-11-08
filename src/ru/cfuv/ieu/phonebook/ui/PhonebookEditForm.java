package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.model.PhonebookContact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhonebookEditForm extends JFrame {
    private JPanel expPanel;
    private JPanel additionalFields;
    private JPanel additionalFieldsContainer;
    private JTextField number1;
    private JPanel numberPanel;
    private JTextField nameField;
    private JButton addNumber;
    private JButton addField;
    private int numberGridCounter = 1;
    private int fieldGridCounter = 0;

    public PhonebookEditForm(PhonebookContact person) {
        this.getContentPane().add(expPanel);
        JOptionPane.showMessageDialog(null, person.getName());

        addField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GridBagConstraints c = new GridBagConstraints();
                c.gridy = fieldGridCounter;

                c.gridx = 0;
                c.weightx = 0.2;
                c.fill = 1;
                JTextField name = new JTextField();
                additionalFieldsContainer.add(name, c);

                c.gridx = 1;
                c.weightx = 0.75;
                JTextField label = new JTextField();
                additionalFieldsContainer.add(label, c);

                c.gridx = 2;
                c.weightx = 0.05;
                JButton delete = new JButton(new ImageIcon("res/Delete.png"));
                delete.setVisible(true);
                additionalFieldsContainer.add(delete,c);

                additionalFieldsContainer.revalidate();


            }
        });
    }
}
