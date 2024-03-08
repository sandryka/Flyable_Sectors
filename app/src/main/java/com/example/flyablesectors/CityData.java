package com.example.flyablesectors;

import java.util.HashMap;
import java.util.Map;

public class CityData {
    private static final Map<String, String> cityInfo = new HashMap<>();

    static {
        // Populate city data. Each city's information includes square mileage, category, and flyable sectors.
        // For example:
        cityInfo.put("LOS ANGELES, CA", "Square Mileage: 468.7, Category: Primary Candidate for UAM (>200), Flyable Sectors: 31.2");
        // Repeat for other cities...
    }

    public static String getCityInfo(String cityName) {
        return cityInfo.getOrDefault(cityName, "City data not found.");
    }
}
