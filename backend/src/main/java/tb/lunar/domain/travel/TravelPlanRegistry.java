package tb.lunar.domain.travel;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * all travel plans registered here
 */
@Component
public class TravelPlanRegistry {

    private final Map<String, TravelPlan> plansByCode;

    public TravelPlanRegistry(List<TravelPlan> plans) {
        this.plansByCode = plans.stream()
                .collect(Collectors.toMap(p -> p.code().toLowerCase(), p -> p));
    }

    public TravelPlan get(String code) {
        if (code == null) throw new IllegalArgumentException("Destination code is required");
        var plan = plansByCode.get(code.toLowerCase());
        if (plan == null) throw new IllegalArgumentException("Unknown travel plan: " + code);
        return plan;
    }
}