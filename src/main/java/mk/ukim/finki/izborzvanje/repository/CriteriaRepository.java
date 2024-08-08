package mk.ukim.finki.izborzvanje.repository;

import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.Enumerations.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriteriaRepository extends JpaRepository<Criteria,Long> {
    List<Criteria> findCriteriaByField(Field field);
}
