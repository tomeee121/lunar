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
class AvailabilityE2ETest {

    @Autowired MockMvc mvc;

    @Test
    void shouldReturnAvailabilityForMoon() throws Exception {
        mvc.perform(get("/api/moon/availability")
                        .param("date","2025-09-01")
                        .param("passengers","4")
                        .param("pkg","armstrong"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].spaceshipName").isString());
    }
}
