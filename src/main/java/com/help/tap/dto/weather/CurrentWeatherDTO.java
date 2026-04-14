package com.help.tap.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentWeatherDTO {
    private Double temperature;
    private Double windspeed;
    private Integer winddirection;
    private Integer weathercode;
    
    @JsonProperty("is_day")
    private Integer isDay;
    
    private String time;
}
