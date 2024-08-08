package mk.ukim.finki.izborzvanje.repository;

import mk.ukim.finki.izborzvanje.model.Subject;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaSpecificationRepository<Subject, String> {
}
