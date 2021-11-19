package com.weather.lanfranchi.marineweather.model;

public class Coordinates {
    private int longitude;
    private int latitude;

    public Coordinates(int longitude, int latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    @Override
    public boolean equals(Object obj)
    {
        return this.latitude == ((Coordinates) obj).latitude && this.longitude == ((Coordinates) obj).longitude;
    }
}
