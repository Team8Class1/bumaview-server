package team8.bumaview.domain.group.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team8.bumaview.domain.group.api.dto.request.AddGroupList;
import team8.bumaview.domain.group.api.dto.request.GroupDto;
import team8.bumaview.domain.group.application.GroupService;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestBody GroupDto groupDto) {

        // 토큰에서 추출
        Long userId = 1L;
        System.out.println("Controller groupDto = " + groupDto);
        groupService.createGroup(userId, groupDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{groupId}")
    public ResponseEntity<Void> createGroup(@PathVariable Long groupId,@RequestBody AddGroupList addGroupList) {
        System.out.println("addGroupList = " + addGroupList);
        groupService.addInterviews(groupId, addGroupList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{groupId}")
    public ResponseEntity<Void> updateGroup(@PathVariable Long groupId, @RequestBody GroupDto groupDto) {
        System.out.println("groupDto = " + groupDto);
        groupService.modifyGroup(groupId, groupDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
