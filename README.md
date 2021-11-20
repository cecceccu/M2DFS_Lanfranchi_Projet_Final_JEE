# M2DFS_Lanfranchi_Projet_Final_JEE
List of endpoints:

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





