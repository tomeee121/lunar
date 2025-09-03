package tb.lunar.web.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tb.lunar.application.QuoteService;
import tb.lunar.web.dto.TripQuoteDTO;
import tb.lunar.web.dto.TripQuoteRequest;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/offers")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    ///api/offers/quote?spaceshipId=1&date=2025-10-01&passengers=3&destination=moon&place=CYCLER&room=armstrong&nights=5&payloadKg=90&rooms=2
    @GetMapping("/quote")
    public TripQuoteDTO quoteGet(
            @RequestParam Long spaceshipId,
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate,
            @RequestParam int passengers,
            @RequestParam String destination,
            @RequestParam String place,
            @RequestParam String room,
            @RequestParam int nights,
            @RequestParam(defaultValue = "0") long payloadKg,   // NOWE
            @RequestParam(defaultValue = "0") int rooms         // NOWE
    ) {
        return quoteService.quote(new TripQuoteRequest(
                spaceshipId,
                flightDate,
                passengers,
                payloadKg,
                destination,
                place,
                room,
                nights,
                rooms
        ));
    }


    // POST /api/offers/quote  (body: TripQuoteRequest)
    @PostMapping("/quote")
    public TripQuoteDTO quotePost(@RequestBody TripQuoteRequest body) {
        return quoteService.quote(body);
    }
}
