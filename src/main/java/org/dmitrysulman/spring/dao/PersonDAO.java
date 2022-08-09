package org.dmitrysulman.spring.dao;

import org.dmitrysulman.spring.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Tom", 20, "tom@email.com"));
        people.add(new Person(++PEOPLE_COUNT, "Bob", 21, "bob@email.com"));
        people.add(new Person(++PEOPLE_COUNT, "Mike", 22, "mike@email.com"));
        people.add(new Person(++PEOPLE_COUNT, "Katy", 23, "katy@email.com"));
    }

    public void delete(int id) {
        people.removeIf(p -> p.getId() == id);
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(p -> p.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person updatePerson) {
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(updatePerson.getName());
        personToBeUpdated.setAge(updatePerson.getAge());
        personToBeUpdated.setEmail(updatePerson.getEmail());
    }
}
