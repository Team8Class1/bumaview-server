package team8.bumaview.domain.favorite.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team8.bumaview.domain.favorite.domain.Favorite;

import java.util.Collection;
import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByIdIn(Collection<Long> ids);
}
