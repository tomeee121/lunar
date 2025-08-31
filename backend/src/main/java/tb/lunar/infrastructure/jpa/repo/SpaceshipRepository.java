package tb.lunar.infrastructure.jpa.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tb.lunar.infrastructure.jpa.entity.Spaceship;

import java.util.List;

public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {

    /**
     * entity graph to create join instead of querrying N+1 type
     */
    @EntityGraph(attributePaths = "fuelType")
    @Query("SELECT s FROM Spaceship s")
    List<Spaceship> findAllWithFuel();
}