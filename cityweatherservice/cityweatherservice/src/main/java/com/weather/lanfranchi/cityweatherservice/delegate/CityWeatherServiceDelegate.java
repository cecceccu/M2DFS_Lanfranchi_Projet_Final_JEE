package com.weather.lanfranchi.cityweatherservice.delegate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class CityWeatherServiceDelegate {


    @Autowired
    RestTemplate restTemplate;

    public JSONObject currentWeatherResponseToJSON(String response)
    {
        JSONObject jsonResponse = new JSONObject();

        String dateTime;
        String weatherText;
        Boolean hasPrecipitation;
        int temperature;

        // Remove leading and trailing [] to get a proper JSON
        response = response.substring(1, response.length() -1);

        try {
            JSONObject json = new JSONObject(response);
            dateTime = json.getString("LocalObservationDateTime");
            weatherText = json.getString("WeatherText");
            hasPrecipitation = json.getBoolean("HasPrecipitation");
            temperature = json.getJSONObject("Temperature").getJSONObject("Metric").getInt("Value");
            jsonResponse.put("date", dateTime);
            jsonResponse.put("weather", weatherText);
            jsonResponse.put("precipitations", hasPrecipitation);
            jsonResponse.put("temperature", temperature + "°C");

        }catch (JSONException err){
            System.out.println("There was an errror while retrieving data");
        }

        return jsonResponse;
    }

    public JSONObject forecastsToJSON(String response, int days)
    {
        JSONObject jsonResponse = new JSONObject();

        String dateTime;
        double minTemp;
        double maxTemp;
        String dayWeather;
        String nightWeather;

        try {
            JSONObject json = new JSONObject(response);
            JSONArray array  = json.getJSONArray("DailyForecasts");
            System.out.println("a");

            for (int i=0; i<days; i++)
            {
                json = array.getJSONObject(i);
                System.out.println(json.toString());
                dateTime = json.getString("Date");

                //-32 /1.8 to convert fahrenheit to celsius (values were already in celsius for current weather)
                minTemp = (json.getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value")-32)/1.8;
                maxTemp = (json.getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value")-32)/1.8;

                //Floor to 2 decimals to make json less ugly
                minTemp = Math.floor(minTemp * 100)/100;
                maxTemp = Math.floor(maxTemp * 100)/100;

                dayWeather = json.getJSONObject("Day").getString("IconPhrase");
                nightWeather = json.getJSONObject("Night").getString("IconPhrase");

                JSONObject weatherDataForDay = new JSONObject();
                weatherDataForDay.put("Minimal temperature", minTemp);
                weatherDataForDay.put("Maximal temperature", maxTemp);
                weatherDataForDay.put("Weather forecast for the day", dayWeather);
                weatherDataForDay.put("Weather forecast for the night", nightWeather);

                jsonResponse.put(dateTime, weatherDataForDay);
            }



        }catch (JSONException err){
            System.out.println("There was an errror while retrieving data");
        }


        return jsonResponse;
    }

    @HystrixCommand(fallbackMethod = "currentWeatherApiErrorFallback")
    public String currentWeather(String cityname)
    {

        String response = restTemplate.exchange("http://current-forecasts/getCurrentWeatherByName/{cityname}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {},cityname ).getBody();


        return currentWeatherResponseToJSON(response).toString();
    }

    @HystrixCommand(fallbackMethod = "currentWeatherApiErrorFallback")
    public String currentWeatherByCode(int citycode)
    {

        String response = restTemplate.exchange("http://current-forecasts/getCurrentWeatherByCode/{citycode}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {},citycode ).getBody();


        return currentWeatherResponseToJSON(response).toString();
    }

    @HystrixCommand(fallbackMethod = "forecastApiErrorFallback")
    public String oneDayForecasts(String cityname)
    {

        String response = restTemplate.exchange("http://current-forecasts/get1DayDailyForecasts/{cityname}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {},cityname ).getBody();


        return forecastsToJSON(response, 1).toString();
    }

    @HystrixCommand(fallbackMethod = "forecastApiErrorFallback")
    public String fiveDayForecasts(String cityname)
    {

        String response = restTemplate.exchange("http://current-forecasts/get5DayDailyForecasts/{cityname}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {},cityname ).getBody();


        return forecastsToJSON(response, 5).toString();
    }

    private String currentWeatherApiErrorFallback(String cityname)
    {
        return "Circuit breaker open, displaying default current weather : {" +
                "date : 2021-11-19, weather : cloudy, precipitations : false, temperature : 11°C}";
    }

    private String currentWeatherApiErrorFallback(int citycode)
    {
        return "Circuit breaker open, displaying default current weather : {" +
                "date : 2021-11-19, weather : cloudy, precipitations : false, temperature : 11°C}";
    }

    private String forecastApiErrorFallback(String cityname)
    {
        return "Circuit breaker open, displaying default weather forecast : {" +
                "2021-11-21: {Maximal temperature : 17.5, Minimal temperature : 10," +
                " Weather forecast for the day : Sunny, Weather forecast for the night :" +
                " Mostly cloudy}}";
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
