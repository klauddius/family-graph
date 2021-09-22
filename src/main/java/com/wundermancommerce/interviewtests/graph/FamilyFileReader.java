package com.wundermancommerce.interviewtests.graph;

import com.wundermancommerce.interviewtests.graph.model.Person;
import com.wundermancommerce.interviewtests.graph.model.Relationship;
import com.wundermancommerce.interviewtests.graph.model.RelationshipType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FamilyFileReader {

    private String FIELD_SEPARATOR = ",";

    private int PERSON_NAME_FIELD = 0;

    private int PERSON_EMAIL_FIELD = 1;

    private int PERSON_AGE_FIELD = 2;

    private int RELATIONSHIP_PEOPLE1_EMAIL_FIELD = 0;

    private int RELATIONSHIP_TYPE_FIELD = 1;

    private int RELATIONSHIP_PEOPLE2_EMAIL_FIELD = 2;


    public List<Person> readPeopleFromFile(File peopleCsvFile) throws IOException {

        FileReader fileReader = new FileReader(peopleCsvFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        List<Person> people = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            String[] personData = line.split(FIELD_SEPARATOR);
            Person person = new Person(personData[PERSON_NAME_FIELD], personData[PERSON_EMAIL_FIELD], Integer.valueOf(personData[PERSON_AGE_FIELD]));
            people.add(person);
        }

        return people;
    }

    public List<Relationship> readRelationshipsFromFile(File relationshipCsvFile) throws IOException {

        FileReader fileReader = new FileReader(relationshipCsvFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        List<Relationship> relationships = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            if ("".equals(line)) {
                continue;
            }
            String[] relationshipData = line.split(FIELD_SEPARATOR);
            Person person1 = new Person(relationshipData[RELATIONSHIP_PEOPLE1_EMAIL_FIELD]);
            Person person2 = new Person(relationshipData[RELATIONSHIP_PEOPLE2_EMAIL_FIELD]);
            RelationshipType type = RelationshipType.valueOf(relationshipData[RELATIONSHIP_TYPE_FIELD]);
            Relationship relationship = new Relationship(person1, type, person2);
            relationships.add(relationship);
        }

        return relationships;
    }

}
