package com.help.tap.service;

import com.help.tap.dto.weather.OpenMeteoResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService() {
        this.restTemplate = new RestTemplate();
    }

    public OpenMeteoResponseDTO getCurrentWeather(Double latitude, Double longitude) {
        String url = String.format(Locale.US, "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current_weather=true", 
                latitude, 
                longitude);
        
        return restTemplate.getForObject(url, OpenMeteoResponseDTO.class);
    }
}
