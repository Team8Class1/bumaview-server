package team8.bumaview.domain.interview.application;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team8.bumaview.domain.answer.api.dto.response.AnswerDto;
import team8.bumaview.domain.answer.domain.Answer;
import team8.bumaview.domain.category.api.dto.response.CategoryList;
import team8.bumaview.domain.category.domain.Category;
import team8.bumaview.domain.category.persistence.CategoryRepository;
import team8.bumaview.domain.company.api.dto.response.CompanyDto;
import team8.bumaview.domain.company.domain.Company;
import team8.bumaview.domain.company.persistence.CompanyRepository;
import team8.bumaview.domain.interview.api.dto.request.ModifyInterviewDto;
import team8.bumaview.domain.interview.api.dto.request.UploadInterviewDto;
import team8.bumaview.domain.interview.api.dto.response.AllInterviewDto;
import team8.bumaview.domain.interview.api.dto.response.CreateInfoDto;
import team8.bumaview.domain.interview.api.dto.response.InterviewDto;
import team8.bumaview.domain.interview.domain.Interview;
import team8.bumaview.domain.interview.persistence.InterviewRepository;
import team8.bumaview.domain.interviewcategory.domain.InterviewCategory;
import team8.bumaview.domain.interviewcategory.persistence.InterviewCategoryRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final CompanyRepository companyRepository;
    private final InterviewCategoryRepository interviewCategoryRepository;
    private final CategoryRepository categoryRepository;

    public void singleUpload(UploadInterviewDto interviewDto) {
        System.out.println("interviewDto = " + interviewDto.getCompanyId());
        for(Long id : interviewDto.getCategoryList()) {
            System.out.println("id = " + id);
        }
        Optional<Company> company = companyRepository.findById(interviewDto.getCompanyId());

        List<Category> categoryList = categoryRepository.findAllById(interviewDto.getCategoryList());

        Interview interview = Interview.builder()
                .question(interviewDto.getQuestion())
                .company(company.orElse(null))
                .questionAt(interviewDto.getQuestionAt())
                .build();

        for (Category category : categoryList) {
            InterviewCategory interviewCategory = InterviewCategory.builder()
                    .category(category)
                    .interview(interview)
                    .build();
            interviewCategoryRepository.save(interviewCategory);
        }

        interviewRepository.save(interview);
    }

    public void uploadInterviewsFromCsv(MultipartFile file) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] nextLine;

            // 첫 줄을 열 이름
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                String question = nextLine[0];
                String categoryName = nextLine[1];
                String companyName = nextLine[2];
                String questionAtStr = nextLine[3];

                System.out.println("question.length = " + question.length());
