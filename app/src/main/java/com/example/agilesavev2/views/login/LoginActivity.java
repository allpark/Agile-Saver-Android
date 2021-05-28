package com.example.agilesavev2.views.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityLoginBinding;
import com.example.agilesavev2.models.users.User;
import com.example.agilesavev2.views.complete_signup.CompleteSignUpActivity;
import com.example.agilesavev2.views.forgotPassword.ForgotPasswordActivity;
import com.example.agilesavev2.views.home.HomeActivity;
import com.example.agilesavev2.views.main.MainActivity;
import com.example.agilesavev2.views.register.RegisterActivity;
import com.example.agilesavev2.views.splash_screens.LoadingScreenActivity;
import com.example.agilesavev2.views.splash_screens.welcome.WelcomeActivity;
import com.example.agilesavev2.views.splash_screens.welcomeback.WelcomeBackActivity;

/**
 * Login Activity is used to display the login activity display {@link R.layout#activity_login}
 * this page shows when the user selects the "login" button as seen from {@link R.layout#activity_main}.
 * more reference on this is from {@link MainActivity}
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window g=getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        /**
         * references of how binding works is from {@link MainActivity}
         */
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        LoginPresenter loginPresenter = new LoginPresenter(this);
        binding.setPresenter(loginPresenter);
        getSupportActionBar().hide();
        loginPresenter.handleGetRemember(findViewById(R.id.remember));
    }

    /**
     * the back() is used to return back to the {@link R.layout#activity_main} view.
     */
    @Override
    public void back() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(0, 0);
    }

    /**
     * onSuccess() is triggered if the login is correct, this will direct the user
     * to the main page
     * {@link UNFINISHED}
     */
    @Override
    public void onSuccess(User userData) {


        new Handler().postDelayed(new Runnable() {
            public void run() {
                //go to main page
                Intent homeActivity = new Intent(LoginActivity.this,HomeActivity.class);
                homeActivity.putExtra("ID",userData.getId());
                homeActivity.putExtra("NAME",userData.getUserName());
                homeActivity.putExtra("EMAIL",userData.getEmail());
                homeActivity.putExtra("MAIN_CURRENCY", userData.getMain_currency());

                overridePendingTransition(0, 0);
                startActivity(homeActivity);
                setResult(RESULT_OK);
                finish();

            }
        }, 3000);
        showWelcomeBackSplashScreen(userData);
    }

    /**
     * onReject() is triggered if the login is incorrect, this will show the user an
     * incorrect email or password message
     * {@link UNFINISHED}
     */
    @Override
    public void onReject() {
        Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
    }

    public void showWelcomeSplashScreen(){
        startActivity(new Intent(this, WelcomeActivity.class));
    }

    public void showWelcomeBackSplashScreen(User userData){
        
        Intent welcomeIntent = new Intent(LoginActivity.this, WelcomeBackActivity.class);
        welcomeIntent.putExtra("Username",userData.getUserName());
        Intent welcome = new Intent(welcomeIntent);
        startActivity(welcome);

    }

    /**
     * showRegister() is used to display the register page {@link R.layout#activity_register} from the
     * login page
     */
    @Override
    public void showRegister() {

        startActivity(new Intent(this, RegisterActivity.class));
    }

    /**
     * showForgotPassword() is used to display the register page {@link R.layout#activity_forgot_password} from the
     * login page
     */
    @Override
    public void showForgotPassword() {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    @Override
    public void showWelcomePage(User userData) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent homeActivity = new Intent(LoginActivity.this, CompleteSignUpActivity.class);
                homeActivity.putExtra("ID",userData.getId());
                homeActivity.putExtra("NAME",userData.getUserName());
                homeActivity.putExtra("EMAIL",userData.getEmail());
                homeActivity.putExtra("MAIN_CURRENCY", userData.getMain_currency());

                overridePendingTransition(0, 0);
                startActivity(homeActivity);
                setResult(RESULT_OK);
                finish();
            }
        }, 3000);
        showWelcomeSplashScreen();


    }


    @Override
    public String getEnteredName() {
        EditText username = findViewById(R.id.login_username);
        return username.getText().toString();
    }

    @Override
    public String getEnteredPassword() {
        EditText password = findViewById(R.id.login_password);
        return password.getText().toString();
    }

    @Override
    public void freezeButton() {
        Button button = findViewById(R.id.loginButton);
        button.setClickable(false);
    }

    @Override
    public void unFreezeButton() {
        Button button = findViewById(R.id.loginButton);
        button.setClickable(true);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setRememberedPassword(String username, String password) {
        EditText usernameTv = findViewById(R.id.login_username);
        EditText passwordTv = findViewById(R.id.login_password);
        usernameTv.setText(username);
        passwordTv.setText(password);
    }

    @Override
    public void showProgress() {
        Intent intent = new Intent(this, LoadingScreenActivity.class);
        startActivity(intent);
    }

    @Override
    public void hideProgress() {
        LoadingScreenActivity.getInstance().finish();
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}