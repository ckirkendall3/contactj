package com.ckirkendall3.contacts.service;

import com.ckirkendall3.contacts.dto.Contact;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Contacts service bean
 */
public class TestParseService {

    private final ParseService parseService = new ParseService();

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testFileParsing() throws IOException {
        final String contactsString =
                "Reese, John, male, black, 9/26/1968\n" +
                        "Finch, Harold, male, gray, 9/7/1954\n" +
                        "Shaw, Sameen, female, red, 1/10/1980\n" +
                        "Carter, Joss, female, blue, 9/11/1970";
        final File tempFile = tempFolder.newFile("contacts.txt");
        FileCopyUtils.copy(contactsString.getBytes(), tempFile);
        List<Contact> contacts =
                parseService.parseFile(tempFile.getAbsolutePath());
        Assert.assertEquals(4, contacts.size());
    }

    @Test
    public void testLineFormats() {
        Contact contact = parseService.parseContact("Smith | John | male | orange | 2/5/1990");
        Assert.assertEquals("Smith", contact.getLastName());

        contact = parseService.parseContact("Jones, Bob, male, white, 5/12/1950");
        Assert.assertEquals("Jones", contact.getLastName());

        contact = parseService.parseContact("Brown Sara female purple 12/24/1996");
        Assert.assertEquals("Brown", contact.getLastName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidNumberOfElements() {
        parseService.parseContact("one, two, three");
    }

    @Test(expected = DateTimeParseException.class)
    public void testInvalidDate() {
        parseService.parseContact("Jones, Bob, male, white, test");
    }
}