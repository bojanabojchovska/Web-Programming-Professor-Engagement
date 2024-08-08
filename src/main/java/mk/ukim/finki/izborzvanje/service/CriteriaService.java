package mk.ukim.finki.izborzvanje.service;

import mk.ukim.finki.izborzvanje.model.Criteria;

import java.util.List;

public interface CriteriaService {

    List<Criteria> listAllCriteria();
    List<Criteria> listAllSPCriteria();
    List<Criteria> listAllNOCriteria();
    List<Criteria> listAllNICriteria();
    Criteria findById(Long id);
}
