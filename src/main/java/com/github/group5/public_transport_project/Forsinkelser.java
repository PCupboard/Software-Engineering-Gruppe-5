package no.hia.oblig4;
import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;
public final class Forsinkelser {
    private Forsinkelser () {}
    public static Tur forsinkHele(Tur tur, int minutter) {
        LocalTime nyStart = tur.getStartID().plusMinutes(minutter);
        LinkedHashMap<String, LocalTime> nyeTider = new LinkedHashMap<>();
        for (Entry<String, LocalTime> e : tur.getTider().entrySet()) {
            nyeTider.put(e.getKey(), e.getValue().plusMinutes(minutter));
        }
        return new Tur(tur.getNokkel(), tur.getRetning(), nyStart, nyeTider);
    }
    public static Tur forsinkFraStopp(Tur tur, String stopp, int minutter) {
        boolean forsinkVidere = false;
        LinkedHashMap<String, LocalTime> nyeTider = new LinkedHashMap<>();

        for (Map.Entry<String, LocalTime> e : tur.getTider().entrySet()) {
            if (!forsinkVidere && e.getKey().equalsIgnoreCase(stopp)) {
                forsinkVidere = true; // fra og med dette stoppet
            }
            LocalTime ny = forsinkVidere ? e.getValue().plusMinutes(minutter) : e.getValue();
            nyeTider.put(e.getKey(), ny);
        }

        // Starttid endres kun hvis første stopp var valgt
        String forsteStopp = tur.getTider().keySet().iterator().next();
        LocalTime nyStart = forsteStopp.equalsIgnoreCase(stopp)
                ? tur.getStartID().plusMinutes(minutter)
                : tur.getStartID();

        return new Tur(tur.getNokkel(), tur.getRetning(), nyStart, nyeTider);
    }
}
