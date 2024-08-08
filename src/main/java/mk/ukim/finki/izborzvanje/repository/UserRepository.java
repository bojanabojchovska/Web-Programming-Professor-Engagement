package mk.ukim.finki.izborzvanje.repository;

import mk.ukim.finki.izborzvanje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
