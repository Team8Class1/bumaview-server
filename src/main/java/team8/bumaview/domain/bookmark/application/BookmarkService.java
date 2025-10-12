package team8.bumaview.domain.bookmark.application;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team8.bumaview.domain.bookmark.domain.Bookmark;
import team8.bumaview.domain.bookmark.persistence.BookmarkRepository;
import team8.bumaview.domain.category.api.dto.response.CategoryList;
import team8.bumaview.domain.interview.api.dto.response.AllInterviewDto;
import team8.bumaview.domain.interview.domain.Interview;
import team8.bumaview.domain.interview.persistence.InterviewRepository;
import team8.bumaview.domain.interviewcategory.domain.InterviewCategory;
import team8.bumaview.domain.user.domain.User;
import team8.bumaview.domain.user.persistence.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final InterviewRepository interviewRepository;

    public Boolean updateBookmark(Long userId, Long interviewId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않음"));
        Interview interview = interviewRepository.findById(interviewId).orElseThrow(() -> new EntityNotFoundException("인터뷰가 존재하지 않음"));

        boolean isExist = false;

        Boolean isBookmarked = null;

        for (Bookmark bookmark : user.getBookmarks()) {
            if(bookmark.getInterview().getId().equals(interview.getId())) {
                isExist = true;
                bookmark.updateBookmark();
                isBookmarked = bookmark.getIsBookmarked();
                bookmarkRepository.save(bookmark);
                break;
            }
        }

        if(!isExist) {
            Bookmark bookmark = Bookmark.builder()
                    .interview(interview)
                    .user(user)
                    .isBookmarked(true)
                    .build();
            bookmarkRepository.save(bookmark);
            isBookmarked = bookmark.getIsBookmarked();
        }
        return isBookmarked;
    }

    public List<AllInterviewDto> getAllInterviews(Long userId) {
        System.out.println("###################");
        List<Interview> interviews = interviewRepository.findAllInfoById(userId);
        System.out.println("interviews = " + interviews.size());
        List<AllInterviewDto> allInterviews = new ArrayList<>();
        for (Interview interview : interviews) {
            List<CategoryList> categoryList = new ArrayList<>();
            for(InterviewCategory ic : interview.getInterviewCategories()) {
                CategoryList category = CategoryList.builder()
                        .categoryId(ic.getCategory().getId())
                        .categoryName(ic.getCategory().getName())
                        .build();
                categoryList.add(category);
            }

            AllInterviewDto dto = AllInterviewDto.builder()
                    .interviewId(interview.getId())
                    .question(interview.getQuestion())
                    .questionAt(interview.getQuestionAt())
                    .companyId(interview.getCompany().getId())
                    .companyName(interview.getCompany().getName())
                    .categoryList(categoryList)
                    .build();
            allInterviews.add(dto);
        }
        return allInterviews;
    }
}
