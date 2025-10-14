package team8.bumaview.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import team8.bumaview.domain.answerlike.domain.AnswerLike;
import team8.bumaview.domain.bookmark.domain.Bookmark;
import team8.bumaview.domain.favorite.domain.Favorite;
import team8.bumaview.domain.user.api.dto.response.UserInfoDto;
import team8.bumaview.domain.userfavorite.domain.UserFavorite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String password;
    private String email;
    private Role role;
    private Date birthday;

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserFavorite> userFavorites = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<AnswerLike> answerLikes = new ArrayList<>();

    public static User create(String userId, String password, String email, Date birthday) {
        User user = new User();
        user.userId = userId;
        user.password = password;
        user.email = email;
        user.birthday = birthday;
        user.role = Role.BASIC;
        return user;
    }

    public UserInfoDto toUserInfoDto(List<UserFavorite> userFavorites) {
        return UserInfoDto.builder()
                .userId(userId)
                .userSequenceId(id)
                .email(email)
                .birthday(birthday)
                .role(role)
                .favoriteList(userFavorites.stream().map(UserFavorite::getFavorite).map(Favorite::getName).collect(Collectors.toList()))
                .build();
    }
}
