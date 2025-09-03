package team8.bumaview.domain.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import team8.bumaview.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);

    User findByUsername(String username);
}
