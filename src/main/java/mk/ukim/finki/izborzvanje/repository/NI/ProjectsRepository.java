package mk.ukim.finki.izborzvanje.repository.NI;


import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.NICriteria.Mentoring;
import mk.ukim.finki.izborzvanje.model.NICriteria.Projects;
import mk.ukim.finki.izborzvanje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectsRepository extends JpaRepository<Projects,Long> {
    List<Projects> findByNIcriteriaAndUser(Criteria criteria, User user);
    List<Projects> findByNIcriteria(Criteria c);
    @Query(value="SELECT c.name, c.points * COUNT(*) as points\n" +
            "FROM projects p join criteria c on c.id = p.nicriteria_id\n" +
            "WHERE p.user_id = :userId \n" +
            "GROUP BY c.name, c.points", nativeQuery = true)
    List<Object[]> calculateProjectPoints(String userId);

    @Query(value = "SELECT c.name AS name, SUM(c.points) AS points " +
            "FROM projects p " +
            "JOIN Criteria c ON p.nicriteria_id = c.id " +
            "WHERE p.user_id = :userId " +
            "GROUP BY p.user_id, c.name, c.points",
            nativeQuery = true)
    List<Object[]> calculateCriteriaPointsByUser(@Param("userId") String userId);

    @Query(value = "SELECT c.name AS name, SUM(c.points) AS points " +
            "FROM projects p " +
            "JOIN Criteria c ON p.nicriteria_id = c.id " +
            "WHERE p.user_id = :userId AND c.id = :criteriaId " +
            "GROUP BY p.user_id, c.name, c.points",
            nativeQuery = true)
    List<Object[]> calculatePointsByUserAndCriteriaId(@Param("userId") String userId, @Param("criteriaId") Long criteriaId);


}
