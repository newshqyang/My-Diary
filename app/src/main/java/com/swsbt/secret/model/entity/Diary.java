package com.swsbt.secret.model.entity;

import java.util.Set;

public class Diary {
    private int id;
    private String title;
    private String date;
    private Set<String> pictureSet;

    public Set<String> getPictureSet() {
        return pictureSet;
    }

    public void setPictureSet(Set<String> pictureSet) {
        this.pictureSet = pictureSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Diary(){}

    public Diary(int id, String title, String date, Set<String> pictureSet) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.pictureSet = pictureSet;
    }

    public Diary(int id, String title, String date){
        super();
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public Diary(int id, String title){
        super();
        this.id = id;
        this.title = title;
    }
}
