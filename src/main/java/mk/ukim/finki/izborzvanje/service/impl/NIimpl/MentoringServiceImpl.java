package mk.ukim.finki.izborzvanje.service.impl.NIimpl;

import mk.ukim.finki.izborzvanje.model.Criteria;

import mk.ukim.finki.izborzvanje.model.NICriteria.Mentoring;
import mk.ukim.finki.izborzvanje.model.Student;
import mk.ukim.finki.izborzvanje.model.User;
import mk.ukim.finki.izborzvanje.repository.CriteriaRepository;

import mk.ukim.finki.izborzvanje.model.CriteriaDto;
import mk.ukim.finki.izborzvanje.model.NICriteria.Mentoring;
import mk.ukim.finki.izborzvanje.model.Student;

import mk.ukim.finki.izborzvanje.repository.NI.MentoringRepository;
import mk.ukim.finki.izborzvanje.service.NI.MentoringService;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MentoringServiceImpl implements MentoringService {
    private  final MentoringRepository mentoringRepository;
    private final CriteriaRepository criteriaRepository;

    public MentoringServiceImpl(MentoringRepository mentoringRepository, CriteriaRepository criteriaRepository) {
        this.mentoringRepository = mentoringRepository;
        this.criteriaRepository = criteriaRepository;
    }


    @Override
    public List<Mentoring> findAll() {
        return mentoringRepository.findAll();
    }

    @Override
    public Mentoring create(Student student, Criteria NIcriteria, String title, User user) {
        return this.mentoringRepository.save(new Mentoring(student,NIcriteria,title,user));
    }


    @Override
    public Mentoring findById(Long id) {
        return mentoringRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override

    public List<Mentoring> findByCriteriaIdAndUser(Long criteriaId, User user) {
        return this.mentoringRepository.findByNIcriteriaAndUser(criteriaRepository.findById(criteriaId).orElseThrow(RuntimeException::new),user);
    }

    @Override
    public Optional<Mentoring> edit(Long id, String title,Student student) {
        Mentoring mentoring = mentoringRepository.findById(id).orElseThrow(RuntimeException::new);
        mentoring.setTitle(title);
        mentoring.setStudent(student);
        this.mentoringRepository.save(mentoring);
        return Optional.of(mentoring);

    }

    @Override
    public Mentoring delete(Long id) {
       Mentoring mentoring = mentoringRepository.findById(id).orElseThrow(RuntimeException::new);
        this.mentoringRepository.delete(mentoring);
        return mentoring;
    }
    @Override
    public Criteria findCriteriaID(Long id) {
        return this.criteriaRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Mentoring> findByCriteriaId(Long criteriaId) {
        return this.mentoringRepository.findByNIcriteria(criteriaRepository.findById(criteriaId).orElseThrow(RuntimeException::new));
    }
    public List<CriteriaDto> calculateCriteriaPointsByUser(String userId) {
        List<CriteriaDto> dtos = new ArrayList<>();
        int i = 1;
        for (Object[] o : this.mentoringRepository.calculateCriteriaPointsByUser(userId)) {
            CriteriaDto spCriteriaDto = new CriteriaDto((String) o[0], (Double) o[1], false);
            i++;
            dtos.add(spCriteriaDto);
        }
        return dtos;
    }

    @Override
    public CriteriaDto calculatePointsByUserAndCriteriaId(String userId, Long criteriaId) {
        if(this.mentoringRepository.calculatePointsByUserAndCriteriaId(userId,criteriaId).isEmpty())
            return null;
        Object[] result = this.mentoringRepository.calculatePointsByUserAndCriteriaId(userId,criteriaId).get(0);
        String name = result[0] instanceof String ? (String) result[0] : null;
        Double points = null;
        if (result[1] instanceof Number) {
            points = ((Number) result[1]).doubleValue();
        }
        return new CriteriaDto(name, points, false);
    }


}
