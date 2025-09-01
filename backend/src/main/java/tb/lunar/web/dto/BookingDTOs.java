package tb.lunar.web.dto;

import java.time.LocalDate;

public class BookingDTOs {
    public record CreateBookingRequest(Long spaceshipId, LocalDate date, int passengers,
                                       String passengerName, String packageCode) {}
    public record BookingView(Long id, String passengerName, int passengers, String packageCode,
                              LocalDate date, Long spaceshipId, String spaceshipName) {}
}
