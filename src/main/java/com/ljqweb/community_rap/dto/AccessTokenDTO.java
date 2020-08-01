package com.ljqweb.community_rap.dto;


public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String client_code;
    private String client_redirect_uri;
    private String state;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getClient_code() {
        return client_code;
    }

    public void setClient_code(String client_code) {
        this.client_code = client_code;
    }

    public String getClient_redirect_uri() {
        return client_redirect_uri;
    }

    public void setClient_redirect_uri(String client_redirect_uri) {
        this.client_redirect_uri = client_redirect_uri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
