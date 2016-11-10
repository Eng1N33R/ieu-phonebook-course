package ru.cfuv.ieu.phonebook;

import ru.cfuv.ieu.phonebook.model.PhonebookContact;
import ru.cfuv.ieu.phonebook.model.PhonebookField;
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
                        "contact integer," +
                        "number varchar(255)," +
                        "FOREIGN KEY(contact) REFERENCES contacts(id))");
                executeUpdate("create table fields " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "contact integer, name varchar(255)," +
                        "value varchar(255)," +
                        "FOREIGN KEY(contact) REFERENCES contacts(id))");
            }
        } catch (Exception e) {
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

    public void updateName(PhonebookContact contact) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "update contacts set name = ? where id = ?");
            stmt.setString(1, contact.getName());
            stmt.setInt(2, contact.getId());
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
                PhonebookContact contact = new PhonebookContact(this, id, name);
                contacts.add(contact);
            }
            resContacts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public PhonebookContact addContact(String name,
                                       List<PhonebookNumber> numbers) {
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

            return new PhonebookContact(this, rowid, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeContact(PhonebookContact contact) {
        try {
            PreparedStatement delete = connection.prepareStatement(
                    "delete from numbers where contact = ?");
            delete.setInt(1, contact.getId());
            delete.executeUpdate();

            delete = connection.prepareStatement(
                    "delete from fields where contact = ?");
            delete.setInt(1, contact.getId());
            delete.executeUpdate();

            delete = connection.prepareStatement(
                    "delete from contacts where id = ?");
            delete.setInt(1, contact.getId());
            delete.executeUpdate();
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

    public void addNumbers(PhonebookContact c, List<PhonebookNumber> numbers) {
        for (PhonebookNumber n : numbers) {
            addNumber(c, n);
        }
    }

    public List<PhonebookNumber> getNumbers(PhonebookContact c) {
        List<PhonebookNumber> numbers = new ArrayList<>();
        try {
            ResultSet resNumbers = execute("select * from numbers where" +
                    " contact = " + c.getId());
            while (resNumbers.next()) {
                numbers.add(new PhonebookNumber(resNumbers.getInt(1),
                        resNumbers.getString(3)));
            }
            resNumbers.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    public void updateNumber(PhonebookContact contact, PhonebookNumber oldn,
                             PhonebookNumber newn) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "update numbers set number = ? where contact = ? and " +
                            "number = ?");
            stmt.setString(1, newn.getNumber());
            stmt.setInt(2, contact.getId());
            stmt.setString(3, oldn.getNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeNumber(PhonebookContact contact, PhonebookNumber number) {
        try {
            if (number.getId() != -1) {
                PreparedStatement stmt = connection.prepareStatement(
                        "delete from numbers where id = ?");
                stmt.setInt(1, number.getId());
                stmt.executeUpdate();
            } else {
                PreparedStatement stmt = connection.prepareStatement(
                        "delete from numbers where contact = ? and number = ?");
                stmt.setInt(1, contact.getId());
                stmt.setString(2, number.getNumber());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addField(PhonebookContact contact, String name, String value) {
        try {
            PreparedStatement insert = connection.prepareStatement(
                    "insert into fields (contact, name, value)" +
                            " values (?, ?, ?)");
            insert.setInt(1, contact.getId());
            insert.setString(2, name);
            insert.setString(3, value);
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PhonebookField> getFields(PhonebookContact contact) {
        List<PhonebookField> fields = new ArrayList<>();
        try {
            PreparedStatement get = connection.prepareStatement(
                    "select id, name, value from fields where contact = ?");
            get.setInt(1, contact.getId());
            ResultSet resFields = get.executeQuery();
            while (resFields.next()) {
                int id = resFields.getInt(1);
                String name = resFields.getString(2);
                String value = resFields.getString(3);
                PhonebookField field = new PhonebookField(this, id,
                        contact, name, value);
                fields.add(field);
            }
            resFields.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fields;
    }

    public void updateField(PhonebookField field) {
        try {
            PreparedStatement update = connection.prepareStatement(
                    "update fields set contact = ?, name = ?, value = ? " +
                            "where id = ?");
            update.setInt(1, field.getContact());
            update.setString(2, field.getName());
            update.setString(3, field.getValue());
            update.setInt(4, field.getId());
            update.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteField(PhonebookField field) {
        try {
            PreparedStatement delete = connection.prepareStatement(
                    "delete from fields where id = ?");
            delete.setInt(1, field.getId());
            delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
