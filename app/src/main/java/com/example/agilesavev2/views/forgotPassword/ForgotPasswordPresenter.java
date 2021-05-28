package com.example.agilesavev2.views.forgotPassword;

import android.view.View;



/**
 * The ForgotPasswordPresenter class implements the Forgot Password interface from
 * {@link ForgotPasswordContract}, and is responsible for listening for the
 * buttons from {@link com.example.agilesavev2.R.layout#activity_forgot_password}
 * and updating the view UI
 */
public class ForgotPasswordPresenter implements ForgotPasswordContract.IForgotPasswordPresenter{
    ForgotPasswordContract.IForgotPasswordView fpView;

    //constructor
    public ForgotPasswordPresenter(ForgotPasswordContract.IForgotPasswordView fpView) {
        this.fpView = fpView;
    }

    /**
     * If the user selects the back buttom from {@link com.example.agilesavev2.R.layout#activity_forgot_password} then
     * this handleBack is called, this will use the view handle which is a reference from {@link ForgotPasswordActivity}
     * and call the back() method which is responsible for updating the view back to the main menu.
     */
    @Override
    public void handleBack(View view) {

        fpView.back();
    }

    /**
     * If the user selects the submit button from the {@link com.example.agilesavev2.R.layout#activity_forgot_password} view then
     * the handleSend() method is triggered, this uses the view handle and invokes the onSuccess() method
     * {@link UNFINISHED}
     */
    @Override
    public void handleSend(View view) {

    }

    /**
     * If the user remembered their password and selected the "Did you remember?" TextView from {@link com.example.agilesavev2.R.layout#activity_forgot_password}
     * then the handleLogin() method is triggered, it uses the view handle to invoke the showLogin() method
     */
    @Override
    public void handleLogin(View view) {

        fpView.showLogin();
    }
}
