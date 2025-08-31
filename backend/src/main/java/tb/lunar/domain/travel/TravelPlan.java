package tb.lunar.domain.travel;

import java.util.List;
import java.util.Map;

public interface TravelPlan {
    String code();
    List<Leg> route();

    /**
     * We need to know number of hotel nights per location
     * ie "armstrong": { CYCLER: 13, SURFACE: 6, CYCLER: 13 }.
     */
    Map<Location, Integer> staysForPackage(String packageCode);
}