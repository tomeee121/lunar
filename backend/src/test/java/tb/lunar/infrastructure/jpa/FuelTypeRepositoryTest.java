package tb.lunar.infrastructure.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import tb.lunar.infrastructure.jpa.entity.FuelType;
import tb.lunar.infrastructure.jpa.repo.FuelTypeRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FuelTypeRepositoryTest {

    @Autowired
    private FuelTypeRepository repo;

    @Test
    void shouldFindFuelTypeByName() {
        Optional<FuelType> fuel = repo.findByName("LOX/RP-1");
        assertThat(fuel).isPresent();
        assertThat(fuel.get().getCarryCapacity()).isNotNull();
    }
}