package mk.ukim.finki.izborzvanje.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mk.ukim.finki.izborzvanje.model.Enumerations.Field;


@Entity
@Data
@NoArgsConstructor
public class Criteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id; // 1
    @Column(length = 600)
    private String name; // prv ciklus
    @Getter
    private double points; //2

//    @OneToMany(mappedBy = "criteria")
//    private NOCriteria noCriteria; //1 Sasho , 2 Ana

    @Enumerated(EnumType.STRING)
    private Field field; // NO, NI, SP
    private boolean ActivitiesOfWiderInterest;
    //bool mentoring, bool project , bool publ

    public Criteria(String name, double points, Field field) {
        this.name = name;
        this.points = points;
        this.field = field;
        this.ActivitiesOfWiderInterest = false;
    }

}
