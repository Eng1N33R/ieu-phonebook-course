package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.model.PhonebookContact;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PhonebookContactPopupMenu extends JPopupMenu {
    public PhonebookContactPopupMenu(PhonebookRoot root, PhonebookContact c) {
        JMenuItem item1 = new JMenuItem("Редактировать...");
        JMenuItem item2 = new JMenuItem("Дублировать");
        JMenuItem item3 = new JMenuItem("Удалить...");

        item1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                root.editContact(c);
            }
        });
        item2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                root.copyContact(c);
            }
        });
        item3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                root.deleteContact(c);
            }
        });

        add(item1);
        add(item2);
        add(new JSeparator());
        add(item3);
    }
}
