package com.ckirkendall3.contacts.cli;


import com.ckirkendall3.contacts.dataservice.ContactsDataservice;
import com.ckirkendall3.contacts.service.ContactsService;
import com.ckirkendall3.contacts.service.ParseService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;


/**
 * Contacts service bean
 */
public class TestContactsApp {

    private final ContactsDataservice dataService = new ContactsDataservice();
    private final ContactsService service = new ContactsService(dataService);
    private final ParseService parseService = new ParseService();
    private final ContactsApp app = new ContactsApp(service, parseService);

    private final String contactsString =
            "Reese, John, male, black, 9/26/1968\n" +
            "Finch, Harold, male, gray, 9/7/1954\n" +
            "Shaw, Sameen, female, red, 1/10/1980\n" +
            "Carter, Joss, female, blue, 9/11/1970";
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testContactsApplication() throws IOException {
        // Create a temporary file so we can test the application
        final File tempFile = tempFolder.newFile("contacts.txt");
        FileCopyUtils.copy(contactsString.getBytes(), tempFile);
        int retval = app.run(new String[]{"-f", tempFile.getAbsolutePath()});
        Assert.assertEquals(0, retval);
    }

    @Test
    public void testInvalidFile() throws IOException {
        int retval = app.run(new String[]{"-f", "nonexisting.txt"});
        Assert.assertEquals(1, retval);
    }
}
