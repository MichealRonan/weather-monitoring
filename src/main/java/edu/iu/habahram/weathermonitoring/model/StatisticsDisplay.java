package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class StatisticsDisplay implements Observer, DisplayElement{
    private List<Float> temperatures = new LinkedList<>();
    private Subject weatherData;

    public StatisticsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public String display() {
        float min = Collections.min(temperatures);
        float max = Collections.max(temperatures);
        float sum = 0.0f;

        for (float temp : temperatures) {
            sum += temp;
        }
        float avg = sum/temperatures.size();

        String html = "";
        html += "<div style=\"background-image: " +
                "url(/images/sky.webp); " +
                "height: 400px; " +
                "width: 647.2px;" +
                "display:flex;flex-wrap:wrap;justify-content:center;align-content:center;" +
                "\">";
        html += "<section>";
        html += String.format("<label>Avg. Temp: %f</label><br />", avg);
        html += String.format("<label>Min. Temp: %f</label><br />", min);
        html += String.format("<label><Max. Temp>: %f</label>", max);
        html += "</section>";
        html += "</div>";
        return html;
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        temperatures.add(temperature);
        if ( temperatures.size() > 3){
            temperatures.remove(0);
        }
    }

    @Override
    public String name() {
        return "Statistics Display";
    }

    @Override
    public String id() {
        return "Statistics";
    }

    public void subscribe() {
        weatherData.registerObserver(this);
    }

    public void unsubscribe() {
        weatherData.removeObserver(this);
    }
}
