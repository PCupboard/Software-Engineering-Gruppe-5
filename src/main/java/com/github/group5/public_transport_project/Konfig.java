package no.hia.oblig4;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Konfig {
//Her lager vi Buss stopp og tider;

    private Konfig () {}

    public static final String[] UTGAENDE_TUR =  { "Halden", "Holebakken", "Veitesvingen", "Harrydalen",
            "Svensketoppen", "Halvardstoppen", "Fredrikstad"};

    public static final int[] UTGAENDE_MIN = {0,10, 15, 20, 40, 50, 60};

    public static final String[] RETUR_TUR = {"Fredrikstad", "Halvardstoppen", "Svensketoppen",
            "Harrydalen", "Veitesvingen", "Holebakken", "Halden"};


    public static final int[] RETUR_MIN = {0, 10, 20, 40, 45, 50, 60};


}
