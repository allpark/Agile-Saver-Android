package com.example.agilesavev2.views.forgotPassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityForgotPasswordBinding;
import com.example.agilesavev2.views.login.LoginActivity;
import com.example.agilesavev2.views.main.MainActivity;

/**
 * Forgot Password Activity is used to display the forgot password activity display {@link R.layout#activity_forgot_password}
 * this page shows when the user selects the "Forgot Password?" button as seen from {@link R.layout#activity_login}.
 */
public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordContract.IForgotPasswordView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window g=getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        /**
         * references of how binding works is from {@link MainActivity}
         */
        ActivityForgotPasswordBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_password);
        ForgotPasswordPresenter presenter = new ForgotPasswordPresenter(this);
        binding.setPresenter(presenter);
        getSupportActionBar().hide();
    }

    /**
     * the back() is used to return back to the {@link R.layout#activity_main} view.
     */
    @Override
    public void back() {
        startActivity(new Intent(this, MainActivity.class));
    }

    /**
     * onSuccess() is triggered if the email is correct, this will send the user
     * a message stating an email has been sent for them to change their password
     * {@link UNFINISHED}
     */
    @Override
    public void onSuccess() {

    }

    /**
     * onReject() is triggered if the email is incorrect, this will show the user a message
     * stating the email does not exist in the system
     * {@link UNFINISHED}
     */
    @Override
    public void onReject() {

    }

    /**
     *ShowLogin() is used to update the UI view from forgot password page to {@link LoginActivity} view
     */
    @Override
    public void showLogin() {
        startActivity(new Intent(this, LoginActivity.class));

    }
}