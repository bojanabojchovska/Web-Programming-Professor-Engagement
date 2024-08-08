package mk.ukim.finki.izborzvanje.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="auth_user")
@Data
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String name;

    private String email;

    private String institution;

    private String fieldOfStudy;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<NOCriteria> noCriteria;

    public User(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
