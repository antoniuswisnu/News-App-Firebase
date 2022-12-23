package com.example.cobauts.like;

public class NewsItem {

    private String title;
    private String desc;
    private String key;
    private String likeStatus;

    public NewsItem(){}

    public NewsItem(String title, String desc, String key, String likeStatus) {
        this.title = title;
        this.desc = desc;
        this.key = key;
        this.likeStatus = likeStatus;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }



}
