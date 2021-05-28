package com.example.agilesavev2.views.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityRegisterBinding;
import com.example.agilesavev2.views.login.LoginActivity;
import com.example.agilesavev2.views.main.MainActivity;

/**
 * Register Activity is used to display the Regisrer activity display {@link com.example.agilesavev2.R.layout#activity_register}
 * this page shows when the user selects the "Register" button as seen from {@link com.example.agilesavev2.R.layout#activity_main}.
 * more reference on this is from {@link MainActivity}
 */

public class RegisterActivity extends AppCompatActivity implements RegisterContract.IRegisterView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window g=getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);

        getSupportActionBar().hide();
        /**
         * references of how binding works is from {@link MainActivity}
         */
        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        RegisterPresenter presenter = new RegisterPresenter(this);
        binding.setPresenter(presenter);
    }

    /**
     * back() changes the view to the {@link R.layout#activity_main} view
     */
    @Override
    public void back() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(0, 0);
    }

    /**
     * onSuccess sends "you have been registered, check your email" message
     * {@link UNFINISHED}
     */
    @Override
    public void onSuccess() {
        Toast.makeText(this, "You have successfully registered!", Toast.LENGTH_SHORT).show();
    }

    /**
     * onReject sends the user an error message if username has been taken
     * or the email has been taken or the password isnt the required length,
     * of if the internet connection isn't available
     * {@link UNFINISHED}
     */
    @Override
    public void onReject() {
        Toast.makeText(this, "Account is already registered!", Toast.LENGTH_SHORT).show();
    }

    /**
     * showLogin() changes the view from {@link R.layout#activity_register} to {@link R.layout#activity_login}
     */
    @Override
    public void showLogin() {

        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void showInvalidRegisterMessage() {
        Toast.makeText(this, "Please fill in the form", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUsername() {
        EditText etName = findViewById(R.id.registerName);
        return etName.getText().toString();

    }

    @Override
    public String getEmail() {
        EditText etEmail = findViewById(R.id.registerEmail);
        return etEmail.getText().toString();
    }

    @Override
    public String getPassword() {
        EditText etPassword =findViewById(R.id.registerPassword);
        return etPassword.getText().toString();
    }

    @Override
    public void onRejectFormat() {
        if(getPassword().length()<8){
            Toast.makeText(this, "Password should be atleast 8 characters long", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Incorrect email format", Toast.LENGTH_SHORT).show();
        }
    }

}