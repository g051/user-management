package com.user.mngmnt.service;

import com.user.mngmnt.model.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private User user;

    public OnRegistrationCompleteEvent(
            User user, String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }

    public OnRegistrationCompleteEvent(Object source) {
        super(source);
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}