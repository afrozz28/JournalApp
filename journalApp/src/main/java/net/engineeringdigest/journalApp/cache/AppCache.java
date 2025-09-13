package net.engineeringdigest.journalApp.cache;

import net.engineeringdigest.journalApp.api.response.weatherResponse;
import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class AppCache {

    public enum keys{
        weather_api;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> appCache;

    public Map<String, weatherResponse> inCache = new HashMap<>();

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : all){
            appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }

    public void setWeatherResponse(String city, weatherResponse response){
        inCache.put(city, response);
    }

    public weatherResponse getWeatherResponse(String city){
        return inCache.get(city);
    }
}
