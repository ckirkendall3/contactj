package com.ckirkendall3.contacts.dataservice;

import com.ckirkendall3.contacts.dto.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Contacts data service bean
 */
@Service
public class ContactsDataservice {

    // Atomic reference to the list of contacts - this is being used as the temporary datastore instead of
    // a database. This can easily be swapped out for persistent storage of contact list.
    private final AtomicReference<List<Contact>> contacts = new AtomicReference<>(new ArrayList<>());

    /**
     * Insert a contact into the list of contacts
     *
     * @param contact contact to insert
     */
    public void insertContact(Contact contact) {
        List<Contact> updateList = new ArrayList<>(contacts.get());
        updateList.add(contact);
        contacts.set(Collections.unmodifiableList(updateList));
    }

    /**
     * Retrieve all the contacts
     *
     * @return List of all contacts
     */
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts.get());
    }
}
