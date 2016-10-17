package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.model.PhonebookContact;
import ru.cfuv.ieu.phonebook.model.PhonebookNumber;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PhonebookTableModel extends AbstractTableModel {
    private final List<PhonebookContact> contacts = new ArrayList<>();

    public void addContact(PhonebookContact contact) {
        contacts.add(contact);
    }

    public void removeContact(PhonebookContact contact) {
        int index = contacts.indexOf(contact);
        contacts.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public PhonebookContact getContact(int index) {
        return contacts.get(index);
    }

    @Override
    public int getRowCount() {
        return contacts.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Имя";
            case 1:
                return "Номер";
            default: throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PhonebookContact c = contacts.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getName();
            case 1:
                List<PhonebookNumber> numbers = c.getNumbers();
                String text = numbers.get(0).toString();
                if (numbers.size()-1 > 0) text += " (и ещё "
                        + (numbers.size()-1) + ")";
                return text;
            default: throw new IndexOutOfBoundsException();
        }
    }
}
