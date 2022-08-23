package org.dmitrysulman.spring.dao;

import org.dmitrysulman.spring.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    private List<Person> people;
//
//    {
//        people = new ArrayList<>();
//
//        people.add(new Person(++PEOPLE_COUNT, "Tom", 20, "tom@email.com"));
//        people.add(new Person(++PEOPLE_COUNT, "Bob", 21, "bob@email.com"));
//        people.add(new Person(++PEOPLE_COUNT, "Mike", 22, "mike@email.com"));
//        people.add(new Person(++PEOPLE_COUNT, "Katy", 23, "katy@email.com"));
//    }

    public void delete(int id) {
//        people.removeIf(p -> p.getId() == id);
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM person");

            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));

                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    public Person show(int id) {
//        return people.stream().filter(p -> p.getId() == id).findAny().orElse(null);
        return null;
    }

    public void save(Person person) {
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO person VALUES ("  + 1 + ", '" + person.getName() + "', " +
                    person.getAge() + ", '" + person.getEmail() + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, Person updatePerson) {
//        Person personToBeUpdated = show(id);
//        personToBeUpdated.setName(updatePerson.getName());
//        personToBeUpdated.setAge(updatePerson.getAge());
//        personToBeUpdated.setEmail(updatePerson.getEmail());
    }
}
