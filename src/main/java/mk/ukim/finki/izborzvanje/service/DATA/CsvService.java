package mk.ukim.finki.izborzvanje.service.DATA;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.Enumerations.Field;
import mk.ukim.finki.izborzvanje.repository.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.FileReader;
import java.io.IOException;

@Service
public class CsvService {

    @Autowired
    private CriteriaRepository criteriaRepository;

    @PostConstruct
    @Transactional
    public void readCsvAndSave() {
        String filePath = "src/main/resources/criteria.csv"; // Adjust the path as needed
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Criteria criteria = new Criteria();
                long id = Long.parseLong(nextLine[0]);
                criteria.setId(id);
                criteria.setField(Field.valueOf(nextLine[1]));
                criteria.setName(nextLine[2]);
                criteria.setPoints(Double.parseDouble(nextLine[3]));
                if (id>=107 && id<=153)
                    criteria.setActivitiesOfWiderInterest(true);
                criteriaRepository.save(criteria);

            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}

