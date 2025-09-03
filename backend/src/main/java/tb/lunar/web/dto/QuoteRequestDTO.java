package tb.lunar.web.dto;

import java.time.LocalDate;

public record QuoteRequestDTO(
        Long spaceshipId,
        LocalDate flightDate,
        int passengers,
        long payloadKg,
        String destination,
        String place,
        String room,
        int nights,
        int rooms
) {}
