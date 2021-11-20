package com.weather.lanfranchi.alertservice.controller;

import com.weather.lanfranchi.alertservice.delegate.AlertServiceDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@Api(value = "Get alerts based on city name")
@RestController
public class AlertServiceController {

    @Autowired
    AlertServiceDelegate alertServiceDelegate;

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




    @ApiOperation(value = "Get 1 day alerts for city", response = Iterable.class, tags = "alerts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    @RequestMapping(value = "/1DayAlerts/{cityname}", method = RequestMethod.GET)
    public String oneDayAlerts(@PathVariable String cityname)
    {
        return alertServiceDelegate.oneDayAlerts(cityname);
    }

    @ApiOperation(value = "Get 5 day alerts for city", response = Iterable.class, tags = "alerts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    @RequestMapping(value = "/5DayAlerts/{cityname}", method = RequestMethod.GET)
    public String fiveDayAlerts(@PathVariable String cityname)
    {

        return alertServiceDelegate.fiveDayAlerts(cityname);
    }





}
