package team8.bumaview.domain.interview.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team8.bumaview.domain.interview.api.dto.request.ModifyInterviewDto;
import team8.bumaview.domain.interview.api.dto.request.UploadInterviewDto;
import team8.bumaview.domain.interview.api.dto.response.AllInterviewDto;
import team8.bumaview.domain.interview.api.dto.response.CreateInfoDto;
import team8.bumaview.domain.interview.api.dto.response.InterviewDto;
import team8.bumaview.domain.interview.application.InterviewService;

import java.util.List;

@RestController
@RequestMapping("/api/interview")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping("/single")
    public ResponseEntity<Void> singleUpload(@RequestBody UploadInterviewDto uploadInterviewDto) {
        System.out.println("uploadInterviewDto = " + uploadInterviewDto);
        interviewService.singleUpload(uploadInterviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/file")
    public ResponseEntity<Void> uploadInterviewsFromCsv(@RequestParam("file") MultipartFile file) {
        interviewService.uploadInterviewsFromCsv(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<Data<List<AllInterviewDto>>> getAllInterviews() {
        List<AllInterviewDto> allInfo = interviewService.getAllInterviews();
        Data<List<AllInterviewDto>> data = new Data<>(allInfo);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/{interviewId}")
    public ResponseEntity<InterviewDto> getInterviewById(@PathVariable Long interviewId) {
        InterviewDto interviewDto = interviewService.findById(interviewId);
        return ResponseEntity.status(HttpStatus.OK).body(interviewDto);
    }

    @GetMapping("/create")
    public ResponseEntity<?> getCreateInterview() {
        CreateInfoDto createInfoDto = interviewService.getCreateInfo();
        return ResponseEntity.status(HttpStatus.OK).body(createInfoDto);
    }

    @DeleteMapping("/{interviewId}")
    public ResponseEntity<Void> deleteInterviewById(@PathVariable Long interviewId) {
        interviewService.deleteById(interviewId);
        return ResponseEntity.status(HttpStatus.OK  ).build();
    }

    @PatchMapping("/{interviewId}")
    public ResponseEntity<Void> modifyInterview(@PathVariable Long interviewId, @RequestBody ModifyInterviewDto modifyInterviewDto) {
        System.out.println("modifyInterviewDto = " + modifyInterviewDto);
        interviewService.modifyInterview(interviewId, modifyInterviewDto);
        return ResponseEntity.status((HttpStatus.OK)).build();
    }

    @GetMapping("/search")
    public ResponseEntity<Data<List<AllInterviewDto>>> getInterviewsBySearch(
            @RequestParam(value = "questionAt", required = false) List<Integer> questionAt,
            @RequestParam(value = "companyId", required = false) List<Long> companyId,
            @RequestParam(value = "categoryId", required = false) List<Long> categoryId) {
        List<AllInterviewDto> interviews = interviewService.getInterviewsBySearch(questionAt, companyId, categoryId);
        Data<List<AllInterviewDto>> data = new Data<>(interviews);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @AllArgsConstructor
    @Getter
    static class Data<T> {
        private T data;
    }
}
