package com.github.group5.public_transport_project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class ServiceJourneyLoader {
   
    public static final class JourneyStop {
        public final String serviceJourneyId;
        public final String quayId;
        public final LocalTime arrivalTime;    
        public final LocalTime departureTime;
        public final int stopSequence;
        public final String extra1;          
        public final String extra2;            
        public final String extra3;            
        public final double distanceMeters;

        public JourneyStop(String serviceJourneyId, String quayId,
                           LocalTime arrivalTime, LocalTime departureTime,
                           int stopSequence, String extra1, String extra2, String extra3,
                           double distanceMeters) {
            this.serviceJourneyId = serviceJourneyId;
            this.quayId = quayId;
            this.arrivalTime = arrivalTime;
            this.departureTime = departureTime;
            this.stopSequence = stopSequence;
            this.extra1 = extra1;
            this.extra2 = extra2;
            this.extra3 = extra3;
            this.distanceMeters = distanceMeters;
        }

        @Override public String toString() {
            return serviceJourneyId + " | " + quayId + " | " +
                    arrivalTime + " → " + departureTime +
                    " | seq=" + stopSequence + " | d=" + distanceMeters + "m";
        }
    }


    public static ArrayList<JourneyStop> load(Path jsonFile) throws IOException {
        String json = Files.readString(jsonFile, StandardCharsets.UTF_8);
        ArrayList<JourneyStop> rows = new ArrayList<>();

        
        Pattern objectPattern = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
        Matcher objMatcher = objectPattern.matcher(json);


        Pattern sjIdPat   = Pattern.compile("\"service_journey_id\"\\s*:\\s*\"([^\"]+)\"");
        Pattern quayIdPat = Pattern.compile("\"quay_id\"\\s*:\\s*\"([^\"]+)\"");
        Pattern arrPat    = Pattern.compile("\"arrival_time\"\\s*:\\s*\"(\\d{2}:\\d{2}:\\d{2})\"");
        Pattern depPat    = Pattern.compile("\"departure_time\"\\s*:\\s*\"(\\d{2}:\\d{2}:\\d{2})\"");
        Pattern seqPat    = Pattern.compile("\"stop_sequence\"\\s*:\\s*(\\d+)");
        Pattern ex1Pat    = Pattern.compile("\"extra1\"\\s*:\\s*(null|\"((?:\\\\.|[^\"\\\\])*)\")");
        Pattern ex2Pat    = Pattern.compile("\"extra2\"\\s*:\\s*(null|\"((?:\\\\.|[^\"\\\\])*)\")");
        Pattern ex3Pat    = Pattern.compile("\"extra3\"\\s*:\\s*(null|\"((?:\\\\.|[^\"\\\\])*)\")");
        Pattern distPat   = Pattern.compile("\"distance_meters\"\\s*:\\s*(-?\\d+(?:\\.\\d+)?)");

        while (objMatcher.find()) {
            String obj = objMatcher.group(1);

            String sjId = findString(sjIdPat, obj);
            String qId  = findString(quayIdPat, obj);
            LocalTime arr = parseTime(findString(arrPat, obj));
            LocalTime dep = parseTime(findString(depPat, obj));
            Integer seq   = findInt(seqPat, obj);
            String extra1 = findNullableString(ex1Pat, obj);
            String extra2 = findNullableString(ex2Pat, obj);
            String extra3 = findNullableString(ex3Pat, obj);
            Double dist   = findDouble(distPat, obj);

            if (sjId != null && qId != null && arr != null && dep != null
                    && seq != null && dist != null) {
                rows.add(new JourneyStop(
                        sjId, qId, arr, dep, seq, extra1, extra2, extra3, dist
                ));
            }
        }
        return rows;
    }

    
    public static Map<String, List<JourneyStop>> groupByJourney(ArrayList<JourneyStop> rows) {
        Map<String, List<JourneyStop>> map = new HashMap<>();
        for (JourneyStop r : rows) {
            map.computeIfAbsent(r.serviceJourneyId, k -> new ArrayList<>()).add(r);
        }
        
        for (List<JourneyStop> list : map.values()) {
            list.sort(Comparator.comparingInt(js -> js.stopSequence));
        }
        return map;
    }


    private static String findString(Pattern p, String text) {
        Matcher m = p.matcher(text);
        return m.find() ? m.group(1) : null;
    }
    private static Integer findInt(Pattern p, String text) {
        Matcher m = p.matcher(text);
        return m.find() ? Integer.valueOf(m.group(1)) : null;
    }
    private static Double findDouble(Pattern p, String text) {
        Matcher m = p.matcher(text);
        return m.find() ? Double.valueOf(m.group(1)) : null;
    }
    private static LocalTime parseTime(String hhmmss) {
        return (hhmmss == null) ? null : LocalTime.parse(hhmmss);
    }

    private static String findNullableString(Pattern p, String text) {
        Matcher m = p.matcher(text);
        if (!m.find()) return null;
        String raw = m.group(1); 
        if ("null".equals(raw)) return null;
        String inner = m.group(2); 
        return unescapeJsonString(inner);
    }
    private static String unescapeJsonString(String s) {
        if (s == null) return null;
        return s.replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\b", "\b")
                .replace("\\f", "\f");
    }

    private ServiceJourneyLoader() {}
}
