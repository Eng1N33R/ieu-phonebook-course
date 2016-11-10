package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.PhonebookRepository;
import ru.cfuv.ieu.phonebook.model.PhonebookContact;
import ru.cfuv.ieu.phonebook.model.PhonebookField;
import ru.cfuv.ieu.phonebook.model.PhonebookNumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused", "Convert2Lambda"})
public class PhonebookEditForm extends JDialog {
    public static final int YES = 0;
    public static final int NO = -1;

    private final PhonebookRepository repo;
    private final PhonebookContact contact;
    private final java.util.List<JTextField> numbersToAdd
            = new ArrayList<>();
    private final Map<JTextField, String> numbersToEdit
            = new HashMap<>();
    private final java.util.List<JTextField[]> fieldsToAdd
            = new ArrayList<>();
    private final Map<Object[], String[]> fieldsToEdit
            = new HashMap<>();

    private int result = NO;

    private JPanel expPanel;
    private JPanel additionalFields;
    private JPanel additionalFieldsContainer;
    private JPanel numberPanel;
    private JTextField nameField;
    private JTextField number1;
    private JButton addNumber;
    private JButton addField;
    private JButton cancel;
    private JButton okay;
    private boolean numbersAdded = false;
    private boolean fieldsAdded = false;
    private java.util.List<Object[]> numbers = new ArrayList<>();
    private java.util.List<Object[]> addFields = new ArrayList<>();

