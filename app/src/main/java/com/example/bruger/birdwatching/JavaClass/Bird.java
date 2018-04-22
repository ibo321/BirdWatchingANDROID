package com.example.bruger.birdwatching.JavaClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Bird implements Serializable {

    private String created;
    private int id;
    private String nameDanish;
    private String nameEnglish;
    private String photoUrl;
    private String userId;

    public Bird(String created, int id, String nameDanish, String nameEnglish, String photoUrl, String userId) {
        this.created = created;
        this.id = id;
        this.nameDanish = nameDanish;
        this.nameEnglish = nameEnglish;
        this.photoUrl = photoUrl;
        this.userId = userId;
    }

    public Bird() {
        //tom
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Created: " + created + "\n" + "ID: " + id + "\n" + "Name in Danish: " + nameDanish + "\n" + "Name in English: " + nameEnglish + "\n" + "Photo URL: " +
                photoUrl + "\n" + "User ID: " + userId + "\n" + "\n";
    }

}
