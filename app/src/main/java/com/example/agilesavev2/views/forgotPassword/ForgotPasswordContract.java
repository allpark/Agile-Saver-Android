package com.example.agilesavev2.views.forgotPassword;

import android.view.View;


/**
 * The references of how contract interfaces
 * work is in {@link com.example.agilesavev2.views.main.MainContract}
 * essentially is used to implement the relevant interfaces for the view and
 * presenter
 */
public interface ForgotPasswordContract {
    /**
     * ForgotPassword View interface is implemented in {@link ForgotPasswordActivity}
     * with all the relevant methods
     */
    interface IForgotPasswordView{
        void back();
        void onSuccess();
        void onReject();
        void showLogin();

    }

    /**
     * ForgotPassword Presenter interface is implemented in {@link ForgotPasswordPresenter}
     * with all the relevant methods
     */
    interface IForgotPasswordPresenter{
        void handleBack(View view);
        void handleSend(View view);
        void handleLogin(View view);
    }
}
