package mk.ukim.finki.izborzvanje.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SPCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Criteria criteria;

    @ManyToOne
    private User user;
    private String name;
    private String year;



    public SPCriteria(Criteria criteria, User user, String name, String year) {
        this.criteria = criteria;
        this.user = user;
        this.name = name;
        this.year = year;
    }


}
