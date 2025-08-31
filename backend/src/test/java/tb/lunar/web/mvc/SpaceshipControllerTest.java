package tb.lunar.web.mvc;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tb.lunar.infrastructure.jpa.entity.FuelType;
import tb.lunar.infrastructure.jpa.entity.Spaceship;
import tb.lunar.infrastructure.jpa.repo.SpaceshipRepository;
import tb.lunar.web.controller.SpaceshipController;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SpaceshipController.class)
@AutoConfigureMockMvc(addFilters = false) // wyłącz security w teście
class SpaceshipControllerTest {

    @Autowired private MockMvc mvc;

    @MockBean
    private SpaceshipRepository repo;

    @Test
    void shouldReturnSpaceshipList() throws Exception {
        var fuel = new FuelType();
        fuel.setId(1L);
        fuel.setName("LOX/RP-1");

        var ship = new Spaceship();
        ship.setId(1L);
        ship.setName("Dragon");
        ship.setBooster("Falcon 9");
        ship.setWeight(new BigDecimal("549045"));
        ship.setMaximumCapacity(new BigDecimal("22800"));
        ship.setFuelType(fuel);

        Mockito.when(repo.findAll()).thenReturn(List.of(ship));

        mvc.perform(get("/api/spaceships").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Dragon"))
                .andExpect(jsonPath("$[0].booster").value("Falcon 9"))
                .andExpect(jsonPath("$[0].fuelType").value("LOX/RP-1"));
    }
}