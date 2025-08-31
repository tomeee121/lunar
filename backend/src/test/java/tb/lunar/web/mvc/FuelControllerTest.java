package tb.lunar.web.mvc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import tb.lunar.application.PricingService;
import tb.lunar.infrastructure.jpa.entity.Spaceship;
import tb.lunar.infrastructure.jpa.repo.SpaceshipRepository;
import tb.lunar.web.controller.FuelController;
import tb.lunar.web.dto.FuelDetailsDTO;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FuelController.class)
@AutoConfigureMockMvc(addFilters = false)   // ignore spring security
@ActiveProfiles("test")
class FuelControllerTest {

    @Autowired private MockMvc mvc;

    @MockBean private SpaceshipRepository repo;
    @MockBean private PricingService pricingService;

    @Test
    void shouldReturnFuelDetails() throws Exception {
        var ship = new Spaceship();
        ship.setId(1L);
        ship.setName("Dragon");
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(ship));

        var dto = new FuelDetailsDTO(1L,"Dragon","LOX/RP-1",
                new BigDecimal("569045"), new BigDecimal("1138090.00"),
                new BigDecimal("0.202"), new BigDecimal("229138.18"),
                new BigDecimal("3.0"), new BigDecimal("687414.54"));

        Mockito.when(pricingService.calculateFuelDetails(Mockito.eq(ship), Mockito.eq(20000.0)))
                .thenReturn(dto);

        mvc.perform(get("/api/fuel/1").param("cargoWeight","20000").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spaceshipId").value(1))
                .andExpect(jsonPath("$.spaceshipName").value("Dragon"))
                .andExpect(jsonPath("$.fuelType").value("LOX/RP-1"))
                .andExpect(jsonPath("$.flightCost").value(687414.54));
    }
}
