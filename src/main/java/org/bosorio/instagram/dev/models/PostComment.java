package org.bosorio.instagram.dev.models;

import java.util.Date;

public class PostComment {

    private Long id;

    private String content;

    private Long postId;

    private String user;

    private Date createdAt;

    public PostComment() {
    }

    public PostComment(Long id, String content, Long postId, String user, Date createdAt) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.user = user;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
