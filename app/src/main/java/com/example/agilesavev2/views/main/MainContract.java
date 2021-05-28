package com.example.agilesavev2.views.main;

import android.view.View;

/**
 * The contract interface contains the MVP interfaces of View and Presenter
 * The interfaces are implemented within {@link MainContract} for view
 * and {@link MainPresenter} for presenter
 */
public interface MainContract {
    /**
     * The interfaces contains all
     * the relevant methods the views
     * and the presenter should perform.
     */
    interface IMainView{
        void showLogin();
        void showRegister();
    }
    interface IMainPresenter{
        void handleLoginAction(View view);
        void handleRegisterAction(View view);
    }
}
