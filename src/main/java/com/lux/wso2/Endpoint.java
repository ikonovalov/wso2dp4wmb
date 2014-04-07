package com.lux.wso2;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Igor on 07.04.2014.
 */
public class Endpoint {

    private final String url;

    private final String username;

    private final String password;

    public Endpoint(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Endpoint{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password.replaceAll(".", "*") + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Endpoint endpoint = (Endpoint) o;

        if (password != null ? !password.equals(endpoint.password) : endpoint.password != null) return false;
        if (url != null ? !url.equals(endpoint.url) : endpoint.url != null) return false;
        if (username != null ? !username.equals(endpoint.username) : endpoint.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
