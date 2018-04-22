package com.example.bruger.birdwatching.JavaClass;

import android.content.Intent;

import java.io.Serializable;

public class Observations implements Serializable {
    private Integer birdId;
    private String comment;
    private String date;
    private Integer id;
    private double latitude;
    private double longitude;
    private String placename;
    private Integer population;
    private String userId;
    private String nameDanish;
    private String nameEnglish;

    public Observations(Integer birdId, String comment, String date, Integer id, double latitude, double longitude, String placename, Integer population, String userId, String nameDanish, String nameEnglish) {
        this.birdId = birdId;
        this.comment = comment;
        this.date = date;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placename = placename;
        this.population = population;
        this.userId = userId;
        this.nameDanish = nameDanish;
        this.nameEnglish = nameEnglish;
    }

    public Observations() {
        //tom
    }

    public Integer getBirdId() {
        return birdId;
    }

    public void setBirdId(Integer birdId) {
        this.birdId = birdId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNameDanish() {
        return nameDanish;
    }

    public void setNameDanish(String nameDanish) {
        this.nameDanish = nameDanish;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    @Override
    public String toString() {
        return "Bird ID: " + birdId + "\n" + "Comment: " + comment + "\n" + "Date: " + date + "\n" + "ID: " + id + "\n" + "Latitude: " +
                latitude + "\n" + "Longitude: " + longitude + "\n" + "Placename: " + placename + "\n" +
                "Population: " + population + "\n" + "User ID: " + userId + "\n" + "Danish name: " + nameDanish + "\n" + "English Name: " + nameEnglish + "\n" + "\n";
    }
}
