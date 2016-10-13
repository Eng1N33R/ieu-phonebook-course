package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.PhonebookRepository;

import javax.swing.*;
import java.awt.*;

// TODO: Осмысленный интерфейс
public class PhonebookRoot {
    private final PhonebookRepository repo;

    public PhonebookRoot(PhonebookRepository repo) {
        this.repo = repo;
        JFrame f = new JFrame("Contact Manager");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());

        JButton b = new JButton("click");
        b.setBounds(130,100,100, 40);

        f.getContentPane().add(b);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);

        f.pack();
        f.setVisible(true);
    }
}
