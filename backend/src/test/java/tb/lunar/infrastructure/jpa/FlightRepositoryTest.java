package tb.lunar.infrastructure.jpa;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import tb.lunar.infrastructure.jpa.entity.Flight;
import tb.lunar.infrastructure.jpa.repo.FlightRepository;
import tb.lunar.infrastructure.jpa.repo.SpaceshipRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FlightRepositoryTest {

    @Autowired
    private FlightRepository repo;

    @Autowired
    private SpaceshipRepository ships;

    @Test
    void shouldSaveFlight() {
        var ship = ships.findAll().get(0);

        var flight = new Flight();
        flight.setSpaceshipId(ship.getId());
        flight.setFlightDate(LocalDate.of(2025, 9, 1));
        repo.save(flight);

        assertThat(flight.getId()).isNotNull();
        assertThat(repo.findById(flight.getId())).isPresent();
    }
}