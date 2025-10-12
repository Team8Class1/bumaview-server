package team8.bumaview.domain.interviewcategory.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team8.bumaview.domain.interviewcategory.domain.InterviewCategory;

@Repository
public interface InterviewCategoryRepository extends JpaRepository<InterviewCategory, Integer> {

    @Modifying
    @Query("delete from InterviewCategory ic where ic.interview.id =:interviewId")
    void removeByInterview_Id(Long interviewId);
}
