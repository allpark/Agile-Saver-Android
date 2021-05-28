package com.example.agilesavev2.views.register;

import android.view.View;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.users.User;
import com.example.agilesavev2.services.Services;

import java.security.Provider;


/**
 * The RegisterPresenter class implements the RegisterPresenter interface from
 * {@link RegisterContract}, and is responsible for listening for the
 * buttons from {@link com.example.agilesavev2.R.layout#activity_register}
 * and updating the view UI
 */
public class RegisterPresenter implements RegisterContract.IRegisterPresenter, Presenter {
    RegisterContract.IRegisterView rView;
    Services service = new Services(this);
    //constructor
    public RegisterPresenter(RegisterContract.IRegisterView rView) {
        this.rView = rView;
    }

    /**
     * If the user selects the back buttom from {@link com.example.agilesavev2.R.layout#activity_register} then
     * this handleBack is called, this will use the view handle which is a reference from {@link RegisterActivity}
     * and call the back() method which is responsible for updating the view back to the main menu.
     */
    @Override
    public void handleBack(View view) {
        rView.back();
    }


    /**
     * The handleLogin() listener is called when the user presses the "Did you remember?" Textview
     * from {@link com.example.agilesavev2.R.layout#activity_register}
     * and uses the view handle to call the showLogin() from {@link RegisterActivity}
     */
    @Override
    public void handleLogin(View view) {
        rView.showLogin();
    }


    /**
     * The handleSubmit() listener is called when the user presses the submit button from
     * the Register UI from {@link com.example.agilesavev2.R.layout#activity_register}
     * {@link UNFINISHED}
     */
    @Override
    public void handleSubmit(View view) {
        String name = rView.getUsername();
        String email =rView.getEmail();
        String password = rView.getPassword();
        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            service.handleRegister(name, email, password,this);
        }else{
            rView.showInvalidRegisterMessage();
        }


    }


    @Override
    public <T> void handleOnSuccess(int code, T data) {
        if(code==3){
            rView.onSuccess();
        }
        if(code==4){
            rView.onRejectFormat();
        }

    }

    @Override
    public void handleOnReject() {
        rView.onReject();
    }

    @Override
    public void handleOnReject(int code) {

    }
}
