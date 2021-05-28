package com.example.agilesavev2.views.main;

import android.view.View;


/**
 * The mainPresenter implements the presenter interface from {@link MainContract}
 * It is responsible for listening for user actions from the Main view UI
 * And its job is to use detect the changes and to update the view respectively
 */
public class MainPresenter implements MainContract.IMainPresenter {
    /**
     * A mView is a handle the presenter needs in order to update the view
     */
    MainContract.IMainView mView;

    //constructor
    public MainPresenter(MainContract.IMainView view){

        this.mView=view;
    }

    /**
     * handleLoginAction is used to invoke the showLogin() method from {@link MainActivity}
     * only if a user presses the login button.
     * The presenter makes use the of mView handle to invoke the showLogin() method to update
     * the view to the login UI.
     */
    @Override
    public void handleLoginAction(View view) {

        this.mView.showLogin();
    }

    /**
     * Like handleLoginAction, handleRegisterAction invokes the showRegister using the mView handle
     * which will update the UI to register UI. This only happens when the user presses the register
     * button
     */
    @Override
    public void handleRegisterAction(View view) {

        this.mView.showRegister();
    }
}
