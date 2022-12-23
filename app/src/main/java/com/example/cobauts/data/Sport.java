package com.example.cobauts.data;

import com.google.firebase.database.Exclude;

public class Sport {

    @Exclude
    private String title;
    private String desc;
    private String age;
    private String kategori;
    private String tanggal;
    private String key;
    private String position;

    public Sport(){}

    public Sport(String title, String desc, String kategori, String age, String tanggal)
    {
        this.title = title;
        this.desc = desc;
        this.kategori = kategori;
        this.age = age;
        this.tanggal = tanggal;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String postition) {
        this.position = postition;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }




}
