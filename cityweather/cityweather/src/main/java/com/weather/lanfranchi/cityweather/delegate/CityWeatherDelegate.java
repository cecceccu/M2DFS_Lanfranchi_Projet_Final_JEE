package com.weather.lanfranchi.cityweather.delegate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class CityWeatherDelegate {
    private String apiKey = "90s9YJcEqa7CAIm8N4vBdQOm6xuK0Ry7";
    @Autowired
    RestTemplate restTemplate;

    public int getCityCode(String cityname)
    {

        JSONObject jsonResponse;

        int cityCode = -1;
        String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey="+ this.apiKey +"&q={cityname}&language=fr-fr&details=false",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, cityname).getBody();

        // Remove leading and trailing [] to get a proper JSON
        response = response.substring(1, response.length() -1);

        try {
            jsonResponse = new JSONObject(response);
            cityCode = jsonResponse.getInt("Key");
        }catch (JSONException err){
            System.out.println("There was an errror while retrieving data");
        }

        System.out.println(cityCode);
        return cityCode;
    }

    @HystrixCommand(fallbackMethod = "getCurrentWeatherByCodeErrorFallback")
    public String getWeatherByCode(int code)
    {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/{code}?apikey="+ this.apiKey +"&language=fr-fr&details=false",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, code).getBody();
        return response;
    }

    @HystrixCommand(fallbackMethod = "getCurrentWeatherByNameErrorFallback")
    public String getWeatherByName(String cityname)
    {
        int code = this.getCityCode(cityname);

        String response = restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/{code}?apikey=" + this.apiKey + "&language=fr-fr&details=false",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, code).getBody();
        return response;
    }

    @HystrixCommand(fallbackMethod = "getDailyForecastsErrorFallback")
    public String get1DayDailyForecasts(String cityname)
    {
        int code = this.getCityCode(cityname);
        String response = restTemplate.exchange("http://dataservice.accuweather.com/forecasts/v1/daily/1day/{code}?apikey="+ this.apiKey +"&language=en-us&details=false",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, code).getBody();
        return response;

    }

    @HystrixCommand(fallbackMethod = "getDailyForecastsErrorFallback")
    public String get5DayDailyForecasts(String cityname)
    {
        int code = this.getCityCode(cityname);
        String response = restTemplate.exchange("http://dataservice.accuweather.com/forecasts/v1/daily/5day/{code}?apikey="+ this.apiKey +"&language=en-us&details=false",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, code).getBody();
        return response;

    }

    private String getCurrentWeatherByCodeErrorFallback(int code)
    {
        return "Circuit breaker open, displaying default current weather : {" +
                "date : 2021-11-19, weather : cloudy, precipitations : false, temperature : 11°C}";
    }

    private String getCurrentWeatherByNameErrorFallback(String cityname)
    {
        return "Circuit breaker open, displaying default current weather : {" +
                "date : 2021-11-19, weather : cloudy, precipitations : false, temperature : 11°C}";
    }

    private String getDailyForecastsErrorFallback(String cityname)
    {
        return "Circuit breaker open, displaying default weather forecast : {" +
                "2021-11-21: {Maximal temperature : 17.5, Minimal temperature : 10," +
                " Weather forecast for the day : Sunny, Weather forecast for the night :" +
                " Mostly cloudy}}";
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }




}
