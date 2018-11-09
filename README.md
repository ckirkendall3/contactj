# contactj
Simple application for storing and retrieving contact information

## Command Line Program

The contacts-cli module builds a jar that can be used to ingest a file of records with each line representing a single record.  Each record must be formatted in one of the following formats:

* LastName | FirstName | Gender | FavoriteColor | DateOfBirth
* LastName, FirstName, Gender, FavoriteColor, DateOfBirth
* LastName FirstName Gender FavoriteColor DateOfBirth

The program will then output the list of records sorted by gender, then sorted by birth date, then sorted by last name descending.

## SpringBoot RESTful application

The contacts-app module builds a spring boot jar that implements a simple RESTful server with the following endpoints:

* POST/records - Posts a single record in the above mentioned formats.
* GET/records/gender - Returns records sorted by gender.
* GET/records/birthdate - Returns records sorted by birthdate.
* GET/records/name = Returns records sorted by name.
