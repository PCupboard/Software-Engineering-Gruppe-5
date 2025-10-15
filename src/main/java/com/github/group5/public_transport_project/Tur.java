package com.github.group5.public_transport_project;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;


public class Tur {

    private String nokkel;
    private String retning;
    private LocalTime startID;
    private LinkedHashMap<String, LocalTime> tider;

    public Tur(String nokkel, String retning, LocalTime startID, LinkedHashMap<String, LocalTime> tider){
        this.nokkel = nokkel;
        this.retning = retning;
        this.startID = startID;
        this.tider = tider;
    }

    public String getNokkel() {
        return nokkel;
    }

    public String getRetning() {
        return retning;
    }

    public LocalTime getStartID() {
        return startID;
    }

    public LinkedHashMap<String, LocalTime> getTider() {
        return tider;
    }
}




