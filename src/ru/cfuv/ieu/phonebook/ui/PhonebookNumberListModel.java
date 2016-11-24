package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.model.PhonebookNumber;
import ru.cfuv.ieu.phonebook.settings.PhonebookSettings;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PhonebookNumberListModel extends
        AbstractListModel<String> {
    private final List<PhonebookNumber> numbers = new ArrayList<>();
    private boolean format;

    public PhonebookNumberListModel(PhonebookSettings settings) {
        format = settings.getFormatting();
    }

    @Override
    public int getSize() {
        return numbers.size();
    }

    @Override
    public String getElementAt(int index) {
        return numbers.get(index).toString(format);
    }

    public void clear() {
        numbers.clear();
    }

    public void addNumber(PhonebookNumber number) {
        numbers.add(number);
        fireContentsChanged(this, numbers.size()-1, numbers.size()-1);
    }

    public void addNumbers(List<PhonebookNumber> numbers) {
        int start = numbers.size()-1;
        this.numbers.addAll(numbers);
        fireIntervalAdded(this, start, numbers.size()-1);
    }

    public void removeNumber(PhonebookNumber number) {
        int index = numbers.indexOf(number);
        numbers.remove(index);
        fireIntervalRemoved(this, index, index);
    }

    public PhonebookNumber getNumber(int index) {
        return numbers.get(index);
    }

    public void setFormat(boolean b) {
        format = b;
    }
}
