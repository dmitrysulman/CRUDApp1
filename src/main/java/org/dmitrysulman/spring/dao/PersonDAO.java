package org.dmitrysulman.spring.dao;

import org.dmitrysulman.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
//    private static final String USERNAME = "postgres";
//    private static final String PASSWORD = "admin";
//
//    private static Connection connection;
//
//    static {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void delete(int id) {
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("DELETE FROM person WHERE id = ?");
//            preparedStatement.setInt(1, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    public List<Person> index() {
//        List<Person> people = new ArrayList<>();
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM person");
//            while (resultSet.next()) {
//                Person person = new Person();
//                person.setId(resultSet.getInt("id"));
//                person.setName(resultSet.getString("name"));
//                person.setEmail(resultSet.getString("email"));
//                person.setAge(resultSet.getInt("age"));
//                people.add(person);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return people;
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
//        Person person;
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("SELECT * FROM person where id = ?");
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//            person = new Person();
//            person.setId(resultSet.getInt("id"));
//            person.setName(resultSet.getString("name"));
//            person.setAge(resultSet.getInt("age"));
//            person.setEmail(resultSet.getString("email"));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return person;
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new BeanPropertyRowMapper<>(Person.class), id)
                .stream()
                .findAny()
                .orElse(null);
    }

    public Person show(String email) {
        return jdbcTemplate.query("SELECT * FROM person WHERE email = ?", new BeanPropertyRowMapper<>(Person.class), email)
                .stream()
                .findAny()
                .orElse(null);
    }

    public void save(Person person) {
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("INSERT INTO person VALUES (1, ?, ?, ?)");
//            preparedStatement.setString(1, person.getName());
//            preparedStatement.setInt(2, person.getAge());
//            preparedStatement.setString(3, person.getEmail());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        jdbcTemplate.update("INSERT INTO person (name, age, email, address) VALUES (?, ?, ?, ?)",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    }

    public void update(int id, Person updatePerson) {
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("UPDATE person SET name = ?, age = ?, email = ? WHERE id = ?");
//            preparedStatement.setString(1, updatePerson.getName());
//            preparedStatement.setInt(2, updatePerson.getAge());
//            preparedStatement.setString(3, updatePerson.getEmail());
//            preparedStatement.setInt(4, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        jdbcTemplate.update("UPDATE person SET name = ?, age = ?, email = ?, address = ? WHERE id = ?",
                updatePerson.getName(), updatePerson.getAge(), updatePerson.getEmail(), updatePerson.getAddress(), id);
    }

    public void testMultipleUpdate() {
        List<Person> people = create1000people();
        long before = System.currentTimeMillis();
        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO person VALUES (?, ?, ?, ?)", person.getId(), person.getName(), person.getAge(), person.getEmail());
        }
        long after = System.currentTimeMillis();

        System.out.println("Time: " + (after - before));
    }

    public void testBatchUpdate() {
        List<Person> people = create1000people();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO person VALUES (?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setInt(1, people.get(i).getId());
                        preparedStatement.setString(2, people.get(i).getName());
                        preparedStatement.setInt(3, people.get(i).getAge());
                        preparedStatement.setString(4, people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });
        long after = System.currentTimeMillis();

        System.out.println("Time: " + (after - before));
    }

    private List<Person> create1000people() {
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name" + i, 30, "test" + i + "@mail.ru", "address"));
        }

        return people;
    }
}
