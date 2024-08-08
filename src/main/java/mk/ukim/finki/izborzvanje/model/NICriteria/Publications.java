package mk.ukim.finki.izborzvanje.model.NICriteria;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.Enumerations.TypeOfThesis;
import mk.ukim.finki.izborzvanje.model.Enumerations.TypeOfWork;
import mk.ukim.finki.izborzvanje.model.Professor;
import mk.ukim.finki.izborzvanje.model.User;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Publications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String  authors;
    @ManyToOne
    private Criteria NIcriteria;

    private  String  Title;

    private int year;

    private String locationName;

    private String publicationMedium;


    private int NoOfAuthors;

    private double percentagePoints;

    @ManyToOne
    private User user;


    public Publications(String authors, Criteria NIcriteria, String title, String locationName, int year, int noOfAuthors,User user) {
        this.authors = authors;
        this.NIcriteria = NIcriteria;
        Title = title;
        this.locationName = locationName;
        this.year = year;
        NoOfAuthors = noOfAuthors;
        this.user = user;

        if(noOfAuthors == 2)
            percentagePoints = 0.9;
        else if(noOfAuthors == 3)
            percentagePoints = 0.8;
        else if(noOfAuthors == 1)
            percentagePoints = 1.0;
        else
            percentagePoints = 0.6;
    }

}