package team8.bumaview.domain.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team8.bumaview.domain.user.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUserId(String username);

    User findByUserId(String username);

    @Query("select u from User u" +
            " left join fetch u.userFavorites uf" +
            " left join fetch uf.favorite" +
            " where u.id = :userId")
    Optional<User> findWithFavoriteById(Long userId);
}
