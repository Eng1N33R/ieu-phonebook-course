package ru.cfuv.ieu.phonebook.model;

import ru.cfuv.ieu.phonebook.PhonebookRepository;


public class PhonebookField {
    private final PhonebookRepository repo;
    private final int internalId;
    private int contactId;
    private String name;
    private String value;

    public PhonebookField(PhonebookRepository repo, int id, PhonebookContact c,
                          String name, String value) {
        this.repo = repo;
        this.internalId = id;
        this.contactId = c.getId();
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return internalId;
    }

    public int getContact() {
        return contactId;
    }

    public void setContact(PhonebookContact c) {
        this.contactId = c.getId();
        repo.updateField(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        repo.updateField(this);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        repo.updateField(this);
    }
}
