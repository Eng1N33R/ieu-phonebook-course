package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.PhonebookRepository;
import ru.cfuv.ieu.phonebook.model.PhonebookContact;
import ru.cfuv.ieu.phonebook.model.PhonebookField;
import ru.cfuv.ieu.phonebook.model.PhonebookNumber;
import ru.cfuv.ieu.phonebook.settings.PhonebookSettings;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

@SuppressWarnings({"unused", "Convert2Lambda"})
public class PhonebookRoot extends JFrame {
    private final PhonebookSettings settings = new PhonebookSettings();
    private final PhonebookRepository repo = new PhonebookRepository(settings);
    private final PhonebookTableModel tableModel =
            new PhonebookTableModel(settings);
    private final PhonebookNumberListModel listModel =
            new PhonebookNumberListModel(settings);

    private JButton addField;
    private JTable table1;
    private JPanel rootpanel;
    private JToolBar toolbar1;
    private JButton editField;
    private JButton copyField;
    private JButton deleteField;
    private JButton settingsBtn;
    private JPanel contactInfoPanel;
    private JLabel contactTitle;
    private JLabel nameDisplay;
    private JList numberDisplay;
    private JLabel nameLabel;
    private JLabel numberLabel;
    private JPanel additionalFields;
    private JPanel additionalFieldsContainer;
    private JPanel menuPanel;

    private static final int TCOL = 0;
    private static final int TROW = 1;
    private static final int CONTACT = 2;
    private static final int LABEL = 3;
    private static final int MTOP = 4;
    private static final int MCONTEXT = 5;
    private double scale;
    private final Font tcolFont = table1.getTableHeader().getFont();
    private final Font trowFont = table1.getFont();
    private final Font contactFont = contactTitle.getFont();
    private final Font labelFont = nameLabel.getFont();
    private Font mtopFont;
    private Font mcontextFont;
    private Font borderFont;

    private void setScale(JComponent c, int type) {
        if (type == TCOL) {
            c.setFont(new Font(tcolFont.getName(),
                    tcolFont.getStyle(), (int) (tcolFont.getSize() * scale)));
        } else if (type == TROW) {
            c.setFont(new Font(trowFont.getName(),
                    trowFont.getStyle(), (int) (trowFont.getSize() * scale)));
        } else if (type == CONTACT) {
            c.setFont(new Font(contactFont.getName(),
                    contactFont.getStyle(),
                    (int) (contactFont.getSize() * scale)));
        } else if (type == LABEL) {
            c.setFont(new Font(labelFont.getName(),
                    labelFont.getStyle(), (int) (labelFont.getSize() * scale)));
        } else if (type == MTOP) {
            c.setFont(new Font(mtopFont.getName(),
                    mtopFont.getStyle(), (int) (mtopFont.getSize() * scale)));
        } else if (type == MCONTEXT) {
            c.setFont(new Font(mcontextFont.getName(),
                    mcontextFont.getStyle(),
                    (int) (mcontextFont.getSize() * scale)));
        }
    }

