package mk.ukim.finki.izborzvanje.service;

import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.SPCriteria;
import mk.ukim.finki.izborzvanje.model.CriteriaDto;
import mk.ukim.finki.izborzvanje.model.User;

import java.util.List;

import java.util.Optional;


public interface SPCriteriaService {

    List<SPCriteria> listAllSPCriteria();
    SPCriteria findById(Long id);

    SPCriteria create(Criteria criteria, User user, String name, String year);
    List<SPCriteria> findByCriteriaIdAndUser (Long id, User user);

    List<CriteriaDto> calculateCriteriaPointsByUser(String userId);

    Optional<SPCriteria> edit(Long id, String name, String year);

    SPCriteria delete(Long id);

    CriteriaDto calculatePointsByUserAndCriteriaId(String userId, Long criteriaId);

}
