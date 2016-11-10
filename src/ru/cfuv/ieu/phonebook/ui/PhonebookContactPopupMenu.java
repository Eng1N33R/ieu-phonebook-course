package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.PhonebookRepository;
import ru.cfuv.ieu.phonebook.model.PhonebookContact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PhonebookContactPopupMenu extends JPopupMenu {
    private final PhonebookRepository repo;
    private final PhonebookContact contact;

    public PhonebookContactPopupMenu(PhonebookRepository repo,
                                     PhonebookContact c) {
        this.repo = repo;
        this.contact = c;

        JMenuItem item1 = new JMenuItem("Редактировать...");
        JMenuItem item2 = new JMenuItem("Дублировать");
        JMenuItem item3 = new JMenuItem("Удалить...");

        item1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                PhonebookEditForm form = new PhonebookEditForm(repo, contact);
                form.pack();
                form.setSize(new Dimension(200, 200));
                form.setVisible(true);
                form.setLocationRelativeTo(null);
            }
        });

        item3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                delete(contact);
            }
        });

        add(item1);
        add(item2);
        add(new JSeparator());
        add(item3);
    }

    private void delete(PhonebookContact c) {
        int dialogResult = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите удалить контакт " + c.getName() + "?",
                "Удалить контакт", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_NO_OPTION) {
            System.out.println("deleting");
        }
    }
}
