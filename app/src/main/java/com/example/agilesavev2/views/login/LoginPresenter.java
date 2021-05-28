package com.example.agilesavev2.views.login;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.users.User;
import com.example.agilesavev2.services.DatabaseHelper;
import com.example.agilesavev2.services.Services;

import java.util.HashMap;

/**
 * The loginPresenter class implements the loginPresenter interface from
 * {@link LoginContract}, and is responsible for listeneing for the
 * buttons from {@link com.example.agilesavev2.R.layout#activity_login}
 * and updating the view UI
 */
public class LoginPresenter extends AppCompatActivity implements LoginContract.LoginPresenter, Presenter {
    /**
     * To know how the view handle works, see in {@link com.example.agilesavev2.views.main.MainPresenter }
     */
    private LoginContract.LoginView lView;
    private Services services;
    //constructor
    public LoginPresenter(LoginContract.LoginView lView) {
        this.lView = lView;
        services =  new Services(this, this.lView.getContext());
    }


    /**
     * The handleSubmit() listener is called when the user presses the submit button from
     * the login UI {@link com.example.agilesavev2.R.layout#activity_login}
     * {@link UNFINISHED}
     */
    @Override
    public void handleSubmit(View view) {
        //view updates UI to loading screen
        this.lView.showProgress();

        //check with database
        String username=lView.getEnteredName();
        String password=lView.getEnteredPassword();
        lView.freezeButton();
        services.handleLogin(username,password,this);

    }

    @Override
    public void handleSuccess(User userData) {
//        if(userData.getRegistered()){
//            lView.showWelcomePage();
//        }
        lView.hideProgress();
        lView.freezeButton();
        if(userData.getFirstname()!=null){
            services.cacheUserData(Integer.parseInt(userData.getId()));
            HashMap<String, String> map = new HashMap<>();
            map.put("currency", userData.getMain_currency());
            services.getCurrencyConversionRates(map);
            services.getSubscriptionAvg();
            lView.onSuccess(userData);
        }else{
            lView.showWelcomePage(userData);
        }


    }

    @Override
    public void handleFailure() {
        lView.hideProgress();
        lView.unFreezeButton();
        lView.onReject();
    }

    /**
     * If the user selects the back buttom from {@link com.example.agilesavev2.R.layout#activity_login} then
     * this handeBack is called, this will use the view handle which is a reference from {@link LoginActivity}
     * and call the back() method which is responsible for updating the view back to the main menu.
     */
    @Override
    public void handleBack(View view) {
        lView.back();
    }

    /**
     * The handleForgotPassword() listener is called when the user presses the "Forgot Password?" Textview
     * from {@link com.example.agilesavev2.R.layout#activity_login}
     * and uses the view handle to call the showForgotPassword() from {@link LoginActivity}
     */
    @Override
    public void handleForgotPassword(View view) {

        lView.showForgotPassword();
    }

    /**
     * The handleRegister() method is called when the user selects the "Don't have an account?" textView from
     * {@link com.example.agilesavev2.R.layout#activity_login}
     * and uses the view handle to call the showRegister() from {@link LoginActivity}
     *
     */
    @Override
    public void handleRegister(View view) {

        lView.showRegister();
    }

    @Override
    public void handleRememberChecked(View view) {
        CheckBox checkBox = (CheckBox) view;
        if(checkBox.isChecked()){
            services.setRememberLogin(lView.getEnteredName(), lView.getEnteredPassword());
        }else{
            services.dontRememberLogin();
        }
    }

    @Override
    public void handleGetRemember(View view) {
        HashMap<String,String> map = services.getRememberLogin();
        String username = map.get("username");
        String password = map.get("password");
        if(!username.equals("") || !password.equals("")){
            CheckBox checkBox = (CheckBox) view;
            checkBox.setChecked(true);
        }
        lView.setRememberedPassword(username,password);
    }

    @Override
    public void handleConnectionFailure() {
        lView.showToastMessage("Can not connect to the servers :(");
        lView.hideProgress();
        lView.unFreezeButton();
    }


    @Override
    public <T> void handleOnSuccess(int code, T data) {

    }

    @Override
    public void handleOnReject() {
        lView.unFreezeButton();
    }

    @Override
    public void handleOnReject(int code) {

    }
}
