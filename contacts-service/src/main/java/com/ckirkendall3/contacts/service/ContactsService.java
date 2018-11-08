
package com.ckirkendall3.contacts.service;

import com.ckirkendall3.contacts.dataservice.ContactsDataservice;
import com.ckirkendall3.contacts.dto.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Contacts service bean
 */
@Service
public class ContactsService {

    private ContactsDataservice dataservice;

    @Autowired
    public ContactsService(ContactsDataservice dataservice) {
        this.dataservice = dataservice;
    }

    public void insertContact(Contact contact) {
        dataservice.insertContact(contact);
    }

    public List<Contact> getSortedContacts(SortType sortBy) {
        List<Contact> contacts = dataservice.getAllContacts();
        contacts.sort(getComparator(sortBy));
        return contacts;
    }

    private Comparator<Contact> getComparator(SortType sortBy) {
        Comparator<Contact> comparator;
        switch(sortBy) {
            case GENDER:
                comparator = new SortbyGender();
                break;
            case DOB:
                comparator = new SortbyDOB();
                break;
            case LASTNAME:
            default:
                comparator = new SortbyLastName();
                break;
        }
        return comparator;
    }

    /**
     * Comparator for sorting by gender.
     */
    private class SortbyGender implements Comparator<Contact>
    {
        public int compare(Contact a, Contact b)
        {
            int retval = a.getGender().compareTo(b.getGender());
            return retval == 0?a.getLastName().compareTo(b.getLastName()):retval;
        }
    }

    /**
     * Comparator for sorting by gender.
     */
    private class SortbyDOB implements Comparator<Contact>
    {
        public int compare(Contact a, Contact b)
        {
            return a.getDateOfBirth().compareTo(b.getDateOfBirth());
        }
    }

    /**
     * Comparator for sorting by gender.
     */
    private class SortbyLastName implements Comparator<Contact>
    {
        public int compare(Contact a, Contact b)
        {
            return b.getLastName().compareTo(a.getLastName());
        }
    }
}
