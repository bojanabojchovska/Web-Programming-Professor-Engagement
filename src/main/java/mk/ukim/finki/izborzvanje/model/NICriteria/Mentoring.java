package mk.ukim.finki.izborzvanje.model.NICriteria;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.Enumerations.StudyCycle;
import mk.ukim.finki.izborzvanje.model.Student;
import mk.ukim.finki.izborzvanje.model.User;

@Entity
@Data
@NoArgsConstructor
public class Mentoring {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student ;

    @ManyToOne
    private Criteria NIcriteria;

    private  String  Title;
    @ManyToOne
    private User user;

    public Mentoring(Student student, Criteria NIcriteria, String title, User user) {
        this.student = student;
        this.NIcriteria = NIcriteria;
        Title = title;
        this.user = user;
    }

    //
//    public NICriteria getNiCriteria() {
//        return NIcriteria;
//    }




}