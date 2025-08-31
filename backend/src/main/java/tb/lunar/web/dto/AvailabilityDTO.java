package tb.lunar.web.dto;

import java.math.BigDecimal;

public record AvailabilityDTO(
    Long spaceshipId, // id

    String spaceshipName,

    String packageCode, //code: "conrad", "aldrin", "armstrong"

    int passengers, //nr of interested passengers

    BigDecimal flightCost, //costs (startup, fuel, markup)

    BigDecimal hotelCost, //costs of hotels (cycler or/and surface) x nights

    BigDecimal totalCost, //flightCost + hotelCost
    BigDecimal perPassenger //price per passenger
) { }
