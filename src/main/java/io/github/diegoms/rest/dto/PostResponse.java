package io.github.diegoms.rest.dto;

import io.github.diegoms.quarkusSocial.domain.model.Post;

import java.time.LocalDateTime;


public class PostResponse {
    private String text;
    private LocalDateTime dateTime;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public PostResponse fromEntity(Post post) {
        PostResponse response = new PostResponse();
        response.setText(post.getText());
        response.setDateTime(post.getDateTime());
        return response;
    }

}
