package mk.ukim.finki.izborzvanje.repository.NI;

import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.NICriteria.Mentoring;
import mk.ukim.finki.izborzvanje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
@Repository
public interface MentoringRepository extends JpaRepository<Mentoring,Long> {

    List<Mentoring> findByNIcriteriaAndUser(Criteria c, User user);
    List<Mentoring> findByNIcriteria(Criteria c);


    @Query(value = "SELECT c.name AS name, COUNT(*) * c.points AS points " +
            "FROM mentoring m " +
            "JOIN Criteria c ON m.nicriteria_id = c.id " +
            "WHERE m.user_id = :userId " +
            "GROUP BY m.user_id, c.name, c.points",
            nativeQuery = true)
    List<Object[]> calculateCriteriaPointsByUser(@Param("userId") String userId);


    @Query(value = "SELECT c.name AS name, COUNT(*) * c.points AS points " +
            "FROM mentoring m " +
            "JOIN Criteria c ON m.nicriteria_id = c.id " +
            "WHERE m.user_id = :userId AND c.id = :criteriaId " +
            "GROUP BY m.user_id, c.name, c.points",
            nativeQuery = true)
    List<Object[]> calculatePointsByUserAndCriteriaId(@Param("userId") String userId, @Param("criteriaId") Long criteriaId);
}