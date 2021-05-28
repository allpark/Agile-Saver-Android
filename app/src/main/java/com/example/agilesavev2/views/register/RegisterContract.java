package com.example.agilesavev2.views.register;

import android.view.View;


/**
 * The references of how contract interfaces
 * work is in {@link com.example.agilesavev2.views.main.MainContract}
 * essentially is used to implement the relevant interfaces for the view and
 * presenter
 */
public interface RegisterContract {
    /**
     * Register View interface is implemented in {@link RegisterActivity}
     * with all the relevant methods
     */
    interface IRegisterView{
        void back();
        void onSuccess();
        void onReject();
        void showLogin();
        void showInvalidRegisterMessage();
        String getUsername();
        String getEmail();
        String getPassword();
        void onRejectFormat();
    }
    /**
     * Register Presenter interface is implemented in {@link RegisterPresenter}
     * with all the relevant methods
     */
    interface IRegisterPresenter{
        void handleBack(View view);
        void handleLogin(View view);
        void handleSubmit(View view);
    }
}