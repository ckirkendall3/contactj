package com.ckirkendall3.contacts.dataservice;


import com.ckirkendall3.contacts.dto.Contact;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Contacts service bean
 */
public class TestContactsDataservice {

    @Test
    public void TestDataservice() {
        ContactsDataservice dataservice = new ContactsDataservice();

        Contact contact1 = new Contact();
        contact1.setFirstName("Bob");
        contact1.setLastName("Ross");
        contact1.setGender("male");
        contact1.setFavoriteColor("sage");
        contact1.setDateOfBirth(LocalDate.of(1942, 10, 29));

        dataservice.insertContact(contact1);
        List<Contact> contacts = dataservice.getAllContacts();
        Assert.assertEquals(1, contacts.size());
        Assert.assertEquals("Bob", contacts.get(0).getFirstName());
        Assert.assertEquals("sage", contacts.get(0).getFavoriteColor());

        Contact contact2 = new Contact();
        contact2.setFirstName("Julia");
        contact2.setLastName("Child");
        contact2.setGender("female");
        contact2.setFavoriteColor("yellow");
        contact2.setDateOfBirth(LocalDate.of(1912, 8, 15));

        dataservice.insertContact(contact2);
        contacts = dataservice.getAllContacts();
        Assert.assertEquals(2, contacts.size());

        Set<String> names = contacts.stream().map(Contact::getLastName).collect(Collectors.toSet());
        Assert.assertTrue(names.contains("Ross"));
        Assert.assertTrue(names.contains("Child"));
    }

}
