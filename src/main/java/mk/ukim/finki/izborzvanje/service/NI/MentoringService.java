package mk.ukim.finki.izborzvanje.service.NI;

import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.CriteriaDto;
import mk.ukim.finki.izborzvanje.model.NICriteria.Mentoring;
import mk.ukim.finki.izborzvanje.model.Student;
import mk.ukim.finki.izborzvanje.model.User;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MentoringService {
    List<Mentoring> findAll();
    Mentoring create(Student student, Criteria NIcriteria, String title, User user);
    Mentoring findById(Long id);

    List<Mentoring> findByCriteriaIdAndUser(Long criteriaId, User user);

//    void save(Mentoring mentoring);
     Optional<Mentoring> edit(Long id, String title,Student student);
    Mentoring delete(Long id);
    Criteria findCriteriaID(Long id);
    List<Mentoring> findByCriteriaId(Long criteriaId);


    List<CriteriaDto> calculateCriteriaPointsByUser(String userId);

    CriteriaDto calculatePointsByUserAndCriteriaId(String userId, Long criteriaId);
}

