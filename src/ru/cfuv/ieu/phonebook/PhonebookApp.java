package ru.cfuv.ieu.phonebook;

import ru.cfuv.ieu.phonebook.settings.PhonebookSettings;
import ru.cfuv.ieu.phonebook.ui.PhonebookRoot;

import javax.swing.*;

public class PhonebookApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        PhonebookSettings settings = new PhonebookSettings();
        PhonebookRepository repo = new PhonebookRepository(settings);
        PhonebookRoot root = new PhonebookRoot(settings, repo);
        root.setVisible(true);
    }
}