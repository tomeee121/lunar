package tb.lunar.web.controller;

import org.springframework.web.bind.annotation.*;
import tb.lunar.application.AvailabilityService;
import tb.lunar.web.dto.AvailabilityDTO;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/{destination}/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping
    public List<AvailabilityDTO> get(@PathVariable String destination,
                                     @RequestParam String date,
                                     @RequestParam(defaultValue = "1") int passengers,
                                     @RequestParam(defaultValue = "armstrong") String pkg,
                                     @RequestParam(required = false) String rooms // "CYCLER: xx/ SURFACE: yy"
    ) {
        return availabilityService.listAvailability(
                destination,
                LocalDate.parse(date),
                passengers,
                pkg,
                rooms
        );
    }
}