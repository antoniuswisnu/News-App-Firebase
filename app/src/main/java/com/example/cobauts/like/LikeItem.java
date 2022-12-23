package com.example.cobauts.like;

public class LikeItem {

    private String item_title;
    private String key_id;
    private String item_desc;

    public LikeItem(){}

    public LikeItem(String item_title, String key_id, String item_desc) {
        this.item_title = item_title;
        this.key_id = key_id;
        this.item_desc = item_desc;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }
}
