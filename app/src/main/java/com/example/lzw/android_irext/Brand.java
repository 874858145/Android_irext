package com.example.lzw.android_irext;

import org.json.JSONException;
import org.json.JSONObject;

public class Brand {
    private int id;
    private String name;
    private int categoryId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public Brand(int id, String name, int categoryId)
    {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
    }

    public static Brand sectionData(JSONObject json){
        try {
            return new Brand(
                    json.getInt("id"),
                    json.getString("name"),
                    json.getInt("categoryId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
