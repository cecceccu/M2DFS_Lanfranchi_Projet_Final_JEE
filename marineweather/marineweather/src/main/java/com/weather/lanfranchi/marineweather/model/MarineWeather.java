package com.weather.lanfranchi.marineweather.model;

public class MarineWeather {
    private int windSpeed;
    private int swellHeight;
    private int waveHeight;

    public MarineWeather(int windSpeed, int swellHeight, int waveHeight) {
        this.windSpeed = windSpeed;
        this.swellHeight = swellHeight;
        this.waveHeight = waveHeight;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getSwellHeight() {
        return swellHeight;
    }

    public void setSwellHeight(int swellHeight) {
        this.swellHeight = swellHeight;
    }

    public int getWaveHeight() {
        return waveHeight;
    }

    public void setWaveHeight(int waveHeight) {
        this.waveHeight = waveHeight;
    }

    @Override
    public String toString() {
        return "MarineWeather{" +
                "windSpeed=" + windSpeed +
                ", swellHeight=" + swellHeight +
                ", waveHeight=" + waveHeight +
                '}';
    }
}
