package com.weather.lanfranchi.marineservice.controller;

import com.weather.lanfranchi.marineservice.delegate.MarineServiceDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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


@RestController

@Api(value = "Gets marine weather based on coordinates")
public class MarineServiceController {

    @Autowired
    MarineServiceDelegate marineServiceDelegate;

    @ApiOperation(value = "Get marine weather for coordinates", response = Iterable.class, tags = "marineWeather")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    @RequestMapping(value = "/marineWeather/{lng}/{lat}", method = RequestMethod.GET)
    public String marineWeather(@PathVariable int lng, @PathVariable int lat)
    {

        return marineServiceDelegate.marineWeather(lng, lat);
    }

    //As explained in the delegate class, this endpoint is only here to make ribbon loadBalancing
    //easier, and I only let it here to make your end of the testing easier
    @RequestMapping(value = "marineWeatherPort", method = RequestMethod.GET)
    public String marineWeatherPort()
    {
        return marineServiceDelegate.marineWeatherPort();
    }

}
