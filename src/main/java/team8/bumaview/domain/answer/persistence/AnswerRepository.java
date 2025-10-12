package team8.bumaview.domain.answer.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import team8.bumaview.domain.answer.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
