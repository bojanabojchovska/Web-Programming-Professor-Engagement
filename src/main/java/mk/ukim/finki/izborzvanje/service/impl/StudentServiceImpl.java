package mk.ukim.finki.izborzvanje.service.impl;

import mk.ukim.finki.izborzvanje.model.Student;
import mk.ukim.finki.izborzvanje.repository.StudentRepository;
import mk.ukim.finki.izborzvanje.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listAllStudents() {
        return null;
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student findByNameAndLastName(String name, String lastName) {
        return studentRepository.findByNameAndLastName(name, lastName);
    }
}
