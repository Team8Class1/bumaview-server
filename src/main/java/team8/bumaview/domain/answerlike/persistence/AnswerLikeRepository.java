package team8.bumaview.domain.answerlike.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team8.bumaview.domain.answerlike.domain.AnswerLike;

import java.util.Optional;

@Repository
public interface AnswerLikeRepository extends JpaRepository<AnswerLike, Long> {
    Optional<AnswerLike> findByUser_IdAndAnswer_Id(Long userId, Long answerId);
    boolean existsByUser_IdAndAnswer_Id(Long userId, Long answerId);
}
