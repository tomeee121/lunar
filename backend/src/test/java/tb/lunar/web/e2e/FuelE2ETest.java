package tb.lunar.web.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)   // ignore spring security
@ActiveProfiles("test")
class FuelE2ETest {

    @Autowired MockMvc mvc;

    @Test
    void shouldReturnFuelDetails() throws Exception {
        mvc.perform(get("/api/fuel/1").param("cargoWeight","20000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spaceshipId").isNumber())
                .andExpect(jsonPath("$.spaceshipName").isString())
                .andExpect(jsonPath("$.fuelType").isString())
                .andExpect(jsonPath("$.flightCost").isNumber());
    }
}
