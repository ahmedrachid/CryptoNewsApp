package com.digger.app.model;

public class News {

    private String title;
    private String source;
    private String imageUrl;
    private String url;
    private String description;
    public News(String title, String source, String imageUrl, String url, String description) {
        this.title = title;
        this.source = source;
        this.imageUrl = imageUrl;
        this.url = url;
        this.description = description;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
