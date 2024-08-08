package mk.ukim.finki.izborzvanje.service.impl.NIimpl;
import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.CriteriaDto;
import mk.ukim.finki.izborzvanje.model.Enumerations.TypeOfThesis;
import mk.ukim.finki.izborzvanje.model.Enumerations.TypeOfWork;
import mk.ukim.finki.izborzvanje.model.NICriteria.Publications;
import mk.ukim.finki.izborzvanje.model.User;
import mk.ukim.finki.izborzvanje.repository.CriteriaRepository;
import mk.ukim.finki.izborzvanje.repository.NI.PublicationsRepository;
import mk.ukim.finki.izborzvanje.service.NI.PublicationsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublicationsServiceImpl implements PublicationsService {

    private final PublicationsRepository publicationsRepository;
    private  final CriteriaRepository criteriaRepository;

    public PublicationsServiceImpl(PublicationsRepository publicationsRepository, CriteriaRepository criteriaRepository) {
        this.publicationsRepository = publicationsRepository;
        this.criteriaRepository = criteriaRepository;
    }

    @Override
    public List<Publications> listAllPublications() {

        return publicationsRepository.findAll();
    }

    @Override
    public List<Publications> findByCriteriaIdAndUser(Long criteriaId, User user) {
        return this.publicationsRepository.findByNIcriteriaAndUser(criteriaRepository.findById(criteriaId).orElseThrow(RuntimeException::new),user);
    }

    @Override
    public Publications create(String authors, String title, int year, String locationName, Criteria NIcriteria, int noOfAuthors, User user) {
        return this.publicationsRepository.save(new Publications(
                authors,
                NIcriteria,
                title,
                locationName,
                year,
                noOfAuthors,
                user
        ));
    }

    @Override
    public Optional<Publications> edit(Long id, String authors, String title, int year, String locationName, int noOfAuthors) {
        Publications publications = this.publicationsRepository.findById(id).orElse(null);
        publications.setAuthors(authors);
        publications.setTitle(title);
        publications.setYear(year);
        publications.setLocationName(locationName);

        publications.setNoOfAuthors(noOfAuthors);
        return Optional.of(this.publicationsRepository.save(publications));
    }


    @Override
    public Publications findById(Long id) {
        return publicationsRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Publications delete(Long id) {
        Publications publications = publicationsRepository.findById(id).orElseThrow(RuntimeException::new);
        this.publicationsRepository.delete(publications);
        return publications;
    }

    @Override
    public Criteria findCriteriaID(Long id) {
        return this.criteriaRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Publications> findByCriteriaId(Long criteriaId) {
        return this.publicationsRepository.findByNIcriteria(criteriaRepository.findById(criteriaId).orElseThrow(RuntimeException::new));
    }

    @Override
    public List<CriteriaDto> calculatePublishingPoints(String userId) {
        List<CriteriaDto> criteriaDtos = new ArrayList<>();
        for(int i=0; i<this.publicationsRepository.calculatePublicationPoints(userId).size(); i ++){
            Object[] object = this.publicationsRepository.calculatePublicationPoints(userId).get(i);
            CriteriaDto dto = new CriteriaDto((String) object[0],(Double) object[1], false);
            criteriaDtos.add(dto);
        }
        return criteriaDtos;
    }

    public List<CriteriaDto> calculateCriteriaPointsByUser(String userId) {
        List<CriteriaDto> dtos = new ArrayList<>();
        int i = 1;
        for (Object[] o : this.publicationsRepository.calculateCriteriaPointsByUser(userId)) {
            CriteriaDto spCriteriaDto = new CriteriaDto((String) o[0], (Double) o[1], false);
            i++;
            dtos.add(spCriteriaDto);
        }
        return dtos;
    }

    @Override
    public CriteriaDto calculatePointsByUserAndCriteriaId(String userId, Long criteriaId) {
        if(this.publicationsRepository.calculatePointsByUserAndCriteriaId(userId,criteriaId).isEmpty())
            return null;
        Object[] result = this.publicationsRepository.calculatePointsByUserAndCriteriaId(userId,criteriaId).get(0);
        String name = result[0] instanceof String ? (String) result[0] : null;
        Double points = null;
        if (result[1] instanceof Number) {
            points = ((Number) result[1]).doubleValue();
        }
        return new CriteriaDto(name, points, false);
    }
}