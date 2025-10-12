package team8.bumaview.domain.group.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team8.bumaview.domain.group.domain.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
