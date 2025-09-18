package net.springProject.journalApp.service;

import net.springProject.journalApp.api.response.weatherResponse;
import net.springProject.journalApp.cache.AppCache;
import net.springProject.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class weatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public weatherResponse getWeather(String city){
        weatherResponse weatherResponse = appCache.getWeatherResponse(city);
        if (weatherResponse != null){
            return weatherResponse;
        }else{
            String finalAPI = appCache.appCache.get(AppCache.keys.weather_api.toString()).replace(Placeholders.CITY,city).replace(Placeholders.API_KEY,apiKey);
            ResponseEntity<weatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, weatherResponse.class);
            weatherResponse body = response.getBody();
            appCache.setWeatherResponse(city,body);
            return body;
        }
    }
}
