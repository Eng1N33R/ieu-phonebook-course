package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.PhonebookRepository;
import ru.cfuv.ieu.phonebook.model.PhonebookContact;
import ru.cfuv.ieu.phonebook.model.PhonebookField;
import ru.cfuv.ieu.phonebook.model.PhonebookNumber;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

@SuppressWarnings("unused")
public class PhonebookRoot extends JFrame {
    private final PhonebookRepository repo = new PhonebookRepository();
    private final PhonebookTableModel tableModel = new PhonebookTableModel();
    private final PhonebookNumberListModel listModel =
            new PhonebookNumberListModel();

    private JButton addField;
    private JTable table1;
    private JPanel rootpanel;
    private JToolBar toolbar1;
    private JButton editField;
    private JButton copyField;
    private JButton deleteField;
    private JButton settingsBtn;
    private JPanel contactInfoPanel;
    private JLabel nameDisplay;
    private JList<PhonebookNumber> numberDisplay;
    private JLabel nameLabel;
    private JLabel numberLabel;
    private JPanel additionalFields;
    private JPanel additionalFieldsContainer;

    private class TableRightClickListener extends MouseAdapter {
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                int r = table1.rowAtPoint(e.getPoint());
                if (r >= 0 && r < table1.getRowCount()) {
                    table1.setRowSelectionInterval(r, r);
                } else {
                    table1.clearSelection();
                }

                PhonebookContactPopupMenu m = new PhonebookContactPopupMenu(
                        repo, tableModel.getContact(table1.getSelectedRow()));
                m.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public PhonebookRoot() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setIconImage((new ImageIcon("res/Person.png")).getImage());
        this.setTitle("Contact Manager");
        this.getContentPane().add(rootpanel);
        addField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PhonebookContact mock = new PhonebookContact(null, 0,
                        "Иван Новый", null);
                editContact(mock);
            }
        });
        deleteField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete(tableModel.getContact(table1.getSelectedRow()));
            }
        });
        copyField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDisplayNumber(new PhonebookNumber("79787064535"));
            }
        });
        numberDisplay.setModel(listModel);

        final int[] i = new int[]{2};
        editField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editContact(tableModel.getContact(table1.getSelectedRow()));
            }
        });

        table1.setModel(tableModel);
        PhonebookContact contact = new PhonebookContact(repo, 1, "Тест Тестович",
                Arrays.asList(new PhonebookNumber("79787064535"),
                        new PhonebookNumber("79787064535"),
                        new PhonebookNumber("79787064535"),
                        new PhonebookNumber("79787064535"),
                        new PhonebookNumber("79787064535"),
                        new PhonebookNumber("79787064535"),
                        new PhonebookNumber("79787064535"),
                        new PhonebookNumber("79787064535"),
                        new PhonebookNumber("79787064535")));
        contact.addField("Email", "painttool@gmail.com");
        contact.addField("Адрес", "<html>Россия, Симферополь, ул. Комсомольская, д. 6, кв. 57</html>");
        tableModel.addContact(contact);

        table1.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        displayContact(
                                tableModel.getContact(table1.getSelectedRow()));
                    }
                });
        table1.addMouseListener(new TableRightClickListener());
    }

    private void editContact(PhonebookContact c) {
        PhonebookEditForm exp = new PhonebookEditForm(c);
        exp.pack();
        exp.setSize(new Dimension(400, 200));
        exp.setVisible(true);
        exp.setLocationRelativeTo(null);


    }

    private void displayContact(PhonebookContact c) {
        contactInfoPanel.setVisible(true);
        editField.setEnabled(true);
        copyField.setEnabled(true);
        deleteField.setEnabled(true);

        nameDisplay.setText(c.getName());
        listModel.clear();
        listModel.addNumbers(c.getNumbers());

        java.util.List<PhonebookField> fields = c.getFields();
        GridBagConstraints constr = new GridBagConstraints();
        constr.anchor = GridBagConstraints.WEST;
        additionalFieldsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        int i = 0;
        for (PhonebookField field : fields) {
            constr.gridy = i;
            constr.gridx = 0;
            constr.insets.right = 15;
            JLabel name = new JLabel(field.getName());
            additionalFieldsContainer.add(name, constr);

            constr.gridx = 1;
            constr.insets.right = 0;
            JLabel value = new JLabel(field.getValue());
            additionalFieldsContainer.add(value, constr);

            i++;
        }
    }

    private void addDisplayNumber(PhonebookNumber number) {
        listModel.addNumber(number);
    }

    private void delete(PhonebookContact c) {
        int dialogResult = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите удалить контакт " + c.getName() + "?",
                "Удалить контакт", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_NO_OPTION) {
            System.out.println("deleting");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // write your code here
            PhonebookRoot test = new PhonebookRoot();
            test.pack();
            test.setSize(new Dimension(800, 600));
            test.setLocationRelativeTo(null);
            test.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
