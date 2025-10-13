package team8.bumaview.domain.group.application;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team8.bumaview.domain.category.api.dto.response.CategoryList;
import team8.bumaview.domain.group.api.dto.request.AddGroupList;
import team8.bumaview.domain.group.api.dto.request.GroupDto;
import team8.bumaview.domain.group.domain.Group;
import team8.bumaview.domain.group.persistence.GroupRepository;
import team8.bumaview.domain.interview.api.dto.response.AllInterviewDto;
import team8.bumaview.domain.interview.domain.Interview;
import team8.bumaview.domain.interview.persistence.InterviewRepository;
import team8.bumaview.domain.interviewcategory.domain.InterviewCategory;
import team8.bumaview.domain.interviewgroup.domain.InterviewGroup;
import team8.bumaview.domain.interviewgroup.persistence.InterviewGroupRepository;
import team8.bumaview.domain.user.domain.User;
import team8.bumaview.domain.user.persistence.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final InterviewRepository interviewRepository;
    private final InterviewGroupRepository interviewGroupRepository;

    public void createGroup(Long userId, GroupDto groupDto) {
        System.out.println("Service groupDto = " + groupDto);
        User user = userRepository.findById(userId).orElse(null);
        Group group = Group.builder()
                .name(groupDto.getName())
                .user(user)
                .build();
        groupRepository.save(group);
    }

    public void addInterviews(Long groupId, AddGroupList addGroupList) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("그룹이 존재하지 않음"));
        for(Long interviewId : addGroupList.getInterviewIdList()) {
            Interview interview = interviewRepository.findById(interviewId).orElseThrow(() -> new EntityNotFoundException("인터뷰가 존재하지 않음"));
            InterviewGroup interviewGroup = InterviewGroup.builder()
                    .interview(interview)
                    .group(group)
                    .build();
            System.out.println("add interview #######");
            interviewGroupRepository.save(interviewGroup);
        }
    }

    public void modifyGroup(Long groupId, GroupDto groupDto) {

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("그룹이 존재하지 않음"));

        group.modify(groupDto);

        groupRepository.save(group);
    }

    public void deleteGroup(Long groupId) {
        interviewGroupRepository.deleteAllByGroupId(groupId); // 자식 삭제
        groupRepository.deleteById(groupId);
    }

    public List<GroupDto> findGroup(Long userId) {
        return groupRepository.findByUserId(userId).stream()
                .map(Group::toDto)
                .collect(Collectors.toList());
    }

    public List<AllInterviewDto> findInterviewsByGroupId(Long groupId) {
        List<InterviewGroup> interviewGroups = interviewGroupRepository.findAllByGroupId(groupId);

        return interviewGroups.stream()
                .map(InterviewGroup::getInterview)
                .map(interview -> {
                    List<CategoryList> categoryLists = new ArrayList<>();
                    for (InterviewCategory interviewCategory : interview.getInterviewCategories()) {
                        categoryLists.add(CategoryList.builder()
                                .categoryId(interviewCategory.getCategory().getId())
                                .categoryName(interviewCategory.getCategory().getName())
                                .build());
                    }

                    return AllInterviewDto.builder()
                            .interviewId(interview.getId())
                            .question(interview.getQuestion())
                            .categoryList(categoryLists)
                            .companyId(interview.getCompany().getId())
                            .companyName(interview.getCompany().getName())
                            .questionAt(interview.getQuestionAt())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
