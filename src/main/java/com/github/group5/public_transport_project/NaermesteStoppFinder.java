package com.github.group5.public_transport_project;

import java.util.ArrayList;
import java.util.List;


public final class NaermesteStoppFinder {
    public static final double brukerLat = 59.13057050830814;
    public static final double brukerLon = 11.354547634380776;

    public static final class Result {
        public final QuayLoader.Quay quay;
        public final double meters;
        public Result(QuayLoader.Quay quay, double meters) { this.quay = quay; this.meters = meters; }
    }

    public static Result findNearest(List<QuayLoader.Quay> quays) {
        return findNearestTo(brukerLat, brukerLon, quays);
    }

    public static Result findNearestTo(double lat, double lon, List<QuayLoader.Quay> quays) {
        if (quays == null || quays.isEmpty()) return null;
        QuayLoader.Quay best = null;
        double bestDist = Double.POSITIVE_INFINITY;
        for (QuayLoader.Quay q : quays) {
            double d = haversineMeters(lat, lon, q.lat, q.lon);
            if (d < bestDist) { bestDist = d; best = q; }
        }
        return new Result(best, bestDist);
    }

    private static double haversineMeters(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371000.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2)*Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2)*Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
}
