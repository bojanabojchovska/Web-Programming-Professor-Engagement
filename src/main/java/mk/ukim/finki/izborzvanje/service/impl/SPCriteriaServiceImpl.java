package mk.ukim.finki.izborzvanje.service.impl;

import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.SPCriteria;
import mk.ukim.finki.izborzvanje.model.CriteriaDto;
import mk.ukim.finki.izborzvanje.model.exceptions.SPCriteriaNotFoundException;
import mk.ukim.finki.izborzvanje.repository.SPCriteriaRepository;
import mk.ukim.finki.izborzvanje.service.SPCriteriaService;
import mk.ukim.finki.izborzvanje.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;


@Service
public class SPCriteriaServiceImpl implements SPCriteriaService {

    private final SPCriteriaRepository spCriteriaRepository;

    public SPCriteriaServiceImpl(SPCriteriaRepository spCriteriaRepository) {
        this.spCriteriaRepository = spCriteriaRepository;
    }

    @Override
    public List<SPCriteria> listAllSPCriteria() {
        return this.spCriteriaRepository.findAll();
    }

    @Override
    public SPCriteria findById(Long id) {
        return this.spCriteriaRepository.findById(id).orElseThrow(SPCriteriaNotFoundException::new);
    }

    @Override
    public SPCriteria create(Criteria criteria, User user, String name,String year) {
        return this.spCriteriaRepository.save(new SPCriteria(criteria,user,name,year));
    }

    @Override
    public List<SPCriteria> findByCriteriaIdAndUser(Long id, User user) {
        return this.spCriteriaRepository.findByCriteriaIdAndUser(id,user);
    }

    @Override

    public List<CriteriaDto> calculateCriteriaPointsByUser(String userId) {
        List<CriteriaDto> dtos = new ArrayList<>();
        int i = 1;
        for (Object[] o : this.spCriteriaRepository.calculateCriteriaPointsByUser(userId)) {
            CriteriaDto spCriteriaDto = new CriteriaDto((String) o[0], (Double) o[1], (boolean) o[2]);
            i++;
            dtos.add(spCriteriaDto);
        }
        return dtos;
    }

    public Optional<SPCriteria> edit(Long id, String name, String year) {
        SPCriteria c = this.findById(id);
        c.setName(name);
        c.setYear(year);
        this.spCriteriaRepository.save(c);
        return Optional.of(c);

    }

    @Override
    public SPCriteria delete(Long id) {
        SPCriteria c = this.findById(id);
        this.spCriteriaRepository.delete(c);
        return c;
    }

    @Override
    public CriteriaDto calculatePointsByUserAndCriteriaId(String userId, Long criteriaId) {
        if(this.spCriteriaRepository.calculatePointsByUserAndCriteriaId(userId,criteriaId).isEmpty())
            return null;
        Object[] result = this.spCriteriaRepository.calculatePointsByUserAndCriteriaId(userId,criteriaId).get(0);
        String name = result[0] instanceof String ? (String) result[0] : null;
        Double points = null;
        if (result[1] instanceof Number) {
            points = ((Number) result[1]).doubleValue();
        }
        return new CriteriaDto(name, points, false);
    }
}
