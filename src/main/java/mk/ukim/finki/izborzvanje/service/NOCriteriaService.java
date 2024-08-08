package mk.ukim.finki.izborzvanje.service;

import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.CriteriaDto;
import mk.ukim.finki.izborzvanje.model.NOCriteria;

import mk.ukim.finki.izborzvanje.model.Enumerations.TypeOfClass;

import mk.ukim.finki.izborzvanje.model.User;

import java.util.List;


public interface NOCriteriaService {
    List<NOCriteria> listAllCriteria();
    NOCriteria findById(Long id);

    NOCriteria create(Criteria criteria, User user, String schoolYear, String subject, String semester, TypeOfClass typeOfClass, int numberOfClasses, int students);
    NOCriteria createMentorshipNOCriteria(Criteria criteria, User user, int studentsForMentorship);
    NOCriteria createWorkNOCriteria(Criteria criteria, User user, String titleOfWork);
    NOCriteria createTeachingNOCriteria(Criteria criteria, User user);

    NOCriteria edit(Long criteriaId, String schoolYear, String subject, TypeOfClass typeOfClass, int numberOfClasses, int students);
    NOCriteria edit(Long criteriaId, int studentsForMentorship);
    NOCriteria edit(Long criteriaId, String nameOfWorkTitle);
    NOCriteria delete(Long criteriaId);

    List<NOCriteria> findByCriteriaIdAndUser (Long id, User user);
    List<NOCriteria> findByUser (User user);

    // Queries
    List<CriteriaDto> calculateCriteriaPointsByUser(String userId);
    CriteriaDto calculatePointsByUserAndCriteriaId(String userId, Long criteriaId);
}
