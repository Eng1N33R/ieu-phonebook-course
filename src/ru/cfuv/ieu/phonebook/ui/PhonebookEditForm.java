package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.model.PhonebookContact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhonebookEditForm extends JFrame {
    private final PhonebookContact contact;

    private JPanel expPanel;
    private JPanel additionalFields;
    private JPanel additionalFieldsContainer;
    private JPanel numberPanel;
    private JTextField nameField;
    private JTextField number1;
    private JButton addNumber;
    private JButton addField;
    private JTextField textField1;
    private int numberGridCounter = 1;

    public PhonebookEditForm(PhonebookContact person) {
        this.getContentPane().add(expPanel);
        this.contact = person;

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0d;
        c.fill = 1;
        number1 = new JTextField();
        numberPanel.add(number1, c);

        addNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JTextField numberField = new JTextField();
                final JButton removeNumber = new JButton(
                        new ImageIcon("res/Delete.png"));

                GridBagConstraints c = new GridBagConstraints();
                c.gridy = numberGridCounter;
                c.fill = 1;
                c.gridx = 0;
                c.weightx = 1.0d;
                numberPanel.add(numberField, c);
                c.gridx = 1;
                c.weightx = 0.0d;
                numberPanel.add(removeNumber, c);
                numberPanel.revalidate();

                numberGridCounter++;

                removeNumber.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        numberPanel.remove(numberField);
                        numberPanel.remove(removeNumber);
                        numberPanel.revalidate();
                    }
                });
            }
        });
    }
}
