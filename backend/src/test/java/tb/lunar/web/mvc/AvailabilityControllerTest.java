package tb.lunar.web.mvc;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tb.lunar.application.AvailabilityService;
import tb.lunar.web.controller.AvailabilityController;
import tb.lunar.web.dto.AvailabilityDTO;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AvailabilityController.class)
@AutoConfigureMockMvc(addFilters = false)   // ignore spring security
@ActiveProfiles("test")
class AvailabilityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AvailabilityService availabilityService;

    @Test
    void shouldReturnAvailabilityList() throws Exception {
        var dto = new AvailabilityDTO(
                1L, "Dragon", "armstrong", 3,
                new BigDecimal("100000.00"),
                new BigDecimal("12000.00"),
                new BigDecimal("112000.00"),
                new BigDecimal("37333.33")
        );
        Mockito.when(availabilityService.listAvailability(
                Mockito.eq("moon"), Mockito.any(), Mockito.eq(3), Mockito.eq("armstrong"), Mockito.isNull()
        )).thenReturn(List.of(dto));

        mvc.perform(get("/api/moon/availability")
                        .param("date", "2025-09-01")
                        .param("passengers", "3")
                        .param("pkg", "armstrong")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].spaceshipName").value("Dragon"));
    }
}
