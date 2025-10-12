package team8.bumaview.domain.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team8.bumaview.domain.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUserId(String username);

    User findByUserId(String username);
}
