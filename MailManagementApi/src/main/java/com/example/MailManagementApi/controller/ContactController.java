package com.example.MailManagementApi.controller;


import com.example.MailManagementApi.model.Contact;
import com.example.MailManagementApi.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contact")
@AllArgsConstructor
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/create")
    public Contact createContact(@RequestBody Contact contact){
        return contactService.createContact(contact);
    }

    @GetMapping("/read")
    public List<Contact> getAllContact(){
        return contactService.getAllContact();
    }

    @PutMapping("/update/{id}")
    public void updateContact(@PathVariable long id,@RequestBody Contact contact){
        contactService.updateContact(id,contact);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteContact(@PathVariable long id){
         contactService.deleteContact(id);
    }


}
