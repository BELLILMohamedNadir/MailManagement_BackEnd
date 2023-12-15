package com.example.MailManagementApi.service;

import com.example.MailManagementApi.model.Contact;

import java.util.List;

public interface ContactService {

    Contact createContact(Contact contact);
    void deleteContact(long id);
    Contact updateContact(long id,Contact contact);
    List<Contact> getAllContact();
}
