package io.github.diegoms.rest.dto;

import java.util.List;

public class FollowerPerUserResponse {
    private Integer followerCount;
    private List<FollowerResponse> content;

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public List<FollowerResponse> getContent() {
        return content;
    }

    public void setContent(List<FollowerResponse> content) {
        this.content = content;
    }
}
