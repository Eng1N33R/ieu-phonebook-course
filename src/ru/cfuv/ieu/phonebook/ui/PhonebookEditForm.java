package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.model.PhonebookContact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@SuppressWarnings("unused")
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
    private java.util.List<JComponent[]> numbers = new ArrayList<>();
    private java.util.List<JComponent[]> addFields = new ArrayList<>();
    private int numberGridCounter = 1;
    private int fieldGridCounter = 0;

    private void addFirstNumber() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0d;
        c.fill = 1;
        c.insets.bottom = 2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        number1 = new JTextField();
        numberPanel.add(number1, c);
    }

    public PhonebookEditForm(PhonebookContact person) {
        this.getContentPane().add(expPanel);
        this.contact = person;

        addFirstNumber();

        addNumber.addActionListener(new ActionListener() {
            private void redrawList() {
                numberPanel.removeAll();
                addFirstNumber();
                numberGridCounter = 1;
                for (JComponent[] comps : numbers) {
                    GridBagConstraints c = new GridBagConstraints();
                    c.gridy = numberGridCounter;

                    c.fill = 1;
                    c.gridx = 0;
                    c.weightx = 0.99d;
                    c.insets.bottom = 2;
                    numberPanel.add(comps[0], c);

                    c.gridx = 1;
                    c.weightx = 0.01d;
                    c.insets.left = 5;
                    numberPanel.add(comps[1], c);

                    numberGridCounter++;
                }
                numberPanel.revalidate();
                numberPanel.repaint();
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                final JTextField numberField = new JTextField();
                final JButton removeNumber = new JButton(
                        new ImageIcon("res/Delete.png"));
                removeNumber.setBorderPainted(false);
                removeNumber.setContentAreaFilled(false);
                removeNumber.setPreferredSize(new Dimension(16, 16));

                JComponent[] row = new JComponent[]{numberField, removeNumber};

                removeNumber.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        numbers.remove(row);
                        redrawList();
                    }
                });
                numbers.add(row);

                redrawList();
            }
        });

        addField.addActionListener(new ActionListener() {
            private void redrawList() {
                additionalFieldsContainer.removeAll();
                fieldGridCounter = 0;
                for (JComponent[] comps : addFields) {
                    GridBagConstraints c = new GridBagConstraints();
                    c.gridy = fieldGridCounter;
                    c.insets.bottom = 2;

                    c.gridx = 0;
                    c.weightx = 0.2;
                    c.fill = 1;
                    c.insets.right = 5;
                    additionalFieldsContainer.add(comps[0], c);

                    c.gridx = 1;
                    c.weightx = 0.75;
                    additionalFieldsContainer.add(comps[1], c);

                    c.gridx = 2;
                    c.weightx = 0.05;
                    additionalFieldsContainer.add(comps[2], c);

                    fieldGridCounter++;
                }
                additionalFieldsContainer.revalidate();
                additionalFieldsContainer.repaint();
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField name = new JTextField();
                JTextField label = new JTextField();
                JButton delete = new JButton(new ImageIcon("res/Delete.png"));
                delete.setBorderPainted(false);
                delete.setContentAreaFilled(false);
                delete.setPreferredSize(new Dimension(16, 16));

                JComponent[] row = new JComponent[]{name, label, delete};
                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addFields.remove(row);
                        redrawList();
                    }
                });
                addFields.add(row);

                redrawList();
            }

        });
    }
}
