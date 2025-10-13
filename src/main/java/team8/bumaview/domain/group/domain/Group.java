package team8.bumaview.domain.group.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team8.bumaview.domain.group.api.dto.request.GroupDto;
import team8.bumaview.domain.user.domain.User;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "interview_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void modify(GroupDto groupDto) {
        this.name = groupDto.getName();
    }

    public GroupDto toDto() {
        return GroupDto.builder()
                .groupId(this.id)
                .name(this.name)
                .build();
    }

}
