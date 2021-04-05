package cn.jcsmallming.entry.dynamiccontent;

import java.util.List;

public class ContentItem {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    public int getOrig_type() {
        return orig_type;
    }

    public void setOrig_type(int orig_type) {
        this.orig_type = orig_type;
    }

    private int orig_type;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Pictures> getPictures() {
        return pictures;
    }

    public void setPictures(List<Pictures> pictures) {
        this.pictures = pictures;
    }

    public int getPictures_count() {
        return pictures_count;
    }

    public void setPictures_count(int pictures_count) {
        this.pictures_count = pictures_count;
    }

    private List<Pictures> pictures;
    private int pictures_count;
}
