package tb.lunar.infrastructure.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import tb.lunar.infrastructure.jpa.entity.Spaceship;
import tb.lunar.infrastructure.jpa.repo.SpaceshipRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpaceshipRepositoryTest {

    @Autowired
    private SpaceshipRepository repo;
    @Test
    void shouldFindAllSpaceshipsWithFuel() {
        List<Spaceship> ships = repo.findAllWithFuel();
        assertThat(ships).isNotEmpty();
        assertThat(ships.get(0).getFuelType()).isNotNull();
    }
}