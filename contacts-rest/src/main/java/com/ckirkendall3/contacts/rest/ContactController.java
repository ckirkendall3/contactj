package com.ckirkendall3.contacts.rest;


import com.ckirkendall3.contacts.dto.Contact;
import com.ckirkendall3.contacts.service.ContactsService;
import com.ckirkendall3.contacts.service.ParseService;
import com.ckirkendall3.contacts.service.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contact Object
 */
@RestController
@RequestMapping("records")
public class ContactController {

    private ContactsService service;
    private ParseService parseService;

    @Autowired
    public ContactController(ContactsService service, ParseService parseService) {
        this.service = service;
        this.parseService = parseService;
    }

    @PostMapping
    public Contact insertContact(@RequestBody String entry) {
        Contact contact = parseService.parseContact(entry);
        service.insertContact(contact);
        return contact;
    }

    @GetMapping("gender")
    public List<Contact> getOrderedByGender() {
        return service.getSortedContacts(SortType.GENDER);
    }

    @GetMapping("birthdate")
    public List<Contact> getOrderedByBirthdate() {
        return service.getSortedContacts(SortType.DOB);
    }

    @GetMapping("name")
    public List<Contact> getOrderedByName() {
        return service.getSortedContacts(SortType.LASTNAME);
    }
}
