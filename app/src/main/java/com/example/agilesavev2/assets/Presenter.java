package com.example.agilesavev2.assets;

import com.example.agilesavev2.models.users.User;

public interface Presenter {
    public <T> void handleOnSuccess(int code,T data);

    public void handleOnReject();
    public void handleOnReject(int code);

}
