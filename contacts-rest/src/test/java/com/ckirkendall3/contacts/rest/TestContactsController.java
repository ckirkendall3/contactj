package com.ckirkendall3.contacts.rest;

import com.ckirkendall3.contacts.dataservice.ContactsDataservice;
import com.ckirkendall3.contacts.dto.Contact;
import com.ckirkendall3.contacts.service.ContactsService;
import com.ckirkendall3.contacts.service.ParseService;
import com.ckirkendall3.contacts.service.SortType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

/**
 * Contacts service bean
 */
public class TestContactsController {

    private final ContactsDataservice dataservice = new ContactsDataservice();
    private final ContactsService service = new ContactsService(dataservice);
    private final ParseService parseService = new ParseService();
    private final ContactController controller = new ContactController(service, parseService);
    @Before
    public void initialize() {
        controller.insertContact("Reese, John, male, black, 9/26/1968");
        controller.insertContact("Finch, Harold, male, gray, 9/7/1954");
        controller.insertContact("Shaw, Sameen, female, red, 1/10/1980");
        controller.insertContact("Carter, Joss, female, blue, 9/11/1970");
    }

    @Test
    public void testSorting() {
        List<Contact> contacts = controller.getOrderedByGender();
        Assert.assertEquals(4, contacts.size());
        Assert.assertEquals("female", contacts.get(0).getGender());
        Assert.assertEquals("Carter", contacts.get(0).getLastName());
        Assert.assertEquals("Shaw", contacts.get(1).getLastName());
        Assert.assertEquals("Finch", contacts.get(2).getLastName());
        Assert.assertEquals("Reese", contacts.get(3).getLastName());

        contacts = controller.getOrderedByBirthdate();
        Assert.assertEquals(4, contacts.size());
        Assert.assertEquals(LocalDate.of(1954, 9, 7), contacts.get(0).getDateOfBirth());
        Assert.assertEquals(LocalDate.of(1968, 9, 26), contacts.get(1).getDateOfBirth());
        Assert.assertEquals(LocalDate.of(1970, 9, 11), contacts.get(2).getDateOfBirth());
        Assert.assertEquals(LocalDate.of(1980, 1, 10), contacts.get(3).getDateOfBirth());

        contacts = controller.getOrderedByName();
        Assert.assertEquals(4, contacts.size());
        Assert.assertEquals("Shaw", contacts.get(0).getLastName());
        Assert.assertEquals("Reese", contacts.get(1).getLastName());
        Assert.assertEquals("Finch", contacts.get(2).getLastName());
        Assert.assertEquals("Carter", contacts.get(3).getLastName());
    }

}
