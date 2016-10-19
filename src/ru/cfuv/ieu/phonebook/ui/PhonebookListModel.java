package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.model.PhonebookNumber;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PhonebookListModel extends AbstractListModel<PhonebookNumber> {
    private List<PhonebookNumber> numbers = new ArrayList<>();

    public void addNumber(PhonebookNumber number) {
        numbers.add(number);
        fireIntervalAdded(this, numbers.size()-1, numbers.size()-1);
    }

    public void addNumbers(List<PhonebookNumber> numbers) {
        int index = numbers.size();
        this.numbers.addAll(numbers);
        fireIntervalAdded(this, index, numbers.size()-1);
    }

    public void clear() {
        fireIntervalRemoved(this, 0, Math.max(numbers.size()-1, 0));
        numbers.clear();
    }

    @Override
    public int getSize() {
        return numbers.size();
    }

    @Override
    public PhonebookNumber getElementAt(int index) {
        return numbers.get(index);
    }
}
