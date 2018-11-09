package com.ckirkendall3.contacts.cli;

import com.ckirkendall3.contacts.dto.Contact;
import com.ckirkendall3.contacts.service.ContactsService;
import com.ckirkendall3.contacts.service.ParseService;
import com.ckirkendall3.contacts.service.SortType;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Contact CLI application
 */
@Component
public class ContactsCliApp {

    private ContactsService service;
    private ParseService parseService;

    @Autowired
    public ContactsCliApp(ContactsService service, ParseService parseService) {
        this.service = service;
        this.parseService = parseService;
    }

    /**
     * Application entry point.
     *
     * @param args application command line arguments
     */
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(
                "com.ckirkendall3.contacts.dataservice",
                "com.ckirkendall3.contacts.service",
                "com.ckirkendall3.contacts.cli"); // Use annotated beans from the specified package

        ContactsCliApp app = ctx.getBean(ContactsCliApp.class);
        int retval = app.run(args);
        System.exit(retval);
    }

    /**
     * Main run routine of application class
     *
     * @param args command line arguments
     */
    public int run(String[] args) {
        try {
            CommandLine commandLine = parseArguments(args);

            if (commandLine.hasOption("filename")) {
                List<Contact> fileContacts = parseService.parseFile(commandLine.getOptionValue("filename"));

                // Insert the contacts
                fileContacts.forEach(contact -> service.insertContact(contact));

                // Print out the contacts in all the sort orders
                Arrays.stream(SortType.values()).forEach(sort -> printContacts(service.getSortedContacts(sort), sort));
            } else {
                throw new MissingArgumentException("Filename argument missing.");
            }
        } catch (Exception e) {
            System.err.println("Error encountered: " + e.getMessage());
            printHelp();
            return 1;
        }

        return 0;
    }

    private void printContacts(List<Contact> sortedContacts, SortType sortBy) {
        System.out.println("\nRecords sorted by " + sortBy.name().toLowerCase());
        System.out.printf("%-19s %-19s %-19s %-19s %-19s\n",
                "Last Name", "First Name", "Gender", "Favorite Color", "Date of Birth");
        sortedContacts.forEach(c ->
                System.out.printf("%-19s %-19s %-19s %-19s %-19s\n", c.getLastName(), c.getFirstName(), c.getGender(),
                        c.getFavoriteColor(), c.getDateOfBirth().format(ParseService.DATE_FORMATTER)));
    }

    /**
     * Parse the command line arguments
     *
     * @throws ParseException argument parsing failed
     */
    private CommandLine parseArguments(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        return parser.parse(getOptions(), args);
    }

    /**
     * Gets the command line options
     */
    private Options getOptions() {
        Options options = new Options();
        options.addOption("f", "filename", true, "file name to load data from");
        return options;
    }

    /**
     * Prints help information
     */
    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Contacts", getOptions(), true);
    }
}
