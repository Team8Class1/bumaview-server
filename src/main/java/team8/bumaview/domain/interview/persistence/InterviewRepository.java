package team8.bumaview.domain.interview.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team8.bumaview.domain.interview.domain.Interview;

import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    @Query("select i from Interview i" +
            " join fetch i.company c" +
            " join fetch i.interviewCategories ic" +
            " join fetch ic.category ct")
    List<Interview> findAllInfo();

    @Query("select i from Interview i" +
            " join fetch i.company c" +
            " join fetch i.interviewCategories ic" +
            " join fetch ic.category ct" +
            " where i.id = :interviewId")
    List<Interview> findAllInfoById(Long interviewId);

    @Query("select i from Interview i" +
            " join fetch i.company c" +
            " join fetch i.interviewCategories ic" +
            " join fetch ic.category ct")
    List<Interview> findAllInfoWithBookmark();

    @Query("SELECT DISTINCT i FROM Interview i " +
            "LEFT JOIN i.company c " +
            "LEFT JOIN i.interviewCategories ic " +
            "LEFT JOIN ic.category cat " +
            "WHERE (:questionAts IS NULL OR FUNCTION('YEAR', i.questionAt) IN :questionAts) " +
            "AND (:companyIds IS NULL OR c.id IN :companyIds) " +
            "AND (:categoryIds IS NULL OR cat.id IN :categoryIds)")
    List<Interview> findInterviewsBySearch(@Param("questionAts") List<Integer> questionAts, @Param("companyIds") List<Long> companyIds, @Param("categoryIds") List<Long> categoryIds);
}
