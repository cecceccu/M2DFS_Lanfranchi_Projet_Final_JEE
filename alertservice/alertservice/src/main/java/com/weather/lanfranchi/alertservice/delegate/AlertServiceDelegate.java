package com.weather.lanfranchi.alertservice.delegate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
public class AlertServiceDelegate {

    @Autowired
    RestTemplate restTemplate;

    private String alertsToJSON(String response, String city, int days)
    {
        JSONObject jsonResponse = null;


        try {

            //We check if response only contains empty array or JSON object
            if (response.length()<=2)
            {
                jsonResponse = new JSONObject();
                jsonResponse.put("Message", "No alerts were found for " + city + " for the next "
                        + days + " days");
            }


        }catch (JSONException err){
            System.out.println("There was an errror while retrieving data");
        }


        return jsonResponse!=null?jsonResponse.toString():response;
    }

    @HystrixCommand(fallbackMethod = "alertApiErrorFallback")
    public String oneDayAlerts(String cityname)
    {

        String response = restTemplate.exchange("http://weather-alerts/get1DayAlerts/{cityname}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {},cityname ).getBody();


        return alertsToJSON(response, cityname, 1);
    }

    @HystrixCommand(fallbackMethod = "alertApiErrorFallback")
    public String fiveDayAlerts(String cityname)
    {

        String response = restTemplate.exchange("http://weather-alerts/get5DayAlerts/{cityname}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {},cityname ).getBody();


        return alertsToJSON(response, cityname, 5);
    }

    private String alertApiErrorFallback(String cityname)
    {
        return "Circuit breaker open, displaying default weather alerts : {Alarms:[Rain, Sustained Wind]}";
    }



    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
