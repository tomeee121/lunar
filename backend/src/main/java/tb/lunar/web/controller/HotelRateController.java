package tb.lunar.web.controller;

import org.springframework.web.bind.annotation.*;
import tb.lunar.infrastructure.jpa.repo.HotelRateRepository;
import tb.lunar.web.dto.HotelRateDTO;

import java.util.List;

@RestController
@RequestMapping("/api/{destination}/hotels")
public class HotelRateController {

    private final HotelRateRepository hotelRates;

    public HotelRateController(HotelRateRepository hotelRates) {
        this.hotelRates = hotelRates;
    }

    @GetMapping
    public List<HotelRateDTO> list(@PathVariable String destination,
                                   @RequestParam(required = false) String place) {
        var rates = (place == null)
                ? hotelRates.findByDestination(destination)
                : hotelRates.findByDestinationAndPlace(destination, place.toUpperCase());

        return rates.stream()
                .map(r -> new HotelRateDTO(r.getPlace(), r.getRoom(), r.getPricePerDay()))
                .toList();
    }
}