package com.wundermancommerce.interviewtests.graph;

import com.wundermancommerce.interviewtests.graph.model.Person;
import com.wundermancommerce.interviewtests.graph.model.Relationship;
import com.wundermancommerce.interviewtests.graph.model.RelationshipType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FamilyGraphTest {

    private FamilyGraph graph;

    private List<Person> mockedPeopleList;

    private List<Relationship> mockedRelationshipList;

    @Before
    public void setUp() throws URISyntaxException, IOException {
        File peopleCsvFile = new File(getClass().getClassLoader().getResource("people.csv").toURI());
        File relationshipsCsvFile = new File(getClass().getClassLoader().getResource("relationships.csv").toURI());

        FamilyFileReader familyFileReader = new FamilyFileReader();

        graph = new FamilyGraph();

        List<Person> people = familyFileReader.readPeopleFromFile(peopleCsvFile);

        for (Person person : people) {
            graph.addPerson(person);
        }

        List<Relationship> relationships = familyFileReader.readRelationshipsFromFile(relationshipsCsvFile);

        for (Relationship relationship : relationships) {
            graph.addRelationship(relationship);
        }

        mockedPeopleList = getMockedPeopleList();
        mockedRelationshipList = getMockedRelationshipList();

    }

    private List<Relationship> getMockedRelationshipList() {
        List<Relationship> relationships = new ArrayList<>();

        relationships.add(new Relationship(new Person("bob@bob.com"), RelationshipType.FAMILY, new Person("finn@gmail.com")));
        relationships.add(new Relationship(new Person("bob@bob.com"), RelationshipType.FAMILY, new Person("amber@gmail.com")));
        relationships.add(new Relationship(new Person("bob@bob.com"), RelationshipType.FAMILY, new Person("anna@clothes.com")));
        relationships.add(new Relationship(new Person("anna@clothes.com"), RelationshipType.FAMILY, new Person("finn@gmail.com")));
        relationships.add(new Relationship(new Person("anna@clothes.com"), RelationshipType.FAMILY, new Person("amber@gmail.com")));
        relationships.add(new Relationship(new Person("amber@gmail.com"), RelationshipType.FAMILY, new Person("finn@gmail.com")));
        relationships.add(new Relationship(new Person("anna@clothes.com"), RelationshipType.FRIEND, new Person("jenny@toys.com")));
        relationships.add(new Relationship(new Person("jenny@toys.com"), RelationshipType.FAMILY, new Person("pete@timber.com")));
        relationships.add(new Relationship(new Person("jenny@toys.com"), RelationshipType.FAMILY, new Person("kerry@oilcompany.org")));
        relationships.add(new Relationship(new Person("pete@timber.com"), RelationshipType.FAMILY, new Person("kerry@oilcompany.org")));
        relationships.add(new Relationship(new Person("dave@dentists.com"), RelationshipType.FAMILY, new Person("pete@timber.com")));
        relationships.add(new Relationship(new Person("kerry@oilcompany.org"), RelationshipType.FRIEND, new Person("joe@construction.net")));
        relationships.add(new Relationship(new Person("joe@construction.net"), RelationshipType.FAMILY, new Person("nigel@marketing.com")));
        relationships.add(new Relationship(new Person("nigel@marketing.com"), RelationshipType.FRIEND, new Person("derek@bob.com")));
        relationships.add(new Relationship(new Person("derek@bob.com"), RelationshipType.FRIEND, new Person("bob@bob.com")));
        relationships.add(new Relationship(new Person("derek@bob.com"), RelationshipType.FRIEND, new Person("pete@timber.com")));

        return relationships;
    }

    private List<Person> getMockedPeopleList() {
        List<Person> peopleList = new ArrayList<>();
        peopleList.add(new Person("Bob", "bob@bob.com", 31));
        peopleList.add(new Person("Derek", "derek@bob.com", 25));
        peopleList.add(new Person("Anna", "anna@clothes.com", 25));
        peopleList.add(new Person("Jenny", "jenny@toys.com", 52));
        peopleList.add(new Person("Pete", "pete@timber.com", 57));
        peopleList.add(new Person("Kerry", "kerry@oilcompany.org", 29));
        peopleList.add(new Person("Joe", "joe@construction.net", 28));
        peopleList.add(new Person("Nigel", "nigel@marketing.com", 40));
        peopleList.add(new Person("Amber", "amber@gmail.com", 12));
        peopleList.add(new Person("Finn", "finn@gmail.com", 15));
        peopleList.add(new Person("Alan", "alan@lonely.org", 23));
        peopleList.add(new Person("Dave", "dave@dentists.com", 49));

        return peopleList;
    }

    /**
     * Exercise 2 - Validate correct people loaded
     */
    @Test
    public void validateExpectedNumberOfPeopleLoaded() {
        assertEquals(graph.getPeople().size(), mockedPeopleList.size());

        // Check if both lists contains all the elements ignoring order
        assertTrue(Arrays.asList(graph.getPeople().toArray()).containsAll(mockedPeopleList));
        assertTrue(mockedPeopleList.containsAll(Arrays.asList(graph.getPeople().toArray())));
    }

    /**
     * Exercise 3 - Validate correct relationships loaded
     *
     * Bob (4 relationships)
     * Jenny (3 relationships)
     * Nigel (2 relationships)
     * Alan (0 relationships)
     */
    @Test
    public void validateExpectedNumberOfConnectionsToOtherPeople() {
        assertEquals(graph.getConnectedPeopleNumberFrom(new Person("bob@bob.com")), 4);
        assertEquals(graph.getConnectedPeopleNumberFrom(new Person("jenny@toys.com")), 3);
        assertEquals(graph.getConnectedPeopleNumberFrom(new Person("nigel@marketing.com")), 2);
        assertEquals(graph.getConnectedPeopleNumberFrom(new Person("alan@lonely.org")), 0);

        assertEquals(graph.getConnectedPeopleNumberFrom(new Person("derek@bob.com")), 3);
        assertEquals(graph.getConnectedPeopleNumberFrom(new Person("anna@clothes.com")), 4);
        assertEquals(graph.getConnectedPeopleNumberFrom(new Person("pete@timber.com")), 4);
        assertEquals(graph.getConnectedPeopleNumberFrom(new Person("kerry@oilcompany.org")), 3);
        assertEquals(graph.getConnectedPeopleNumberFrom(new Person("joe@construction.net")), 2);
        assertEquals(graph.getConnectedPeopleNumberFrom(new Person("amber@gmail.com")), 3);
        assertEquals(graph.getConnectedPeopleNumberFrom(new Person("finn@gmail.com")), 3);
        assertEquals(graph.getConnectedPeopleNumberFrom(new Person("dave@dentists.com")), 1);


    }

    /**
     * Exercise 4 - Write a method that calculates the size of the extended family
     *
     * Jenny (4 family members)
     * Bob (4 family members)
     *
     */
    @Test
    public void validateSizeExtendedFamily() {
        assertEquals(graph.getExtendedFamilyNumberFrom(new Person("jenny@toys.com")), 4);
        assertEquals(graph.getExtendedFamilyNumberFrom(new Person("bob@bob.com")), 4);

        assertEquals(graph.getExtendedFamilyNumberFrom(new Person("anna@clothes.com")), 4);
        assertEquals(graph.getExtendedFamilyNumberFrom(new Person("amber@gmail.com")), 4);
        assertEquals(graph.getExtendedFamilyNumberFrom(new Person("pete@timber.com")), 4);
        assertEquals(graph.getExtendedFamilyNumberFrom(new Person("kerry@oilcompany.org")), 4);
        assertEquals(graph.getExtendedFamilyNumberFrom(new Person("nigel@marketing.com")), 2);

        assertEquals(graph.getExtendedFamilyNumberFrom(new Person("joe@construction.net")), 2);
        assertEquals(graph.getExtendedFamilyNumberFrom(new Person("finn@gmail.com")), 4);
        assertEquals(graph.getExtendedFamilyNumberFrom(new Person("alan@lonely.org")), 1);
        assertEquals(graph.getExtendedFamilyNumberFrom(new Person("dave@dentists.com")), 4);

    }

    @Test
    public void validatePersonWithFamilyAndFriends() {
        Person bob = new Person("bob@bob.com");

        // Assert the number of people that are directly or indirectly family of Bob
        assertEquals(graph.getAllRelatedPeopleNumber(bob,RelationshipType.FAMILY), 4);

        // Assert the number of people that are directly or indirectly friends of bob
        assertEquals(graph.getAllRelatedPeopleNumber(bob, RelationshipType.FRIEND), 4);
    }

    @Test
    public void validatePersonWithOnlyFriends() {
        Person derek = new Person("derek@bob.com");

        // Assert the number of people that are directly or indirectly family of Bob
        assertEquals(graph.getAllRelatedPeopleNumber(derek,RelationshipType.FAMILY), 1);

        // Assert the number of people that are directly or indirectly friends of bob
        assertEquals(graph.getAllRelatedPeopleNumber(derek, RelationshipType.FRIEND), 4);
    }

    @Test
    public void validatePersonWithNoRelationsExceptWithYourself() {
        Person alan = new Person("alan@lonely.org");

        // Assert the number of people that are directly or indirectly family of Bob
        assertEquals(graph.getAllRelatedPeopleNumber(alan,RelationshipType.FAMILY), 1);

        // Assert the number of people that are directly or indirectly friends of bob
        assertEquals(graph.getAllRelatedPeopleNumber(alan, RelationshipType.FRIEND), 1);
    }

}
