package team8.bumaview.domain.interviewgroup.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team8.bumaview.domain.interviewgroup.domain.InterviewGroup;

import java.util.List;

@Repository
public interface InterviewGroupRepository extends JpaRepository<InterviewGroup, Long> {
    void deleteAllByGroupId(Long groupId);

    @Query("select ig from InterviewGroup ig" +
            " join fetch ig.interview i" +
            " join fetch i.company c" +
            " join fetch i.interviewCategories ic" +
            " join fetch ic.category" +
            " where ig.group.id = :groupId")
    List<InterviewGroup> findAllByGroupId(@Param("groupId") Long groupId);

}
