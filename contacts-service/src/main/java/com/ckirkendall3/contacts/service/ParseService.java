package com.ckirkendall3.contacts.service;

import com.ckirkendall3.contacts.dto.Contact;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoField.*;

/**
 * Parsing service for contact information
 */
@Service
public class ParseService {

    public static final DateTimeFormatter DATE_FORMATTER;

    static {
        DATE_FORMATTER = new DateTimeFormatterBuilder()
                .parseLenient()
                .appendValue(MONTH_OF_YEAR, 1)
                .appendLiteral('/')
                .appendValue(DAY_OF_MONTH, 2)
                .appendLiteral('/')
                .appendValue(YEAR, 4)
                .toFormatter();
    }

    public Contact parseContact(String line) throws IllegalArgumentException {
        Contact contact = new Contact();

        String[] columns;
        if (line.contains(",")) {
            columns = line.split(",");
        } else if (line.contains("|")) {
            columns = line.split("\\|");
        } else {
            // Must be space delimited
            columns = line.trim().split(" ");
        }

        if (columns.length != 5) {
            throw new IllegalArgumentException("Invalid number of elements: " + line);
        }

        contact.setLastName(columns[0].trim());
        contact.setFirstName(columns[1].trim());
        contact.setGender(columns[2].trim().toLowerCase());
        contact.setFavoriteColor(columns[3].trim().toLowerCase());
        contact.setDateOfBirth(LocalDate.parse(columns[4].trim(), DATE_FORMATTER));
        return contact;
    }

    public List<Contact> parseFile(String fileName) throws IOException {
        Scanner s = new Scanner(new File(fileName));
        s.useDelimiter(System.getProperty("line.separator"));
        ArrayList<String> lines = new ArrayList<>();
        while (s.hasNext()) {
            lines.add(s.next());
        }
        s.close();

        return lines.stream().map(this::parseContact).collect(Collectors.toList());
    }
}
