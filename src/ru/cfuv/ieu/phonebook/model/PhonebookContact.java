package ru.cfuv.ieu.phonebook.model;

import ru.cfuv.ieu.phonebook.PhonebookRepository;

import java.util.List;

public class PhonebookContact {
    private final PhonebookRepository repo;
    private final int internalId;
    private String name;
    private final List<PhonebookNumber> numbers;

    public PhonebookContact(PhonebookRepository repo, int id, String name,
                            List<PhonebookNumber> numbers) {
        this.repo = repo;
        this.internalId = id;
        this.name = name;
        this.numbers = numbers;
    }

    public int getId() {
        return internalId;
    }

    public void setName(String name) {
        repo.updateName(this, name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addNumber(PhonebookNumber number) {
        repo.addNumber(this, number);
    }

    public List<PhonebookNumber> getNumbers() {
        return numbers;
    }
}
