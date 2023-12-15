package com.example.MailManagementApi.service;

import com.example.MailManagementApi.exception.IdNotFoundException;
import com.example.MailManagementApi.model.Contact;
import com.example.MailManagementApi.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public void deleteContact(long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public Contact updateContact(long id,Contact contact) {
        return contactRepository.findById(id)
                .map(c->{
                        c.setName(contact.getName());
                        c.setFirstName(contact.getFirstName());
                        c.setStructure(contact.getStructure());
                        c.setEmail(contact.getEmail());
                        return contactRepository.save(c);
                }).orElseThrow(()->new IdNotFoundException("Invalid Id"));
    }

    @Override
    public List<Contact> getAllContact() {
        return contactRepository.findAll();
    }

}
