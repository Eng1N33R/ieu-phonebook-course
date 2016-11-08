package ru.cfuv.ieu.phonebook.model;

import ru.cfuv.ieu.phonebook.PhonebookRepository;

import java.util.List;

public class PhonebookContact {
    private final PhonebookRepository repo;
    private final int internalId;
    private String name;

    public PhonebookContact(PhonebookRepository repo, int id, String name,
                            List<PhonebookNumber> numbers) {
        this.repo = repo;
        this.internalId = id;
        this.name = name;
        if (numbers != null) repo.addNumbers(this, numbers);
    }

    public PhonebookContact(PhonebookRepository repo, int id, String name) {
        this(repo, id, name, null);
    }

    public int getId() {
        return internalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        repo.updateName(this);
    }

    public List<PhonebookNumber> getNumbers() {
        return repo.getNumbers(this);
    }

    public void addNumber(PhonebookNumber number) {
        repo.addNumber(this, number);
    }

    public List<PhonebookField> getFields() {
        return repo.getFields(this);
    }

    public void addField(String name, String value) {
        repo.addField(this, name, value);
    }
}
