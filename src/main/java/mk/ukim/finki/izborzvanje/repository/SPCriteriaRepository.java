package mk.ukim.finki.izborzvanje.repository;

import mk.ukim.finki.izborzvanje.model.SPCriteria;
import mk.ukim.finki.izborzvanje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SPCriteriaRepository extends JpaRepository<SPCriteria, Long> {
    List<SPCriteria> findByCriteriaIdAndUser(Long id, User user);

    @Query(value = "SELECT c.name AS name, COUNT(*) * c.points AS points , c.activities_of_wider_interest AS activitiesOfWiderInterest " +
            "FROM SPCriteria sp " +
            "JOIN Criteria c ON sp.criteria_id = c.id " +
            "WHERE sp.user_id = :userId " +
            "GROUP BY sp.user_id, c.name, c.points, c.activities_of_wider_interest",
            nativeQuery = true)
    List<Object[]> calculateCriteriaPointsByUser(@Param("userId") String userId);
    @Query(value = "SELECT c.name, c.points * COUNT(*) as points FROM SPCriteria sp join Criteria c on sp.criteria_id = c.id\n" +
            "WHERE sp.user_id = :userId AND sp.criteria_id = :criteriaId \n" +
            "GROUP BY c.id", nativeQuery = true)
    List<Object[]> calculatePointsByUserAndCriteriaId(String userId, Long criteriaId);
}
