package com.github.group5.public_transport_project;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public final class TimetableService {

    public static final class TimetableRow {
        public final String serviceJourneyId;
        public final int stopSequence;
        public final String quayId;
        public final String stopName;     
        public final LocalTime arrival;
        public final LocalTime departure;
        public final Double lat;          
        public final Double lon;         

        public TimetableRow(String serviceJourneyId, int stopSequence, String quayId,
                            String stopName, LocalTime arrival, LocalTime departure,
                            Double lat, Double lon) {
            this.serviceJourneyId = serviceJourneyId;
            this.stopSequence = stopSequence;
            this.quayId = quayId;
            this.stopName = stopName;
            this.arrival = arrival;
            this.departure = departure;
            this.lat = lat;
            this.lon = lon;
        }

        @Override public String toString() {
            return String.format("#%02d  %-30s  %s → %s",
                    stopSequence, stopName, arrival, departure);
        }
    }

    
    public static List<TimetableRow> getTimetableForJourney(String journeyId) {
        
        Map<String, QuayLoader.Quay> quayById = QuayRepository.getQuays()
                .stream().collect(Collectors.toMap(q -> q.id, q -> q));

        var rows = ServiceJourneyRepository.getByJourneyId(journeyId);
        List<TimetableRow> out = new ArrayList<>(rows.size());

        for (var r : rows) {
            QuayLoader.Quay q = quayById.get(r.quayId);
            String name = (q != null ? q.navn : "(ukjent stopp: " + r.quayId + ")");
            Double lat = (q != null ? q.lat : null);
            Double lon = (q != null ? q.lon : null);
            out.add(new TimetableRow(
                    r.serviceJourneyId, r.stopSequence, r.quayId,
                    name, r.arrivalTime, r.departureTime, lat, lon
            ));
        }

    
        out.sort(Comparator.comparingInt(t -> t.stopSequence));
        return out;
    }

    
    public static void printTimetable(String journeyId) {
        var tt = getTimetableForJourney(journeyId);
        if (tt.isEmpty()) {
            System.out.println("Fant ingen rader for journeyId=" + journeyId);
            return;
        }
        System.out.println("Reise: " + journeyId + " (" + tt.size() + " stopp)");
        for (var row : tt) {
            System.out.println(row.toString());
        }
    }

    private TimetableService() {}
}
