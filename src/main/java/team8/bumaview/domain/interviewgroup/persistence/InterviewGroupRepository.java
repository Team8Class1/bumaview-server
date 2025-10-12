package team8.bumaview.domain.interviewgroup.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team8.bumaview.domain.interviewgroup.domain.InterviewGroup;

@Repository
public interface InterviewGroupRepository extends JpaRepository<InterviewGroup, Long> {
}
