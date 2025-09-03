package tb.lunar.web.dto;

import java.time.LocalDate;

public record TripQuoteRequest(
        Long spaceshipId,
        LocalDate flightDate,
        int passengers,
        long payloadKg,      // additional payload; 0 = none
        String destination,  // "moon"
        String place,        // "CYCLER" / "SURFACE"
        String room,         // "armstrong" / "conrad" / "aldrin" / ...
        int nights,
        int rooms
) {}
