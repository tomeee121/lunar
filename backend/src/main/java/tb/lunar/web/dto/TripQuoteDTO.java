package tb.lunar.web.dto;

import java.time.LocalDate;

public record TripQuoteDTO(
        String spaceshipName,
        String booster,
        String fuelType,
        LocalDate flightDate,
        int passengers,
        long payloadKg,
        long estFuelKg,
        String destination,
        String place,
        String room,
        int nights,
        int rooms,
        MoneyDTO fuelCost,
        MoneyDTO hotelCost,
        MoneyDTO serviceFee,
        MoneyDTO govTax,
        MoneyDTO subtotal,
        MoneyDTO total
) {}
