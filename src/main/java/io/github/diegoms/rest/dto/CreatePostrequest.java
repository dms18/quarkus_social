package io.github.diegoms.rest.dto;

import lombok.Data;


public class CreatePostrequest {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
