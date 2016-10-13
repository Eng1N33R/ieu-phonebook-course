package ru.cfuv.ieu.phonebook;

import ru.cfuv.ieu.phonebook.model.PhonebookContact;
import ru.cfuv.ieu.phonebook.model.PhonebookNumber;
import ru.cfuv.ieu.phonebook.ui.PhonebookRoot;

import javax.swing.*;
import java.util.List;

public class PhonebookApp {
    private static void setup() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //setup();
        //PhonebookRoot root = new PhonebookRoot(new PhonebookRepository());
        PhonebookRepository repo = new PhonebookRepository();
        List<PhonebookContact> contacts = repo.getContacts();
        System.out.println("Got " + contacts.size() + " contacts");
        for (PhonebookContact contact : contacts) {
            System.out.println(contact.getName());
            for (PhonebookNumber number : contact.getNumbers()) {
                System.out.println("\t" + number.toString());
            }
            System.out.println("---------------");
        }
        //System.out.println(repo.execute("select * from contacts"));
    }
}