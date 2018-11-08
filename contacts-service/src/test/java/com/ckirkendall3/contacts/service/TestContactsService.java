package com.ckirkendall3.contacts.service;

import com.ckirkendall3.contacts.dataservice.ContactsDataservice;
import com.ckirkendall3.contacts.dto.Contact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

/**
 * Contacts service bean
 */
public class TestContactsService {

    private final ContactsDataservice dataservice = new ContactsDataservice();
    private final ContactsService service = new ContactsService(dataservice);
    private final ParseService parseService = new ParseService();

    @Before
    public void initialize() {
        service.insertContact(
                parseService.parseContact("Reese, John, male, black, 9/26/1968"));
        service.insertContact(
                parseService.parseContact("Finch, Harold, male, gray, 9/7/1954"));
        service.insertContact(
                parseService.parseContact("Shaw, Sameen, female, red, 1/10/1980"));
        service.insertContact(
                parseService.parseContact("Carter, Joss, female, blue, 9/11/1970"));
    }

    @Test
    public void testSorting() {
        List<Contact> contacts = service.getSortedContacts(SortType.GENDER);
        Assert.assertEquals(4, contacts.size());
        Assert.assertEquals("female", contacts.get(0).getGender());
        Assert.assertEquals("Carter", contacts.get(0).getLastName());
        Assert.assertEquals("Shaw", contacts.get(1).getLastName());
        Assert.assertEquals("Finch", contacts.get(2).getLastName());
        Assert.assertEquals("Reese", contacts.get(3).getLastName());

        contacts = service.getSortedContacts(SortType.DOB);
        Assert.assertEquals(4, contacts.size());
        Assert.assertEquals(LocalDate.of(1954, 9, 7), contacts.get(0).getDateOfBirth());
        Assert.assertEquals(LocalDate.of(1968, 9, 26), contacts.get(1).getDateOfBirth());
        Assert.assertEquals(LocalDate.of(1970, 9, 11), contacts.get(2).getDateOfBirth());
        Assert.assertEquals(LocalDate.of(1980, 1, 10), contacts.get(3).getDateOfBirth());

        contacts = service.getSortedContacts(SortType.LASTNAME);
        Assert.assertEquals(4, contacts.size());
        Assert.assertEquals("Shaw", contacts.get(0).getLastName());
        Assert.assertEquals("Reese", contacts.get(1).getLastName());
        Assert.assertEquals("Finch", contacts.get(2).getLastName());
        Assert.assertEquals("Carter", contacts.get(3).getLastName());
    }

}
