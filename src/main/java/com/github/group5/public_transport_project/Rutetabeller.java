package com.github.group5.public_transport_project;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import static no.hia.oblig4.Konfig.*;

public class RutetabellBygger {

    public List<Tur> ByggBussTur () {
        List<Tur> ByggBussTurHLDFRD = new ArrayList();
        int teller = 1;

        for (int hour = 8; hour <= 16; hour++){
            LocalTime start = LocalTime.of(hour, 0);
            LinkedHashMap<String, LocalTime> tider = new LinkedHashMap();

            for (int i = 0; i < UTGAENDE_TUR.length; i++){
                tider.put(UTGAENDE_TUR[i], start.pluseMinutes(UTGAENDE_MIN[i]));
            }
            ByggBussTurHLDFRD.add(new Tur("HLD-FRD-" + teller, "Halden --> Fredrikstad", start, tider));

        }

        return ByggBussTurHLDFRD;

    }

    public List<Tur> ByggBussTur () {
        List<Tur> ByggBussTurHLDFRD = new ArrayList();
        int teller = 1;¨
        for (int hour = 8; hour <= 16; hour++){
            LocalTime start = LocalTime.of(hour, 0);
            LinkedHashMap<String, LocalTime> tider = new LinkedHashMap();
            for (int i = 1; i < RETUR_TUR.length; i++){
                tider.put(UTGAENDE_TUR[i], start.pluseMinutes(UTGAENDE_MIN[i]));
            }

            ByggBussTurFRDHLD.add(new Tur("HLD-FRD-" + teller, "Halden --> Fredrikstad", start, tider));
        }

        return ByggBussTurFRDHLD
    }
 
}
