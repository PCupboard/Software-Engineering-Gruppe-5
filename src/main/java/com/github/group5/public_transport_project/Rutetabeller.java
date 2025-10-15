package com.github.group5.public_transport_project;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class RutetabellBygger {

    public List<Tur> ByggBussTurer () {
        List<Tur> ByggBussTurHLDFRD = new ArrayList<>();
        int teller = 1;

        for (int hour = 8; hour <= 23; hour++){
        LocalTime start = LocalTime.of(hour, 0);
        LinkedHashMap<String, LocalTime> tider = new LinkedHashMap<>();
            for(int i = 1; i < UTGAENDE_TURER.length; i++){
                tider.put(UTGAENDE_TURER[i],start.plusMinutes(UTGAENDE_MIN[i]));

            }
            ByggBussTurHLDFRD.add(new Tur("HLD-FRD-" + teller, "Halden --> Fredrikstad", start, tider));
            teller ++;
        }
        return ByggBussTurHLDFRD;
    }
    public List<Tur> ByggBussRETUR () {
        List<Tur> ByggBussTurFRDHLD = new ArrayList<>();
        int teller = 1;

        for (int hour = 8; hour <= 23; hour++){
            LocalTime start = LocalTime.of(hour, 30);
            LinkedHashMap<String, LocalTime> tider = new LinkedHashMap<>();
            for (int i = 1; i < RETUR_TURER.length; i ++){
                tider.put(UTGAENDE_TURER[i],start.plusMinutes(RETUR_MIN[i]));

            }
            ByggBussTurFRDHLD.add(new Tur("FRD-HLD-" + teller, "Fredrikstad --> Halden", start, tider ));
            teller++;
        }
        return ByggBussTurFRDHLD;
    }

}
