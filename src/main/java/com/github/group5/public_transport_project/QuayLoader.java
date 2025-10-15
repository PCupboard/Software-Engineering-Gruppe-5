package com.github.group5.public_transport_project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuayLoader {
   public static class Quay {
        public final String id;
        public final String navn;
        public final double lat;
        public final double lon;

        public Quay(String id, String navn, double lat, double lon) {
            this.id = id;
            this.navn = navn;
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        public String toString() {
            return id + " | " + navn + " | " + lat + ", " + lon;
        }
    }


    public static ArrayList<Quay> load(Path jsonFile) throws IOException {
        String json = Files.readString(jsonFile, StandardCharsets.UTF_8);

        ArrayList<Quay> result = new ArrayList<>();

        Pattern objectPattern = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
        Matcher objMatcher = objectPattern.matcher(json);

        Pattern idPattern   = Pattern.compile("\"id\"\\s*:\\s*\"([^\"]+)\"");
        Pattern navnPattern = Pattern.compile("\"navn\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");
        Pattern latPattern  = Pattern.compile("\"lat\"\\s*:\\s*(-?\\d+(?:\\.\\d+)?)");
        Pattern lonPattern  = Pattern.compile("\"lon\"\\s*:\\s*(-?\\d+(?:\\.\\d+)?)");

        while (objMatcher.find()) {
            String obj = objMatcher.group(1);

            String id   = findString(idPattern, obj);
            String navn = unescapeJsonString(findString(navnPattern, obj));
            Double lat  = findDouble(latPattern, obj);
            Double lon  = findDouble(lonPattern, obj);

            // Krev at alle felter finnes før vi legger til
            if (id != null && navn != null && lat != null && lon != null) {
                result.add(new Quay(id, navn, lat, lon));
            }
        }

        return result;
    }

    private static String findString(Pattern p, String text) {
        Matcher m = p.matcher(text);
        return m.find() ? m.group(1) : null;
    }

    private static Double findDouble(Pattern p, String text) {
        Matcher m = p.matcher(text);
        return m.find() ? Double.valueOf(m.group(1)) : null;
    }

    // Liten av-escaping for vanlige JSON-sekvenser i navn
    private static String unescapeJsonString(String s) {
        if (s == null) return null;
        // erstatt \" -> ", \\ -> \, \n -> newline, \t -> tab osv.
        String out = s.replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\b", "\b")
                .replace("\\f", "\f");
        return out;
    }

}
