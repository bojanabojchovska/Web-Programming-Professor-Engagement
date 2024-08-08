package mk.ukim.finki.izborzvanje.model.NICriteria;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.Enumerations.TypeOfWork;
import mk.ukim.finki.izborzvanje.model.User;
import org.bouncycastle.asn1.pkcs.CertificationRequestInfo;

@Entity
@Data
@NoArgsConstructor
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String Title;

    private String fundedBy;

    @ManyToOne
    private Criteria NIcriteria;

    private int yearStart;

    private int yearEnd;

    @ManyToOne
    private User user;

    public Projects(String title, String fundedBy, Criteria NIcriteria,
                    int yearStart, int yearEnd,User user) {
        Title = title;
        this.fundedBy = fundedBy;
        this.NIcriteria = NIcriteria;
        this.user = user;
        this.yearStart = yearStart;
        this.yearEnd = yearEnd;
    }


}
