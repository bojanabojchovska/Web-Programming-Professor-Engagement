package mk.ukim.finki.izborzvanje.service.impl;

import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.Enumerations.Field;
import mk.ukim.finki.izborzvanje.model.exceptions.InvalidCriteriaIdException;
import mk.ukim.finki.izborzvanje.repository.CriteriaRepository;
import mk.ukim.finki.izborzvanje.service.CriteriaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriteriaServiceImpl implements CriteriaService {

    private final CriteriaRepository criteriaRepository;

    public CriteriaServiceImpl(CriteriaRepository criteriaRepository) {
        this.criteriaRepository = criteriaRepository;
    }

    @Override
    public List<Criteria> listAllCriteria() {
        return criteriaRepository.findAll();
    }

    @Override
    public List<Criteria> listAllSPCriteria() {
        return criteriaRepository.findCriteriaByField(Field.SP);
    }

    @Override
    public List<Criteria> listAllNOCriteria() {
        return criteriaRepository.findCriteriaByField(Field.NO);
    }

    @Override
    public List<Criteria> listAllNICriteria() {
        return criteriaRepository.findCriteriaByField(Field.NI);
    }

    @Override
    public Criteria findById(Long id) {
        return criteriaRepository.findById(id).orElseThrow(InvalidCriteriaIdException::new);
    }
}
