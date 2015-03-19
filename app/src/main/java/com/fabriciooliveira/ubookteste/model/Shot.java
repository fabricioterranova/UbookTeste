package com.fabriciooliveira.ubookteste.model;

import java.io.Serializable;

/**
 * Created by fabriciooliveira on 3/18/15.
 */
public class Shot implements Serializable{

    private int id;

    private String title;

    private String description;

    private long viewsCount;

    private int commentsCount;

    private String createdAt;

    private String urlImage;

    public Shot(){

    }

    public Shot(String title, String description, long viewsCount, String urlImage, String createdAt, int commentsCount) {
        this.title = title;
        this.description = description;
        this.viewsCount = viewsCount;
        this.urlImage = urlImage;
        this.createdAt = createdAt;
        this.commentsCount = commentsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(long viewsCount) {
        this.viewsCount = viewsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
