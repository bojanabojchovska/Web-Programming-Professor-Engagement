package mk.ukim.finki.izborzvanje.repository.NI;

import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.NICriteria.Mentoring;

import mk.ukim.finki.izborzvanje.model.CriteriaDto;

import mk.ukim.finki.izborzvanje.model.NICriteria.Projects;
import mk.ukim.finki.izborzvanje.model.NICriteria.Publications;
import mk.ukim.finki.izborzvanje.model.User;
import mk.ukim.finki.izborzvanje.repository.CriteriaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicationsRepository extends JpaRepository<Publications,Long> {

    List<Publications> findByNIcriteriaAndUser(Criteria criteria, User user);
    List<Publications> findByNIcriteria(Criteria c);


    @Query( value = "SELECT name, SUM(points) " +
            " FROM (SELECT criteria.id as id, criteria.name as name," +
                    "criteria.points * publications.percentage_points as points " +
                    "FROM publications " +
                    "JOIN criteria ON criteria.id = publications.nicriteria_id " +
                    "WHERE publications.user_id = :userId " +
            ") " +
           " GROUP BY name;", nativeQuery = true
    )
    List<Object[]> calculatePublicationPoints(@Param("userId") String userId);

    @Query(value = "SELECT c.name AS name, SUM(c.points * p.percentage_points) AS points " +
            "FROM publications p " +
            "JOIN Criteria c ON p.nicriteria_id = c.id " +
            "WHERE p.user_id = :userId " +
            "GROUP BY p.user_id, c.name, c.points",
            nativeQuery = true)
    List<Object[]> calculateCriteriaPointsByUser(@Param("userId") String userId);

    @Query(value = "SELECT c.name AS name, SUM(c.points * p.percentage_points) AS points " +
            "FROM publications p " +
            "JOIN Criteria c ON p.nicriteria_id = c.id " +
            "WHERE p.user_id = :userId AND c.id = :criteriaId " +
            "GROUP BY p.user_id, c.name, c.points",
            nativeQuery = true)
    List<Object[]> calculatePointsByUserAndCriteriaId(@Param("userId") String userId, @Param("criteriaId") Long criteriaId);



}