    private void addExistingNumbers() {
        numberPanel.removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0d;
        c.weighty = 1.0d;
        c.fill = 1;
        c.insets.bottom = 2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        number1 = new JTextField();
        numberPanel.add(number1, c);

        if (contact != null) {
            java.util.List<PhonebookNumber> cnumbers = contact.getNumbers();
            System.out.println(cnumbers.size());
            number1.setText(cnumbers.get(0).getNumber());
            numbersToEdit.put(number1, number1.getText());

            if (!numbersAdded) {
                for (int i = 1; i < cnumbers.size(); i++) {
                    final PhonebookNumber number = cnumbers.get(i);
                    final JTextField numberField = new JTextField();
                    numberField.setText(number.getNumber());
                    final JButton removeNumber = new JButton(
                            new ImageIcon("res/Delete.png"));
                    removeNumber.setBorderPainted(false);
                    removeNumber.setContentAreaFilled(false);
                    removeNumber.setFocusPainted(false);
                    removeNumber.setPreferredSize(new Dimension(16, 16));

                    Object[] row = new Object[]{number.getId(), numberField,
                            removeNumber};

                    removeNumber.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            numbers.remove(row);
                            repo.removeNumber(contact, number);
                            redrawNumbers();
                        }
                    });
                    numbers.add(row);
                    numbersToEdit.put(numberField, number.getNumber());
                }
                numbersAdded = true;
            }
        }
    }

    private void addExistingFields() {
        additionalFieldsContainer.removeAll();

        if (contact != null) {
            java.util.List<PhonebookField> cfields = contact.getFields();

            if (!fieldsAdded) {
                for (int i = 0; i < cfields.size(); i++) {
                    final PhonebookField field = cfields.get(i);
                    JTextField name = new JTextField();
                    name.setText(field.getName());
                    JTextField label = new JTextField();
                    label.setText(field.getValue());
                    JButton delete = new JButton(new ImageIcon(
                            "res/Delete.png"));
                    delete.setBorderPainted(false);
                    delete.setContentAreaFilled(false);
                    delete.setFocusPainted(false);
                    delete.setPreferredSize(new Dimension(16, 16));

                    Object[] row = new Object[]{field.getId(), name, label,
                            delete};

                    delete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            addFields.remove(row);
                            repo.deleteField(field);
                            redrawFields();
                        }
                    });
                    addFields.add(row);
                    fieldsToEdit.put(new Object[]{field.getId(), name, label},
                            new String[]{field.getName(), field.getValue()});
                }
                fieldsAdded = true;
            }
        }
    }

    private void redrawNumbers() {
        addExistingNumbers();
        int numberGridCounter = 1;
        for (Object[] comps : numbers) {
            GridBagConstraints c = new GridBagConstraints();
            c.gridy = numberGridCounter;
            c.weighty = 1.0d;

            c.fill = 1;
            c.gridx = 0;
            c.weightx = 0.99d;
            c.insets.bottom = 2;
            numberPanel.add((JComponent) comps[1], c);

            c.gridx = 1;
            c.weightx = 0.01d;
            c.insets.left = 5;
            numberPanel.add((JComponent) comps[2], c);

            numberGridCounter++;
        }
        numberPanel.revalidate();
        numberPanel.repaint();
    }

    private void redrawFields() {
        addExistingFields();
        int fieldGridCounter = 0;
        for (Object[] comps : addFields) {
            GridBagConstraints c = new GridBagConstraints();
            c.gridy = fieldGridCounter;
            c.insets.bottom = 2;

            c.gridx = 0;
            c.weightx = 0.2;
            c.fill = 1;
            c.insets.right = 5;
            additionalFieldsContainer.add((JComponent) comps[1], c);

            c.gridx = 1;
            c.weightx = 0.79;
            c.insets.right = 5;
            additionalFieldsContainer.add((JComponent) comps[2], c);

            c.gridx = 2;
            c.weightx = 0.01;
            additionalFieldsContainer.add((JComponent) comps[3], c);

            fieldGridCounter++;
        }
        additionalFieldsContainer.revalidate();
        additionalFieldsContainer.repaint();
    }

    private void close() {
        this.setVisible(false);
        this.dispose();
    }

    public PhonebookEditForm(PhonebookRepository repo, PhonebookContact c) {
        this.contact = c;
        this.repo = repo;

        if (c != null) nameField.setText(c.getName());

        okay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contact != null) {
                    contact.setName(nameField.getText());

                    for (JTextField num : numbersToAdd) {
                        contact.addNumber(new PhonebookNumber(num.getText()));
                    }
                    for (JTextField num : numbersToEdit.keySet()) {
                        repo.updateNumber(contact,
                                new PhonebookNumber(numbersToEdit.get(num)),
                                new PhonebookNumber(num.getText()));
                    }

                    for (JTextField[] field : fieldsToAdd) {
                        repo.addField(contact, field[0].getText(),
                                field[1].getText());
                    }
                    for (Object[] field : fieldsToEdit.keySet()) {
                        PhonebookField f = new PhonebookField(repo,
                                (int) field[0], contact,
                                ((JTextField) field[1]).getText(),
                                ((JTextField) field[2]).getText());
                        repo.updateField(f);
                    }
                } else {
                    numbersToAdd.add(number1);
                    java.util.List<PhonebookNumber> numbers = new ArrayList<>();
                    for (JTextField t : numbersToAdd) {
                        numbers.add(new PhonebookNumber(t.getText()));
                    }
                    PhonebookContact nContact = repo.addContact(
                            nameField.getText(), numbers);
                    for (JTextField[] t : fieldsToAdd) {
                        nContact.addField(t[0].getText(), t[1].getText());
                    }
                }

                close();
                result = YES;
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });

        addExistingNumbers();
        redrawNumbers();

        addNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JTextField numberField = new JTextField();
                final JButton removeNumber = new JButton(
                        new ImageIcon("res/Delete.png"));
                removeNumber.setBorderPainted(false);
                removeNumber.setContentAreaFilled(false);
                removeNumber.setFocusPainted(false);
                removeNumber.setPreferredSize(new Dimension(16, 16));

                Object[] row = new Object[]{-1, numberField, removeNumber};

                removeNumber.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        numbers.remove(row);
                        numbersToAdd.remove(numberField);
                        redrawNumbers();
                    }
                });
                numbers.add(row);
                numbersToAdd.add(numberField);

                redrawNumbers();
            }
        });

        addExistingFields();
        redrawFields();

        addField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField name = new JTextField();
                JTextField label = new JTextField();
                JButton delete = new JButton(new ImageIcon("res/Delete.png"));
                delete.setBorderPainted(false);
                delete.setContentAreaFilled(false);
                delete.setFocusPainted(false);
                delete.setPreferredSize(new Dimension(16, 16));

                Object[] row = new Object[]{-1, name, label, delete};
                JTextField[] add = new JTextField[]{name, label};
                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addFields.remove(row);
                        fieldsToAdd.remove(add);
                        redrawFields();
                    }
                });
                addFields.add(row);
                fieldsToAdd.add(add);

                redrawFields();
            }

        });
    }

    public int display() {
        this.getContentPane().add(expPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setModal(true);
        this.setSize(new Dimension(400, 260));
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        return result;
    }

    public PhonebookEditForm(PhonebookRepository repo) {
        this(repo, null);
    }
}
