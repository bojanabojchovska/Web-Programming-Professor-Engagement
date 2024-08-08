package mk.ukim.finki.izborzvanje.service.NI;

import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.CriteriaDto;
import mk.ukim.finki.izborzvanje.model.NICriteria.Mentoring;
import mk.ukim.finki.izborzvanje.model.NICriteria.Projects;
import mk.ukim.finki.izborzvanje.model.User;

import java.util.List;
import java.util.Optional;

public interface ProjectsService {
    List<Projects> listAllProjects();
    List<Projects> findByCriteriaIdAndUser(Long criteriaId, User user);
//    Projects save(Projects project);
    Projects create(String title, String fundedBy, Criteria NIcriteria,
                           int yearStart, int yearEnd,User user);
    Projects findById(Long id);
    Projects delete(Long id);
    Optional<Projects> edit(Long id,String title, String fundedBy, int yearStart, int yearEnd);
    Criteria findCriteriaID(Long id);
    List<Projects> findByCriteriaId(Long criteriaId);
    List<CriteriaDto> calculateProjectPoints(String userId);

    List<CriteriaDto> calculateCriteriaPointsByUser(String userId);

    CriteriaDto calculatePointsByUserAndCriteriaId(String userId, Long criteriaId);
}
