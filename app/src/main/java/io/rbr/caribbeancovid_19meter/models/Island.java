package io.rbr.caribbeancovid_19meter.models;

import org.json.JSONObject;

public class Island {
    public String name;
    public String geoId;
    public long population;
    public int totalCases;
    public int totalDeaths;
    public int todayCases;
    public int todayDeaths;

    public Island(String name, int population) {
        this.name = name;
        this.population = population;
    }
}
