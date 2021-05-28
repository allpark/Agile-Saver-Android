package com.example.agilesavev2.views.login;

import android.content.Context;
import android.view.View;

import com.example.agilesavev2.models.users.User;

/**
 * The references of how contract interfaces
 * work is in {@link com.example.agilesavev2.views.main.MainContract}
 * essentially is used to implement the relevant interfaces for the view and
 * presenter
 */
public interface LoginContract {
    /**
     * Login View interface is implemented in {@link LoginActivity}
     * with all the relevant methods
     */
    interface LoginView{
        void back();
        void onSuccess(User userData);
        void onReject();
        void showRegister();
        void showForgotPassword();
        void showWelcomePage(User userData);
        String getEnteredName();
        String getEnteredPassword();
        void freezeButton();
        void unFreezeButton();
        Context getContext();
        void setRememberedPassword(String username, String password);
        void showProgress();
        void hideProgress();
        void showToastMessage(String message);

    }

    /**
     * Login presenter interface is implemented in {@link LoginPresenter}
     * with all the relevant methods
     */
    interface LoginPresenter{
        void handleBack(View view);
        void handleSubmit(View view);
        void handleSuccess(User userData);
        void handleFailure();
        void handleForgotPassword(View view);
        void handleRegister(View view);
        void handleRememberChecked(View view);
        void handleGetRemember(View view);
        void handleConnectionFailure();
    }
}
