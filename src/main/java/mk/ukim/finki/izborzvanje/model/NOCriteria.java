package mk.ukim.finki.izborzvanje.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import mk.ukim.finki.izborzvanje.model.Enumerations.Semester;
import mk.ukim.finki.izborzvanje.model.Enumerations.TypeOfClass;

@Entity
@Data
@NoArgsConstructor
public class NOCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Criteria criteria;

    private String schoolYear;

    private String semester;

    private String subject;

    @Enumerated(EnumType.STRING)
    private TypeOfClass typeOfClass;
    private int numberOfClasses;
    private int students;
    private int consultations;//will be calculated with query, no.Students*0.002
    private int studentsForMentorship;
    private String nameOfWorkTitle; // delo
    private String nameOfSubject;

    @ManyToOne
    private User user;

    public NOCriteria(Criteria criteria, User user, String schoolYear, String subject, String semester, TypeOfClass typeOfClass, int numberOfClasses, int students) {
        this.criteria = criteria;
        this.schoolYear = schoolYear;
        this.subject = subject;
        this.semester = semester;
        this.typeOfClass = typeOfClass;
        this.numberOfClasses = numberOfClasses;
        this.students = students;
        this.user = user;
    }
    public NOCriteria(Criteria criteria, User user, int studentsForMentorship){
        this.criteria= criteria;
        this.user = user;
        this.studentsForMentorship=studentsForMentorship;
    }
    public NOCriteria(Criteria criteria, User user, String nameOfWorkTitle){
        this.criteria= criteria;
        this.user = user;
        this.nameOfWorkTitle=nameOfWorkTitle;
    }
    public NOCriteria (Criteria criteria, User user){
        this.criteria=criteria;
        this.user=user;
    }



}
