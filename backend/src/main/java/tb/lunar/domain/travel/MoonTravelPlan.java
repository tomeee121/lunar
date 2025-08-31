package tb.lunar.domain.travel;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MoonTravelPlan implements TravelPlan {

    @Override
    public String code() {
        return "moon";
    }

    @Override
    public List<Leg> route() {
        return List.of(
                new Leg("Earth", "LEO", false, false),
                new Leg("LEO", "Cycler", true, false),
                new Leg("Cycler", "LLO", false, false),
                new Leg("LLO", "Surface", false, true)
        );
    }

    @Override
    public Map<Location, Integer> staysForPackage(String packageCode) {
        return switch (packageCode.toLowerCase()) {
            case "conrad" -> Map.of(); // no nights in the hotel
            case "aldrin" -> Map.of(Location.CYCLER, 13);
            case "armstrong" -> Map.of(Location.CYCLER, 13, Location.SURFACE, 6);
            default -> throw new IllegalArgumentException("Unknown package: " + packageCode);
        };
    }
}