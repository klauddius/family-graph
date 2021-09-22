package com.wundermancommerce.interviewtests.graph;

import com.wundermancommerce.interviewtests.graph.model.Person;
import com.wundermancommerce.interviewtests.graph.model.Relationship;
import com.wundermancommerce.interviewtests.graph.model.RelationshipType;

import java.util.*;

public class FamilyGraph {

    private final Map<Person, Map<RelationshipType, List<Person>>> familyGraph;

    public FamilyGraph() {
        this.familyGraph = new HashMap<>();
    }

    public void addPerson(Person personToAdd) {
        HashMap<RelationshipType, List<Person>> relationships = new HashMap<>();
        // Create an empty list that contains families relationships
        relationships.putIfAbsent(RelationshipType.FAMILY, new ArrayList<>());
        // Create an empty list that contains friends relationships
        relationships.putIfAbsent(RelationshipType.FRIEND, new ArrayList<>());
        this.familyGraph.putIfAbsent(personToAdd, relationships);
    }

    public void addRelationship(Relationship relationship) {
        familyGraph.get(relationship.getPerson1()).get(relationship.getType()).add(relationship.getPerson2());
        familyGraph.get(relationship.getPerson2()).get(relationship.getType()).add(relationship.getPerson1());
    }

    public Set<Person> getPeople() {
        return familyGraph.keySet();
    }

    /**
     * Get people that are directly connected to the desired person and with the desired relationship
     */
    public List<Person> getConnectedPeopleFrom(Person person, RelationshipType relationshipType) {
        if (relationshipType == null) {
            return getConnectedPeopleFrom(person);
        }
        return familyGraph.get(person).get(relationshipType);
    }

    /**
     * Get all people that are directly connected to the desired person no matter relationship type
     */
    public List<Person> getConnectedPeopleFrom(Person person) {
        List<Person> people = new ArrayList<>();
        people.addAll(getConnectedPeopleFrom(person, RelationshipType.FAMILY));
        people.addAll(getConnectedPeopleFrom(person, RelationshipType.FRIEND));
        return people;
    }

    /**
     * Get the number of people that are directly connected to the desired person no matter relationship type
     */
    public int getConnectedPeopleNumberFrom(Person person) {
        return getConnectedPeopleFrom(person).size();
    }

    /**
     * Get all people that are directly and indirectly related to the desired person and with the desired relationship
     */
    public Set<Person> getAllRelatedPeopleFrom(Person rootPerson, RelationshipType relationship) {
        Set<Person> visited = new HashSet<>();
        Stack<Person> stack = new Stack<>();
        stack.push(rootPerson);
        while (!stack.isEmpty()) {
            Person node = stack.pop();
            if (!visited.contains(node)) {
                visited.add(node);
                for (Person p : this.getConnectedPeopleFrom(node, relationship)) {
                    stack.push(p);
                }
            }
        }
        return visited;
    }

    /**
     * Get the number of all people that are directly and indirectly related to the desired person and with the desired
     * relationship
     */
    public int getAllRelatedPeopleNumber(Person rootPerson, RelationshipType relationship) {
        return getAllRelatedPeopleFrom(rootPerson, relationship).size();
    }

    /**
     * Get all extended family that are directly and indirectly related to the desired person
     */
    public int getExtendedFamilyNumberFrom(Person person) {
        return getAllRelatedPeopleNumber(person, RelationshipType.FAMILY);
    }
}

