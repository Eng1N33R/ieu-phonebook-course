package ru.cfuv.ieu.phonebook;

import ru.cfuv.ieu.phonebook.model.PhonebookContact;
import ru.cfuv.ieu.phonebook.model.PhonebookNumber;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhonebookRepository {
    private Connection connection;

    public PhonebookRepository() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" +
                    "phonebook.db");
            connection.setAutoCommit(false);
            setup();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setup() {
        try {
            if (execute("select count(*) from sqlite_master where" +
                    " type = 'table' and name != 'sqlite_sequence';")
                    .getInt(1) == 0) {
                executeUpdate("create table contacts " +
                        "(id integer primary key autoincrement," +
                        "name varchar(255))");
                executeUpdate("create table numbers " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "number varchar(255))");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanup() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet execute(String query) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void executeUpdate(String query) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateName(PhonebookContact contact, String name) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "update contacts set name = ? where id = ?");
            stmt.setString(1, name);
            stmt.setInt(2, contact.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNumber(PhonebookContact contact, PhonebookNumber number) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "insert into numbers (contact, number) values (?, ?)");
            stmt.setInt(1, contact.getId());
            stmt.setString(2, number.getNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeNumber(PhonebookContact contact, PhonebookNumber number) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "delete from numbers where contact = ? and number = ?");
            stmt.setInt(1, contact.getId());
            stmt.setString(2, number.getNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PhonebookContact> getContacts() {
        List<PhonebookContact> contacts = new ArrayList<>();
        ResultSet resContacts = execute("select * from contacts");
        try {
            while (resContacts.next()) {
                int id = resContacts.getInt(1);
                String name = resContacts.getString(2);
                List<PhonebookNumber> numbers = new ArrayList<>();
                ResultSet resNumbers = execute("select * from numbers where" +
                        " contact = " + id);
                while (resNumbers.next()) {
                    numbers.add(new PhonebookNumber(resNumbers.getString(3)));
                }
                resNumbers.close();

                PhonebookContact contact = new PhonebookContact(this, id, name,
                        numbers);
                contacts.add(contact);
            }
            resContacts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public void addContact(String name, String snumber) {
        PhonebookNumber number = new PhonebookNumber(snumber);
        addContact(name, new ArrayList<>(Collections.singletonList(number)));
    }

    public void addContact(String name, List<PhonebookNumber> numbers) {
        try {
            PreparedStatement insert = connection.prepareStatement(
                    "insert into contacts (name) values (?)");
            insert.setString(1, name);
            insert.executeUpdate();

            int rowid = execute("select last_insert_rowid()").getInt(1);

            for (PhonebookNumber number : numbers) {
                PreparedStatement insert2 = connection.prepareStatement(
                        "insert into numbers (contact, number) values (?, ?)");
                insert2.setInt(1, rowid);
                insert2.setString(2, number.getNumber());
                insert2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
