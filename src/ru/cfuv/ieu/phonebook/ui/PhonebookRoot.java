package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.PhonebookRepository;
import ru.cfuv.ieu.phonebook.model.PhonebookContact;
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
import java.util.Collections;

public class PhonebookRoot extends JFrame {
    private final PhonebookRepository repo = new PhonebookRepository();
    private final PhonebookTableModel tableModel = new PhonebookTableModel();
    private final PhonebookListModel listModel = new PhonebookListModel();

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

    private class TableRightClickListener extends MouseAdapter {
        public void mouseReleased(MouseEvent e){
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
                        "Тест Тестович", null);
                PhonebookEditForm exp = new PhonebookEditForm(mock);
                exp.pack();
                exp.setSize(new Dimension(200, 200));
                exp.setVisible(true);
                exp.setLocationRelativeTo(null);
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
                addDisplayNumber(new PhonebookNumber("447710476771"));
            }
        });

        numberDisplay.setModel(listModel);

        table1.setModel(tableModel);
        tableModel.addContact(new PhonebookContact(repo, 1, "Тестов Тест Тестович",
                Arrays.asList(new PhonebookNumber("79787064535"), new PhonebookNumber("6581636523"),
                        new PhonebookNumber("14033509099"), new PhonebookNumber("8615999529217"),
                        new PhonebookNumber("4915223000305"), new PhonebookNumber("447399139404"),
                        new PhonebookNumber("380953954724"))));
        table1.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        contactInfoPanel.setVisible(true);
                        nameDisplay.setText(tableModel.getContact(
                                table1.getSelectedRow()).getName());
                        listModel.clear();
                        listModel.addNumbers(tableModel.getContact(
                                table1.getSelectedRow()).getNumbers());
                        editField.setEnabled(true);
                        copyField.setEnabled(true);
                        deleteField.setEnabled(true);
                    }
                });
        table1.addMouseListener(new TableRightClickListener());
    }

    private void delete(PhonebookContact c) {
        int dialogResult = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите удалить контакт " + c.getName() + "?",
                "Удалить контакт", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_NO_OPTION) {
            System.out.println("deleting");
        }
    }

    private void addDisplayNumber(PhonebookNumber number) {
        listModel.addNumber(number);
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
