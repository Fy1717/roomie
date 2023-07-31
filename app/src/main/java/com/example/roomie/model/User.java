package com.example.roomie.model;

import android.util.Log;

public class User {
    private static String id;
    private static String email;
    private static String username;
    private static String name;
    private static String surname;
    private static String password;
    private static String accessToken;

    private static String accessTokenExpiration;
    private static String refreshToken;
    private static String refreshTokenExpiration;

    private static Boolean rememberMe;

    private static Boolean isAnonymous;

    private static String secret;

    private static User user;

    private User() {}

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }

        return user;
    }

    public static String getId() {
        return id;
    }
    public static void setId(String id) {
        User.id = id;
    }
    public static String getEmail() {
        return email;
    }
    public static void setEmail(String email) {
        User.email = email;
    }
    public static String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        User.username = username;
    }
    public static String getPassword() {
        return password;
    }
    public static void setPassword(String password) {
        User.password = password;
    }
    public static String getName() {
        return name;
    }
    public static void setName(String name) {
        User.name = name;
    }
    public static String getSurname() {
        return surname;
    }
    public static void setSurname(String surname) {
        User.surname = surname;
    }
    public static String getAccessToken() {
        return accessToken;
    }
    public static void setAccessToken(String accessToken) {
        User.accessToken = accessToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(String refreshToken) {
        User.refreshToken = refreshToken;
    }
    public static String getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public static void setAccessTokenExpiration(String accessTokenExpiration) {
        User.accessTokenExpiration = accessTokenExpiration;
    }

    public static String getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public static void setRefreshTokenExpiration(String refreshTokenExpiration) {
        User.refreshTokenExpiration = refreshTokenExpiration;
    }

    public static Boolean getRememberMe() {
        return rememberMe;
    }

    public static void setRememberMe(Boolean rememberMe) {
        User.rememberMe = rememberMe;
    }

    public static Boolean getIsAnonymous() {
        return isAnonymous;
    }

    public static void setIsAnonymous(Boolean isAnonymous) {
        User.isAnonymous = isAnonymous;
    }

    public static String getSecret() {
        return secret;
    }

    public static void setSecret(String secret) {
        User.secret = secret;
    }

    public User(String name, String surname, String username, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String secret) {
        this.secret = secret;
    }

    public String toJsonForRegister() {
        return "{\"name\":\"" + name + "\",\"surname\":\"" + surname + "\",\"username\":\"" + username + "\",\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
    }

    public String toJsonForLogin() {
        return "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
    }

    public String toJsonForAnonymousLogin() {
        return "{\"id\":\"" + "SpaApp" + "\",\"secret\":\"" + secret + "\"}";
    }

    public String toJsonForLogout() {
        Log.i("USER-RT", String.valueOf(refreshToken));

        return "{\"token\":\"" + refreshToken + "\"}";
    }
}
