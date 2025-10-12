package team8.bumaview.domain.bookmark.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team8.bumaview.domain.bookmark.application.BookmarkService;
import team8.bumaview.domain.interview.api.InterviewController;
import team8.bumaview.domain.interview.api.dto.response.AllInterviewDto;
import team8.bumaview.domain.interview.application.InterviewService;

import java.util.List;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PatchMapping("/{interviewId}")
    public ResponseEntity<Boolean> updateBookmark(@PathVariable Long interviewId) {

        // 토큰에서 아이디 가져옴
        Long userId = 1L;

        Boolean isBookmarked = bookmarkService.updateBookmark(userId, interviewId);
        return ResponseEntity.status(HttpStatus.OK).body(isBookmarked);
    }

    @GetMapping
    public ResponseEntity<?> getAllBookmarks() {

        // 토큰에서 아이디 가져옴
        Long userId = 1L;
        List<AllInterviewDto> allInfo = bookmarkService.getAllInterviews(userId);
        Data<List<AllInterviewDto>> data = new Data<>(allInfo);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @AllArgsConstructor
    @Getter
    static class Data<T> {
        private T data;
    }
}
