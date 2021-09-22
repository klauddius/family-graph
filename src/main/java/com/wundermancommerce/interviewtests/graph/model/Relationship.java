package com.wundermancommerce.interviewtests.graph.model;

public class Relationship {

    private Person person1;

    private RelationshipType type;

    private Person person2;

    public Relationship(Person person1, RelationshipType type, Person person2) {
        this.person1 = person1;
        this.type = type;
        this.person2 = person2;
    }

    public Person getPerson1() {
        return person1;
    }

    public RelationshipType getType() {
        return type;
    }

    public Person getPerson2() {
        return person2;
    }
}
