package com.help.tap.controller;

import com.help.tap.dto.weather.OpenMeteoResponseDTO;
import com.help.tap.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<OpenMeteoResponseDTO> getWeather(
            @RequestParam Double latitude, 
            @RequestParam Double longitude) {
        
        OpenMeteoResponseDTO response = weatherService.getCurrentWeather(latitude, longitude);
        return ResponseEntity.ok(response);
    }
}
