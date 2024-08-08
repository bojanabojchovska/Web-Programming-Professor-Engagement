package mk.ukim.finki.izborzvanje.repository;

import mk.ukim.finki.izborzvanje.model.NOCriteria;
import mk.ukim.finki.izborzvanje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NOCriteriaRepository extends JpaRepository<NOCriteria, Long> {
    List<NOCriteria> findByCriteria_IdAndUser(Long id, User user);
    List<NOCriteria> findByUser(User user);

    @Query(value =
            "SELECT cname , SUM(points)\n" +
                    "FROM (SELECT no.user_id as cuser, c.name AS cname, c.points*15*no.number_of_classes*count(*) AS points\n" +
                    "      FROM nocriteria no JOIN criteria c ON no.criteria_id = c.id\n" +
                    "      WHERE c.id IN (1,2,3)"+
                    "      GROUP BY no.user_id, c.name, c.points, no.number_of_classes)\n" +
                    "WHERE cuser = :userId " +
                    "GROUP BY cname\n",
            nativeQuery = true)
    List<Object[]> calculateCycleOfStudiesPoints(@Param("userId") String userId);

    @Query(value =
            "SELECT cname , SUM(points)\n" +
                    "FROM (SELECT no.user_id as cuser, c.name AS cname, c.points*count(*) AS points\n" +
                    "      FROM nocriteria no JOIN criteria c ON no.criteria_id = c.id\n" +
                    "      WHERE c.id IN (4,5,6,7,8,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30)" +
                    "       GROUP BY no.user_id, c.name, c.points)\n" +
                    "WHERE cuser = :userId " +
                    "GROUP BY cname\n",
            nativeQuery = true)
    List<Object[]> calculateTeachingPoints(@Param("userId") String userId);

    @Query(value =
            "SELECT cname , SUM(points)\n" +
                    "FROM (SELECT no.user_id as cuser, c.name AS cname, c.points*no.students_for_mentorship*count(*) AS points\n" +
                    "      FROM nocriteria no JOIN criteria c ON no.criteria_id = c.id\n" +
                    "      WHERE c.id IN (10,11,12,13,14,15)" +
                    "       GROUP BY no.user_id, c.name, c.points, no.students_for_mentorship)\n" +
                    "WHERE cuser = :userId " +
                    "GROUP BY cname\n",
            nativeQuery = true)
    List<Object[]> calculateMentoringPoints(@Param("userId") String userId);

    @Query(value =
            "SELECT cname ,  SUM(points)\n" +
            "FROM (SELECT no.user_id as cuser, c.name AS cname, c.points*15*no.number_of_classes*count(*) AS points\n" +
            "      FROM nocriteria no JOIN criteria c ON no.criteria_id = c.id\n" +
            "      WHERE c.id= :criteriaId "+
            "      GROUP BY no.user_id, c.name, c.points, no.number_of_classes)\n" +
            "WHERE cuser = :userId " +
            "GROUP BY cname\n",
    nativeQuery = true)
    List<Object[]> calculatePointsByUserAndCriteriaId(String userId, Long criteriaId);

}
