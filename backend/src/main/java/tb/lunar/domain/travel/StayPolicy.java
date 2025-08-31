package tb.lunar.domain.travel;

import java.util.Map;

/**
 * ie "armstrong": { CYCLER: 13, SURFACE: 6 }.
 */
public interface StayPolicy {
    Map<Location, Integer> staysForPackage(String packageCode);
}