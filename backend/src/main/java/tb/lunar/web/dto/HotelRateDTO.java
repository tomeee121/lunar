package tb.lunar.web.dto;

import java.math.BigDecimal;

public record HotelRateDTO(
        String place,   // "CYCLER" | "SURFACE"
        String room,    // "standard"/"panoramic"/"penthouse" | "armstrong"/"conrad"/"aldrin"
        BigDecimal pricePerDay
) {}