package com.ljqweb.community_rap.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String client_code;
    private String client_redirect_uri;
    private String state;
}
