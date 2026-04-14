package com.help.tap.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpenMeteoResponseDTO {
    private Double latitude;
    private Double longitude;
    
    @JsonProperty("current_weather")
    private CurrentWeatherDTO currentWeather;
}
