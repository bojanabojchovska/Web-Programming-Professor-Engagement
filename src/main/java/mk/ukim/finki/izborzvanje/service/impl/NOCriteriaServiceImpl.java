package mk.ukim.finki.izborzvanje.service.impl;

import mk.ukim.finki.izborzvanje.model.*;

import mk.ukim.finki.izborzvanje.model.Enumerations.Semester;
import mk.ukim.finki.izborzvanje.model.Enumerations.TypeOfClass;

import mk.ukim.finki.izborzvanje.model.exceptions.InvalidDetailsOfStudiesIdException;
import mk.ukim.finki.izborzvanje.repository.NOCriteriaRepository;
import mk.ukim.finki.izborzvanje.service.NOCriteriaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NOCriteriaServiceImpl implements NOCriteriaService {
    private final NOCriteriaRepository noCriteriaRepository;

    public NOCriteriaServiceImpl(NOCriteriaRepository noCriteriaRepository) {
        this.noCriteriaRepository = noCriteriaRepository;
    }

    @Override
    public List<NOCriteria> listAllCriteria() {
        return noCriteriaRepository.findAll();
    }

    @Override
    public NOCriteria findById(Long id) {
        return noCriteriaRepository.findById(id).orElseThrow(InvalidDetailsOfStudiesIdException::new);
    }

    @Override
    public NOCriteria create(Criteria criteria,
                             User user,
                             String schoolYear,
                             String subject,
                             String semester,
                             TypeOfClass typeOfClass,
                             int numberOfClasses,
                             int students) {
        return noCriteriaRepository.save(
                new NOCriteria(criteria,
                user,
                schoolYear,
                subject,
                semester,
                typeOfClass,
                numberOfClasses,
                students));
    }

    @Override
    public NOCriteria createMentorshipNOCriteria(Criteria criteria, User user, int studentsForMentorship) {
        return noCriteriaRepository.save(new NOCriteria(criteria,user,studentsForMentorship));
    }

    @Override
    public NOCriteria createWorkNOCriteria(Criteria criteria, User user, String titleOfWork) {
        return noCriteriaRepository.save(new NOCriteria(criteria,user,titleOfWork));
    }

    @Override
    public NOCriteria createTeachingNOCriteria(Criteria criteria, User user) {
        return noCriteriaRepository.save(new NOCriteria(criteria,user));
    }

    @Override
    public NOCriteria edit(Long criteriaId, String schoolYear, String subject, TypeOfClass typeOfClass, int numberOfClasses, int students) {
        NOCriteria criteria = this.findById(criteriaId);
        criteria.setSchoolYear(schoolYear);
        criteria.setSubject(subject);
        criteria.setTypeOfClass(typeOfClass);
        criteria.setNumberOfClasses(numberOfClasses);
        criteria.setStudents(students);

        return noCriteriaRepository.save(criteria);
    }

    @Override
    public NOCriteria edit(Long criteriaId, int studentsForMentorship) {
        NOCriteria criteria = this.findById(criteriaId);
        criteria.setStudentsForMentorship(studentsForMentorship);
        return noCriteriaRepository.save(criteria);
    }

    @Override
    public NOCriteria edit(Long criteriaId, String nameOfWorkTitle) {
        NOCriteria criteria = this.findById(criteriaId);
        criteria.setNameOfWorkTitle(nameOfWorkTitle);
        return noCriteriaRepository.save(criteria);
    }

    @Override
    public NOCriteria delete(Long criteriaId) {
        NOCriteria criteria = this.findById(criteriaId);
        noCriteriaRepository.delete(criteria);
        return criteria;
    }

    @Override
    public List<NOCriteria> findByCriteriaIdAndUser(Long id, User user) {
        return noCriteriaRepository.findByCriteria_IdAndUser(id,user);
    }

    @Override
    public List<NOCriteria> findByUser(User user) {
        return noCriteriaRepository.findByUser(user);
    }

    @Override
    public List<CriteriaDto> calculateCriteriaPointsByUser(String userId) {
        List<CriteriaDto> dtos = new ArrayList<>();

        List<Object[]> noCriteriaFromUser = new ArrayList<>();
        noCriteriaFromUser.addAll(noCriteriaRepository.calculateCycleOfStudiesPoints(userId));
        noCriteriaFromUser.addAll(noCriteriaRepository.calculateTeachingPoints(userId));
        noCriteriaFromUser.addAll(noCriteriaRepository.calculateMentoringPoints(userId));

        int length = noCriteriaFromUser.size();

        for (int i = 0; i < length ; i++) {
            Object[] o = noCriteriaFromUser.get(i);
            Double points = (Double) o[1];
            String formattedPoints = String.format("%.2f", points);
            CriteriaDto dto = new CriteriaDto((String) o[0], Double.parseDouble(formattedPoints), false);  // Adjust index starting from 1
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public CriteriaDto calculatePointsByUserAndCriteriaId(String userId, Long criteriaId) {
        if(this.noCriteriaRepository.calculatePointsByUserAndCriteriaId(userId,criteriaId).isEmpty())
            return null;
        Object[] result = this.noCriteriaRepository.calculatePointsByUserAndCriteriaId(userId,criteriaId).get(0);
        String name = result[0] instanceof String ? (String) result[0] : null;
        Double points = null;
        if (result[1] instanceof Number) {
            points = ((Number) result[1]).doubleValue();
        }
        return new CriteriaDto(name, points, false);
    }


}
