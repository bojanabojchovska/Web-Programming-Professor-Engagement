package mk.ukim.finki.izborzvanje.service;

import mk.ukim.finki.izborzvanje.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> listAllStudents();

    void save(Student student);
    Student findByNameAndLastName(String name, String lastName);
}
