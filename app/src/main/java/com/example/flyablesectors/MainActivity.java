package com.example.flyablesectors;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner citySpinner;
    Button computeButton;
    TextView resultTextView;
    Map<String, CityInfo> citiesInfo = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        citySpinner = findViewById(R.id.citySpinner);
        computeButton = findViewById(R.id.computeButton);
        resultTextView = findViewById(R.id.resultTextView);

        initializeCitiesInfo();

        List<String> cityList = new ArrayList<>(citiesInfo.keySet());
        cityList.add(0, "Select your city"); // Correctly adding hint at the beginning

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cityList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(getResources().getColor(R.color.white)); // Set text color to white
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(getResources().getColor(R.color.white)); // Set text color to white
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        // Set button background color programmatically with radius
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(getResources().getDimensionPixelSize(R.dimen.button_radius));
        drawable.setColor(getResources().getColor(R.color.dark_compute_button_background));
        computeButton.setBackground(drawable);

        computeButton.setOnClickListener(v -> {
            int selectedPosition = citySpinner.getSelectedItemPosition();
            if (selectedPosition > 0) {
                String selectedCity = cityList.get(selectedPosition);
                CityInfo info = citiesInfo.get(selectedCity);
                String category = categorizeCity(info.squareMileage);
                double flyableSectors = calculateFlyableSectors(info.squareMileage);

                // Calculate the total square miles
                double totalSquareMiles = 0;
                for (String city : cityList.subList(1, cityList.size())) {
                    totalSquareMiles += citiesInfo.get(city).squareMileage;
                }

                resultTextView.setText(String.format("City: %s\nTotal Square Miles: %.1f\nCategory: %s\nFlyable Sectors: %.1f",
                        selectedCity, totalSquareMiles, category, flyableSectors));
            } else {
                resultTextView.setText("Please select a city.");
            }
        });
    }

    private void initializeCitiesInfo() {
        // Initialize all cities with their square mileage
        citiesInfo.put("Los Angeles, CA", new CityInfo(468.7));
        citiesInfo.put("Chicago, IL", new CityInfo(227.3));
        citiesInfo.put("Houston, TX", new CityInfo(637.5));
        citiesInfo.put("Phoenix, AZ", new CityInfo(517.6));
        citiesInfo.put("San Antonio, TX", new CityInfo(461.0));
        citiesInfo.put("Jacksonville, FL", new CityInfo(747.4));
        citiesInfo.put("Indianapolis, IN", new CityInfo(368.2));
        citiesInfo.put("Philadelphia, PA", new CityInfo(134.1));
        citiesInfo.put("San Diego, CA", new CityInfo(325.2));
        citiesInfo.put("Dallas, TX", new CityInfo(340.9));
        citiesInfo.put("San Jose, CA", new CityInfo(177.5));
        citiesInfo.put("Austin, TX", new CityInfo(312.7));
        citiesInfo.put("Fort Worth, TX", new CityInfo(345.1));
        citiesInfo.put("Columbus, OH", new CityInfo(223.1));
        citiesInfo.put("Charlotte, NC", new CityInfo(305.4));
        citiesInfo.put("Seattle, WA", new CityInfo(142.5));
        citiesInfo.put("Denver, CO", new CityInfo(155.0));
        citiesInfo.put("San Francisco, CA", new CityInfo(46.9));
        citiesInfo.put("Washington, D.C.", new CityInfo(68.3));
        // Add any other cities here
    }

    private String categorizeCity(double squareMileage) {
        if (squareMileage < 100) {
            return "Primary Candidates for RAM";
        } else if (squareMileage >= 100 && squareMileage <= 200) {
            return "Prime Candidates for UAM";
        } else {
            return "Primary Candidates for UAM";
        }
    }

    private double calculateFlyableSectors(double squareMileage) {
        // Assuming the formula for calculating flyable sectors is given,
        // for example, city's square mileage divided by 3 then multiplied by 20% (+/- rate)
        return (squareMileage / 3) * 0.2;
    }

    static class CityInfo {
        double squareMileage;

        CityInfo(double squareMileage) {
            this.squareMileage = squareMileage;
        }
    }
}
