package mk.ukim.finki.izborzvanje.repository;

import mk.ukim.finki.izborzvanje.model.SPCriteria;
import mk.ukim.finki.izborzvanje.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByNameAndLastName(String name, String lastName);
}
