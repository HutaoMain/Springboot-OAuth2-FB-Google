package com.oauth.oauth.api.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Profile {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("picture")
    private Picture picture;

    @Data
    public static class Picture {
        @JsonProperty("data")
        private Data data;

        @Getter
        @Setter
        public static class Data {
            @JsonProperty("url")
            private String url;

            public String getUrl() {
                return url;
            }
        }
    }
}


