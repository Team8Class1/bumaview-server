package team8.bumaview.domain.answer.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team8.bumaview.domain.answer.api.dto.request.AnswerDto;
import team8.bumaview.domain.answer.api.dto.request.ReplyDto;
import team8.bumaview.domain.answer.application.AnswerService;

@RestController
@RequestMapping("/api/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<Void> createAnswer(@RequestBody AnswerDto answerDto) {

        Long userId = 1L;
        System.out.println("answerDto = " + answerDto);
        answerService.createAnswer(userId, answerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/reply")
    public ResponseEntity<Void> replyAnswer(@RequestBody ReplyDto replyDto) {
        Long userId = 1L;
        answerService.createReply(userId, replyDto);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{answerId}")
    public ResponseEntity<Void> modifyAnswer(@PathVariable Long answerId, @RequestBody AnswerDto answerDto) {
        Long userId = 1L;
        answerService.modifyAnswer(userId, answerId, answerDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
