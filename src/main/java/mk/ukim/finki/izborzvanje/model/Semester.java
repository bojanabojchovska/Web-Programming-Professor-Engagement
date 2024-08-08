package mk.ukim.finki.izborzvanje.model;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.izborzvanje.model.Enumerations.StudyCycle;

import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Semester {

    // 2022/2023-W -- ke go stavam da e generated

    @Id
    private String code;

    // 2022/2023
    private String year;

    @Enumerated(EnumType.STRING)
    private SemesterType semesterType;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate enrollmentStartDate;

    private LocalDate enrollmentEndDate;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<StudyCycle> cycle;

    @Enumerated(EnumType.STRING)
    private SemesterState state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Semester semester = (Semester) o;
        return getCode() != null && Objects.equals(getCode(), semester.getCode());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
