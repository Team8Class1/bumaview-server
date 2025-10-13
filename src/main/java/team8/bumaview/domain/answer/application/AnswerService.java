package team8.bumaview.domain.answer.application;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team8.bumaview.domain.answer.api.dto.request.AnswerDto;
import team8.bumaview.domain.answer.api.dto.request.ReplyDto;
import team8.bumaview.domain.answer.domain.Answer;
import team8.bumaview.domain.answer.persistence.AnswerRepository;
import team8.bumaview.domain.answerlike.domain.AnswerLike;
import team8.bumaview.domain.answerlike.persistence.AnswerLikeRepository;
import team8.bumaview.domain.interview.domain.Interview;
import team8.bumaview.domain.interview.persistence.InterviewRepository;
import team8.bumaview.domain.user.domain.User;
import team8.bumaview.domain.user.persistence.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final InterviewRepository interviewRepository;
    private final AnswerLikeRepository answerLikeRepository;

    public void createAnswer(Long userId, AnswerDto answerDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않음"));
        Interview interview = interviewRepository.findById(answerDto.getInterviewId()).orElseThrow(() -> new EntityNotFoundException("인터뷰가 존재하지 않음"));
        Answer answer = Answer.builder()
                .likeCount(0)
                .user(user)
                .isPrivate(answerDto.getIsPrivate())
                .interview(interview)
                .answer(answerDto.getAnswer())
                .build();
        answerRepository.save(answer);
    }

    public void createReply(Long userId, ReplyDto replyDto) {
        User user = userRepository.findById(userId).orElse(null);
        Interview interview = interviewRepository.findById(replyDto.getInterviewId()).orElse(null);
        Answer parentAnswer = answerRepository.findById(replyDto.getParentAnswerId()).orElse(null);
        Answer answer = Answer.builder()
                .likeCount(0)
                .user(user)
                .isPrivate(replyDto.getIsPrivate())
                .interview(interview)
                .answer(replyDto.getAnswer())
                .parent(parentAnswer)
                .build();
        answerRepository.save(answer);
    }

    public void modifyAnswer(Long userId, Long answerId, AnswerDto answerDto) {
        User user = userRepository.findById(userId).orElse(null);
        Answer answer = answerRepository.findById(answerId).orElse(null);
        answer.modify(answerDto);
    }

    public void deleteAnswer(Long answerId) {
        answerRepository.deleteById(answerId);
    }

    public void likeAnswer(Long userId, Long answerId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않음"));
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new EntityNotFoundException("답변이 존재하지 않음"));

        if (answerLikeRepository.existsByUser_IdAndAnswer_Id(userId, answerId)) {
            answerLikeRepository.findByUser_IdAndAnswer_Id(userId, answerId).ifPresent(answerLikeRepository::delete);
            answer.decreaseLikeCount();
        } else {
            AnswerLike answerLike = AnswerLike.builder()
                    .user(user)
                    .answer(answer)
                    .build();
            answerLikeRepository.save(answerLike);
            answer.increaseLikeCount();
        }
    }
}
