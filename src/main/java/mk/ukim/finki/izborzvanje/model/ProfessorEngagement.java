package mk.ukim.finki.izborzvanje.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class ProfessorEngagement extends TrackedHistoryEntity {

    @Id
    private String id;

    @ManyToOne
    private Professor professor;

    @ManyToOne
    private Semester semester;

    @ManyToOne
    private JoinedSubject subject;

    @Enumerated(EnumType.STRING)
    private ClassType classType;

    private Float numberOfClasses;

    private Boolean sharedWithOtherTeacher;

    @Enumerated(EnumType.STRING)
    private Language language;

    private Short numberOfStudents;

    private Boolean consultative;

    @Column(length = 2000)
    private String note;


    public ProfessorEngagement(Professor professor,
                               Semester semester,
                               JoinedSubject subject,
                               ClassType classType,
                               Language language,
                               Float numberOfClasses,
                               Boolean otherTeacher,
                               Short numberOfStudents,
                               Boolean consultative,
                               String note) {
        this.id = generateId(professor.getId(), semester.getCode(),
                subject.getAbbreviation(), classType, language);
        this.professor = professor;
        this.semester = semester;
        this.subject = subject;
        this.classType = classType;
        this.numberOfClasses = numberOfClasses;
        this.sharedWithOtherTeacher = otherTeacher;
        this.language = language;
        this.numberOfStudents = numberOfStudents;
        this.consultative = consultative;
        this.note = note;
    }

    public static String generateId(String prof, String semester, String subject, ClassType type, Language lang) {
        return String.format("%s-%s-%s-%s-%s", prof, semester, subject, type.name(), lang.name());
    }


}