    private void refreshScales() {
        scale = ((double) settings.getFontScale()) / 100;

        setScale(table1.getTableHeader(), TCOL);
        setScale(table1, TROW);
        table1.repaint();
        setScale(numberDisplay, TROW);
        setScale(contactTitle, CONTACT);
        setScale(nameLabel, LABEL);
        setScale(nameDisplay, LABEL);
        setScale(numberLabel, LABEL);
        TitledBorder tb = (TitledBorder) additionalFields.getBorder();
        if (borderFont == null) borderFont = tb.getTitleFont();
        tb.setTitleFont(new Font(borderFont.getName(),
                borderFont.getStyle(), (int) (borderFont.getSize() * scale)));
        contactInfoPanel.repaint();

        Component[] menus = ((JMenuBar) menuPanel.getComponent(0))
                .getComponents();
        for (Component m : menus) {
            JMenu menu = (JMenu) m;
            setScale(menu, MTOP);
            for (Component mi : menu.getMenuComponents()) {
                if (mi instanceof JMenuItem) setScale((JMenuItem) mi, MCONTEXT);
            }
        }

        for (Component c : additionalFieldsContainer.getComponents()) {
            setScale(((JLabel) c), LABEL);
        }
        additionalFieldsContainer.repaint();
    }

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
                        PhonebookRoot.this, tableModel.getContact(
                        table1.getSelectedRow()));
                for (Component mi : m.getComponents()) {
                    if (mi instanceof JMenuItem)
                        setScale((JMenuItem) mi, MCONTEXT);
                }
                m.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private void rebuildTable() {
        tableModel.clear();
        java.util.List<PhonebookContact> contacts = repo.getContacts();
        for (PhonebookContact c : contacts) {
            tableModel.addContact(c);
        }
        table1.revalidate();
        table1.repaint();
    }

    public PhonebookRoot() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setIconImage((new ImageIcon("res/Person.png")).getImage());
        this.setTitle("Адресная книга");
        this.getContentPane().add(rootpanel);
        buildMenu();
        addField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createContact();
            }
        });
        editField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if (row != -1) editContact(tableModel.getContact(row));
            }
        });
        deleteField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table1.getSelectedRows();
                if (rows.length > 0) {
                    deleteContacts(rows);
                }
            }
        });
        copyField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if (row != -1) copyContact(tableModel.getContact(row));
            }
        });
        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSettings();
            }
        });
        numberDisplay.setModel(listModel);

        table1.setModel(tableModel);
        PhonebookContact contact = repo.addContact("Тест Тестович",
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
        contact.addField("Адрес", "ул. Комсомольская, д. 6, кв. 57");

        table1.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    int[] rows = table1.getSelectedRows();
                    if (rows.length > 0)
                        deleteContacts(rows);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        table1.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int i = table1.getSelectedRow();
                        if (i >= 0) displayContact(tableModel.getContact(i));
                    }
                });
        table1.addMouseListener(new TableRightClickListener());
        rebuildTable();
        refreshScales();
    }

    private void buildMenu() {
        JMenuBar mainMenu = new JMenuBar();
        mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenu.setAlignmentY(Component.CENTER_ALIGNMENT);
        menuPanel.add(mainMenu);

        JMenu file = new JMenu("Файл");
        if (mtopFont == null) mtopFont = file.getFont();
        file.setMargin(new Insets(0, 5, 0, 5));

        JMenuItem settings = new JMenuItem("Настройки...");
        if (mcontextFont == null) mcontextFont = settings.getFont();
        JMenuItem quit = new JMenuItem("Выход");

        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSettings();
            }
        });
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PhonebookRoot.this.setVisible(false);
                PhonebookRoot.this.dispose();
            }
        });

        file.add(settings);
        file.add(new JSeparator());
        file.add(quit);

        JMenu edit = new JMenu("Правка");
        edit.setMargin(new Insets(0, 5, 0, 5));
        JMenuItem editc = new JMenuItem("Редактировать контакт...");
        JMenuItem copy = new JMenuItem("Дублировать контакт");
        JMenuItem delete = new JMenuItem("Удалить контакт...");
        editc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if (row != -1) editContact(tableModel.getContact(row));
            }
        });
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if (row != -1) copyContact(tableModel.getContact(row));
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table1.getSelectedRows();
                if (rows.length > 0)
                    deleteContacts(rows);
            }
        });

        edit.add(editc);
        edit.add(copy);
        edit.add(delete);

        mainMenu.add(file);
        mainMenu.add(edit);
    }

    private void createContact() {
        int result = (new PhonebookEditForm(repo)).display();
        if (result == PhonebookEditForm.YES) {
            rebuildTable();
        }
    }

    public void editContact(PhonebookContact c) {
        int result = (new PhonebookEditForm(repo, c)).display();
        if (result == PhonebookEditForm.YES) {
            rebuildTable();
            displayContact(c);
        }
    }

    private void openSettings() {
        int result = (new PhonebookSettingsForm(settings)).display();
        if (result == PhonebookSettingsForm.YES) {
            listModel.setFormat(settings.getFormatting());
            tableModel.setFormat(settings.getFormatting());
            numberDisplay.repaint();
            refreshScales();
        }
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
        additionalFieldsContainer.removeAll();
        additionalFieldsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        int i = 0;
        for (PhonebookField field : fields) {
            constr.gridy = i;
            constr.gridx = 0;
            constr.insets.right = 15;
            JLabel name = new JLabel(field.getName());
            setScale(name, LABEL);
            additionalFieldsContainer.add(name, constr);

            constr.gridx = 1;
            constr.insets.right = 0;
            JLabel value = new JLabel(field.getValue());
            setScale(value, LABEL);
            additionalFieldsContainer.add(value, constr);

            i++;
        }
        additionalFieldsContainer.revalidate();
        additionalFieldsContainer.repaint();
    }

    public void copyContact(PhonebookContact c) {
        PhonebookContact nc = repo.addContact(c.getName(), c.getNumbers());
        List<PhonebookField> fields = c.getFields();
        for (PhonebookField f : fields) {
            nc.addField(f.getName(), f.getValue());
        }
        rebuildTable();
        int i = tableModel.findRowById(nc.getId());
        table1.setRowSelectionInterval(i, i);
    }

    public void deleteContact(PhonebookContact c) {
        int dialogResult = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите удалить контакт " + c.getName() + "?",
                "Удалить контакт", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_NO_OPTION) {
            repo.removeContact(c);
            repo.commitTransaction();
            contactInfoPanel.setVisible(false);
            rebuildTable();
        }
    }

    public void deleteContacts(int[] rows) {
        int dialogResult;
        List<PhonebookContact> contacts = new ArrayList<>();
        if (rows.length == 1) {
            PhonebookContact c = tableModel.getContact(rows[0]);
            contacts.add(c);
            dialogResult = JOptionPane.showConfirmDialog(this,
                    "Вы уверены, что хотите удалить контакт " + c.getName() + "?",
                    "Удалить контакт", JOptionPane.YES_NO_OPTION);
        } else {
            for (int r : rows) {
                contacts.add(tableModel.getContact(r));
            }
            dialogResult = JOptionPane.showConfirmDialog(this,
                    "Вы уверены, что хотите удалить " + rows.length
                            + " выделенных контакта(-ов)?",
                    "Удалить контакт", JOptionPane.YES_NO_OPTION);
        }
        if (dialogResult == JOptionPane.YES_NO_OPTION) {
            for (PhonebookContact c : contacts) repo.removeContact(c);
            repo.commitTransaction();
            contactInfoPanel.setVisible(false);
            rebuildTable();
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
