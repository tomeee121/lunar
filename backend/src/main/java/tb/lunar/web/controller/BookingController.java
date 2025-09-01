package tb.lunar.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tb.lunar.application.BookingService;
import tb.lunar.infrastructure.jpa.entity.Spaceship;
import tb.lunar.infrastructure.jpa.repo.AppUserRepository;
import tb.lunar.infrastructure.jpa.repo.SpaceshipRepository;
import tb.lunar.web.dto.BookingDTOs.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService service;
    private final AppUserRepository users;
    private final SpaceshipRepository spaceships;

    public BookingController(BookingService service, AppUserRepository users, SpaceshipRepository spaceships) {
        this.service = service; this.users = users;
        this.spaceships = spaceships;
    }

    @PostMapping
    public ResponseEntity<BookingView> create(@AuthenticationPrincipal UserDetails ud,
                                              @RequestBody CreateBookingRequest req) {
        Long userId = users.findByEmail(ud.getUsername()).orElseThrow().getId();
        var b = service.create(userId, req.spaceshipId(), req.date(), req.passengers(), req.passengerName(), req.packageCode());

        var ship = spaceships.findById(b.getFlight().getSpaceshipId()).orElse(null);

        var v = new BookingView(
                b.getId(),
                b.getPassengerName(),
                b.getPassengersCount(),
                b.getPackageCode(),
                b.getFlight().getFlightDate(),
                b.getFlight().getSpaceshipId(),
                ship != null ? ship.getName() : null
        );
        return ResponseEntity.ok(v);
    }


    @GetMapping("/me")
    public ResponseEntity<List<BookingView>> myBookings(@AuthenticationPrincipal UserDetails ud) {
        Long userId = users.findByEmail(ud.getUsername()).orElseThrow().getId();
        var bookings = service.listForUser(userId);

        var shipIds = bookings.stream()
                .map(b -> b.getFlight().getSpaceshipId())
                .collect(Collectors.toSet());

        var shipNameById = spaceships.findAllById(shipIds).stream()
                .collect(Collectors.toMap(Spaceship::getId, Spaceship::getName));

        var list = bookings.stream().map(b ->
                new BookingView(
                        b.getId(),
                        b.getPassengerName(),
                        b.getPassengersCount(),
                        b.getPackageCode(),
                        b.getFlight().getFlightDate(),
                        b.getFlight().getSpaceshipId(),
                        shipNameById.get(b.getFlight().getSpaceshipId())
                )
        ).toList();

        return ResponseEntity.ok(list);
    }
}
