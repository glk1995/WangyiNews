package com.example.glk.wangyinews.beans;

import java.io.Serializable;

/**
 * Created by zgqdg on 2016/11/13.
 */

public class WeatherBean implements Serializable {

    private static final long serialVersionUID = 11;

    //temperature
    private String temperature;

    //weather
    private String weather;

    //wind
    private String wind;

    //week
    private String week;

    //data
    private String data;

    //image
    private int imageRes;

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
