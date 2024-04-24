package org.bosorio.instagram.dev.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@Entity
@Table(name = "posts")
@XmlRootElement
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @NotEmpty
    private String image;

    private String description;

    @Column(name = "created_at")
    private Date createdAt;

    public Post() {
    }

    public Post(Long userId, String image, String description) {
        this.userId = userId;
        this.image = image;
        this.description = description;
    }

    public Post(Long id, Long userId, String image, String description, Date createdAt) {
        this.id = id;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = new Date();
    }
}
