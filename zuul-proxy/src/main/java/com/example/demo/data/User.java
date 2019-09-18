package com.example.demo.data;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class User {
    @Id
    private String token;
    private LocalDateTime firstCall;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getFirstCall() {
        return firstCall;
    }

    public void setFirstCall(LocalDateTime firstCall) {
        this.firstCall = firstCall;
    }

    public int getnCall() {
        return nCall;
    }

    public void setnCall(int nCall) {
        this.nCall = nCall;
    }

    private int nCall;


}
