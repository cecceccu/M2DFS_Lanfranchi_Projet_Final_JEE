# M2DFS_Lanfranchi_Projet_Final_JEE

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Almost all the features have been implemented : 

3 rest controller services that fetch data from the API (current weather and forecasts in a city, weather alarms in a city, and marine weather at given coordinates (this controller uses a cutsom database since the API was not working properly)).

3 client services have been implemented to properly format the data returned by each controller.

All the services are monitored on a eureka server, can register to this server, and all the services can be discovered by any other service.

Circuit breakers have been implemented on every client and on every controller except the one that doesn't use the API (MarineWeather, but MarineService does have a circuit breaker)

Ribbon loadBalancing has been implemented as well between the clients and the rest controllers.

The only missing features are the CRUD operations, as I could not really conceptualize how to implement them on an application that is only able to fetch data.

Finally, due to many dependencies and runtime errors that I was not able to fix after multiple hours, I was unable to run any test suite, so I couldn't properly test the application.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

List of endpoints:

Eureka Dashboard : http://localhost:8761/

Backend endpoints (should not be called directly in normal use cases):
  - Weather alert forecasts for hikers:
      - curl -X GET --header 'Accept: text/plain' 'http://localhost:8083/get1DayAlerts/{cityname}'
      - curl -X GET --header 'Accept: text/plain' 'http://localhost:8083/get5DayAlerts/{cityname}'
  - Current weather and forecasts for given city:
    - curl -X GET --header 'Accept: application/json' 'http://localhost:8082/getCurrentWeatherByCode/{code}'
    - curl -X GET --header 'Accept: text/plain' 'http://localhost:8082/getCurrentWeatherByName/{cityname}'
    - curl -X GET --header 'Accept: text/plain' 'http://localhost:8082/get1DayDailyForecasts/{cityname}'
    - curl -X GET --header 'Accept: text/plain' 'http://localhost:8082/get5DayDailyForecasts/{cityname}'
  - Marine weather
   - curl -X GET --header 'Accept: application/json' 'http://localhost:8084/getMarineWeather/{lng}/{lat}'

Client endpoints (which should normally be used):
  - Weather alert forecasts for hikers:
      - curl -X GET --header 'Accept: text/plain' 'http://localhost:8281/1DayAlerts/{cityname}'
      - curl -X GET --header 'Accept: text/plain' 'http://localhost:8281/5DayAlerts/{cityname}'
  - Current weather and forecasts for given city:
    - curl -X GET --header 'Accept: text/plain' 'http://localhost:8280/currentWeatherByCode/{citycode}'
    - curl -X GET --header 'Accept: text/plain' 'http://localhost:8280/currentWeather/{cityname}'
    - curl -X GET --header 'Accept: text/plain' 'http://localhost:8280/1DayForecasts/{cityname}'
    - curl -X GET --header 'Accept: text/plain' 'http://localhost:8280/5DayForecasts/{cityname}'
  - Marine weather:
   - curl -X GET --header 'Accept: application/json' 'http://localhost:8282/marineWeather/{lng}/{lat}'
   - http://localhost:8282/marineWeatherPort : this endpoint just returns the port of the server that handled the request, it is only here to help testing ribbon loadbalancing


The swagger documentation for each API is available at http://localhost:port/swagger-ui.html





