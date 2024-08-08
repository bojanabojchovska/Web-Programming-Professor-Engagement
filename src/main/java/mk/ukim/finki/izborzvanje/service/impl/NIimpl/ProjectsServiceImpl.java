package mk.ukim.finki.izborzvanje.service.impl.NIimpl;

import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.CriteriaDto;
import mk.ukim.finki.izborzvanje.model.NICriteria.Projects;
import mk.ukim.finki.izborzvanje.model.SPCriteria;
import mk.ukim.finki.izborzvanje.model.User;
import mk.ukim.finki.izborzvanje.repository.CriteriaRepository;
import mk.ukim.finki.izborzvanje.repository.NI.ProjectsRepository;
import mk.ukim.finki.izborzvanje.service.NI.ProjectsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectsServiceImpl implements ProjectsService {


    private final ProjectsRepository projectsRepository;
    private final CriteriaRepository criteriaRepository;

    public ProjectsServiceImpl(ProjectsRepository projectsRepository, CriteriaRepository criteriaRepository) {
        this.projectsRepository = projectsRepository;
        this.criteriaRepository = criteriaRepository;
    }

    @Override
    public List<Projects> listAllProjects() {

        return projectsRepository.findAll();
    }

    @Override
    public List<Projects> findByCriteriaIdAndUser(Long criteriaId, User user) {
        return this.projectsRepository.findByNIcriteriaAndUser(criteriaRepository.findById(criteriaId).orElseThrow(RuntimeException::new), user);
    }


    @Override
    public Projects create(String title, String fundedBy, Criteria NIcriteria, int yearStart, int yearEnd, User user) {
        return this.projectsRepository.save(new Projects(title, fundedBy, NIcriteria, yearStart, yearEnd, user));
    }


    @Override
    public Projects findById(Long id) {
        return projectsRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Projects delete(Long id) {
        Projects projects = projectsRepository.findById(id).orElseThrow(RuntimeException::new);
        this.projectsRepository.delete(projects);
        return projects;
    }

    @Override
    public Optional<Projects> edit(Long id, String title, String fundedBy, int yearStart, int yearEnd) {
        Projects projects = projectsRepository.findById(id).orElseThrow(RuntimeException::new);
        projects.setTitle(title);
        projects.setFundedBy(fundedBy);
        projects.setYearStart(yearStart);
        projects.setYearEnd(yearEnd);
        return Optional.of(projectsRepository.save(projects));
    }

    @Override
    public Criteria findCriteriaID(Long id) {
        return this.criteriaRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Projects> findByCriteriaId(Long criteriaId) {
        return this.projectsRepository.findByNIcriteria(criteriaRepository.findById(criteriaId).orElseThrow(RuntimeException::new));
    }

    @Override
    public List<CriteriaDto> calculateProjectPoints(String userId) {
        List<CriteriaDto> criteriaDtos = new ArrayList<>();
        for (int i = 0; i < this.projectsRepository.calculateProjectPoints(userId).size(); i++) {
            Object[] o = this.projectsRepository.calculateProjectPoints(userId).get(i);
            CriteriaDto dto = new CriteriaDto((String) o[0], (Double) o[1], false);
            criteriaDtos.add(dto);
        }
        return criteriaDtos;
    }

    public List<CriteriaDto> calculateCriteriaPointsByUser(String userId) {
        List<CriteriaDto> dtos = new ArrayList<>();
        int i = 1;
        for (Object[] o : this.projectsRepository.calculateCriteriaPointsByUser(userId)) {
            CriteriaDto spCriteriaDto = new CriteriaDto((String) o[0], (Double) o[1], false);
            i++;
            dtos.add(spCriteriaDto);
        }
        return dtos;
    }

    @Override
    public CriteriaDto calculatePointsByUserAndCriteriaId(String userId, Long criteriaId) {
        if(this.projectsRepository.calculatePointsByUserAndCriteriaId(userId,criteriaId).isEmpty())
            return null;
        Object[] result = this.projectsRepository.calculatePointsByUserAndCriteriaId(userId,criteriaId).get(0);
        String name = result[0] instanceof String ? (String) result[0] : null;
        Double points = null;
        if (result[1] instanceof Number) {
            points = ((Number) result[1]).doubleValue();
        }
        return new CriteriaDto(name, points, false);
    }
}