//                System.out.printf("%s - %s - %s - %s\n", question, categoryName, companyName, questionAtStr);

                Company company = companyRepository.findByName(companyName)
                        .orElseThrow(() -> new EntityNotFoundException("Company not found: " + companyName));

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
                Interview interview = Interview.builder()
                        .question(question)
                        .company(company)
                        .questionAt(formatter.parse(questionAtStr))
                        .build();

                Category category = categoryRepository.findByName(categoryName)
                        .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryName));

                InterviewCategory interviewCategory = InterviewCategory.builder()
                        .category(category)
                        .interview(interview)
                        .build();
                interviewCategoryRepository.save(interviewCategory);
                interviewRepository.save(interview);
            }
        } catch (IOException | CsvValidationException | ParseException e) {
            throw new RuntimeException("Failed to parse CSV file", e);
        }
    }

    public List<AllInterviewDto> getAllInterviews() {
        System.out.println("###################");
        List<Interview> interviews = interviewRepository.findAllInfo();
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

    public InterviewDto findById(Long interviewId) {
//        Interview interview = interviewRepository.findAllInfoById(interviewId);
        Interview interview = interviewRepository.findById(interviewId).orElseThrow(() -> new EntityNotFoundException("인터뷰 없음"));
        List<CategoryList> categoryList = new ArrayList<>();
        for(InterviewCategory ic : interview.getInterviewCategories()) {
            CategoryList category = CategoryList.builder()
                    .categoryId(ic.getCategory().getId())
                    .categoryName(ic.getCategory().getName())
                    .build();
            categoryList.add(category);
        }

        List<AnswerDto> answerDtoList = new ArrayList<>();
        for (Answer answer : interview.getAnswers()) {
            if (answer.getParent() == null && !answer.getIsPrivate()) { // 최상위 답변 중 public인 것만 처리
                AnswerDto answerDto = AnswerDto.builder()
                        .userSequenceId(answer.getUser().getId())
                        .userId(answer.getUser().getUserId())
                        .like(answer.getLikeCount())
                        .answerId(answer.getId())
                        .answer(answer.getAnswer())
                        .replies(buildReplies(answer)) // 대댓글 목록 생성
                        .build();
                answerDtoList.add(answerDto);
            }
        }

        return InterviewDto.builder()
                .interviewId(interview.getId())
                .question(interview.getQuestion())
                .categoryList(categoryList)
                .companyId(interview.getCompany().getId())
                .companyName(interview.getCompany().getName())
                .questionAt(interview.getQuestionAt())
                .answer(answerDtoList)
                .build();
    }

    private List<AnswerDto> buildReplies(Answer parentAnswer) {
        List<AnswerDto> replyDtoList = new ArrayList<>();
        for (Answer reply : parentAnswer.getChildren()) {
            if (!reply.getIsPrivate()) { // 대댓글 중 public인 것만 처리
                AnswerDto replyDto = AnswerDto.builder()
                        .userSequenceId(reply.getUser().getId())
                        .userId(reply.getUser().getUserId())
                        .like(reply.getLikeCount())
                        .answerId(reply.getId())
                        .answer(reply.getAnswer())
                        .replies(null) // 대댓글의 대댓글은 없으므로 null 처리
                        .build();
                replyDtoList.add(replyDto);
            }
        }
        return replyDtoList;
    }

    public CreateInfoDto getCreateInfo() {
        List<Company> companyList = companyRepository.findAll();
        List<Category> categoryList = categoryRepository.findAll();
        List<CompanyDto> companyDtoList = new ArrayList<>();
        List<CategoryList> categoryDtoList = new ArrayList<>();
        for(Company company : companyList) {
            companyDtoList.add(CompanyDto.builder()
                            .CompanyId(company.getId())
                            .companyName(company.getName())
                        .build());
        }
        for(Category category : categoryList) {
            categoryDtoList.add(CategoryList.builder()
                            .categoryName(category.getName())
                            .categoryId(category.getId())
                    .build());
        }
        return CreateInfoDto.builder()
                .companyList(companyDtoList)
                .categoryList(categoryDtoList)
                .build();
    }

    public void deleteById(Long interviewId) {
        interviewRepository.deleteById(interviewId);
    }

    public void modifyInterview(Long interviewId, ModifyInterviewDto modifyInterviewDto) {
        Interview interview = interviewRepository.findById(interviewId).orElseThrow(() -> new EntityNotFoundException("인터뷰 없음"));
        Company company = companyRepository.findById(interview.getCompany().getId()).orElse(null);
        List<InterviewCategory> categoryList = interview.getInterviewCategories();
        for(InterviewCategory ic : categoryList) {
            interviewCategoryRepository.removeByInterview_Id(interviewId);
        }
        List<Category> categories =  categoryRepository.findAllById(modifyInterviewDto.getCategory());
        interviewCategoryRepository.removeByInterview_Id(interviewId);
        for(Category category : categories) {
            InterviewCategory ic = InterviewCategory.builder()
                    .category(category)
                    .interview(interview)
                    .build();
            interviewCategoryRepository.save(ic);
        }
        interview.update(modifyInterviewDto, company);
    }

    public List<AllInterviewDto> getInterviewsBySearch(List<Integer> questionAts, List<Long> companyIds, List<Long> categoryIds) {
        List<Interview> interviews = interviewRepository.findInterviewsBySearch(
                (questionAts != null && !questionAts.isEmpty()) ? questionAts : null,
                (companyIds != null && !companyIds.isEmpty()) ? companyIds : null,
                (categoryIds != null && !categoryIds.isEmpty()) ? categoryIds : null);
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
