package org.bosorio.instagram.dev.models;

import java.util.Date;

public class PostUser {

    private Long id;

    private String user;

    private String image;

    private String description;

    private Date createdAt;

    public PostUser() {
    }

    public PostUser(Long id, String user, String image, String description, Date createdAt) {
        this.id = id;
        this.user = user;
        this.image = image;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
