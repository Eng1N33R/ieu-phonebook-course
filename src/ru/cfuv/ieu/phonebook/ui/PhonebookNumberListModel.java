package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.model.PhonebookNumber;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PhonebookNumberListModel extends
        AbstractListModel<PhonebookNumber> {
    private final List<PhonebookNumber> numbers = new ArrayList<>();

    @Override
    public int getSize() {
        return numbers.size();
    }

    @Override
    public PhonebookNumber getElementAt(int index) {
        return numbers.get(index);
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
}
