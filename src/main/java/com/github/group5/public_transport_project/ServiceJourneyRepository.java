package com.github.group5.public_transport_project;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public final class ServiceJourneyRepository {

    private static volatile boolean initialized = false;
    private static List<ServiceJourneyLoader.JourneyStop> rows = List.of();
    private static Map<String, List<ServiceJourneyLoader.JourneyStop>> byJourneyId = Map.of();
    private static Map<String, List<ServiceJourneyLoader.JourneyStop>> byQuayId = Map.of();

    private ServiceJourneyRepository() {}

    
    public static synchronized void init(Path jsonPath) throws IOException {
        if (initialized) return; 
        var loaded = ServiceJourneyLoader.load(jsonPath);

        Map<String, List<ServiceJourneyLoader.JourneyStop>> _byJourney =
                loaded.stream().collect(Collectors.groupingBy(js -> js.serviceJourneyId));
      
        _byJourney.values().forEach(list -> list.sort(Comparator.comparingInt(js -> js.stopSequence)));

        Map<String, List<ServiceJourneyLoader.JourneyStop>> _byQuay =
                loaded.stream().collect(Collectors.groupingBy(js -> js.quayId));

        rows = Collections.unmodifiableList(new ArrayList<>(loaded));
        byJourneyId = unmodifiableDeep(_byJourney);
        byQuayId    = unmodifiableDeep(_byQuay);

        initialized = true;
    }

    private static <K,V> Map<K, List<V>> unmodifiableDeep(Map<K, List<V>> src) {
        Map<K, List<V>> out = new HashMap<>();
        for (var e : src.entrySet()) {
            out.put(e.getKey(), Collections.unmodifiableList(new ArrayList<>(e.getValue())));
        }
        return Collections.unmodifiableMap(out);
    }

    public static List<ServiceJourneyLoader.JourneyStop> getAll() {
        ensureInit();
        return rows;
    }

    public static List<ServiceJourneyLoader.JourneyStop> getByJourneyId(String journeyId) {
        ensureInit();
        return byJourneyId.getOrDefault(journeyId, List.of());
    }


    public static List<ServiceJourneyLoader.JourneyStop> getByQuayId(String quayId) {
        ensureInit();
        return byQuayId.getOrDefault(quayId, List.of());
    }

    public static Map<String, List<ServiceJourneyLoader.JourneyStop>> getGroupedByJourney() {
        ensureInit();
        return byJourneyId;
    }

    private static void ensureInit() {
        if (!initialized) {
            throw new IllegalStateException("ServiceJourneyRepository er ikke initialisert. Kall init(path) først.");
        }
    }
}
