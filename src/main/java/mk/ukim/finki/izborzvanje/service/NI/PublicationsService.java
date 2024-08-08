package mk.ukim.finki.izborzvanje.service.NI;
import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.CriteriaDto;
import mk.ukim.finki.izborzvanje.model.Enumerations.TypeOfThesis;
import mk.ukim.finki.izborzvanje.model.Enumerations.TypeOfWork;
import mk.ukim.finki.izborzvanje.model.NICriteria.Mentoring;
import mk.ukim.finki.izborzvanje.model.NICriteria.Publications;
import mk.ukim.finki.izborzvanje.model.SPCriteria;
import mk.ukim.finki.izborzvanje.model.User;

import java.util.List;
import java.util.Optional;

public interface PublicationsService {
    List<Publications> listAllPublications();

    public List<Publications> findByCriteriaIdAndUser(Long criteriaId, User user);
    Publications create(String authors, String title, int year, String locationName, Criteria NIcriteria, int noOfAuthors,User user);
    Optional<Publications> edit(Long id,String authors, String title, int year, String locationName, int noOfAuthors);
    Publications findById(Long id);
    Publications delete(Long id);

    Criteria findCriteriaID(Long id);
    List<Publications> findByCriteriaId(Long criteriaId);

    List<CriteriaDto> calculatePublishingPoints(String userId);

    List<CriteriaDto> calculateCriteriaPointsByUser(String userId);

    CriteriaDto calculatePointsByUserAndCriteriaId(String userId, Long criteriaId);
}