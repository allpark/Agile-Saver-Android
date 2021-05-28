package com.example.agilesavev2.views.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityMainBinding;
import com.example.agilesavev2.views.home.HomeActivity;
import com.example.agilesavev2.views.login.LoginActivity;
import com.example.agilesavev2.views.register.RegisterActivity;
import com.example.agilesavev2.views.splash_screens.OpenAppActivityMainActivity;

/**
This is the view activity for the Main section of the app
It is responsible for displaying the Main display {@link R.layout#activity_main}, but also
updating the display to login page or register page.
The orders of the update view is given from the presenter {@link MainPresenter}

 */
public class MainActivity extends AppCompatActivity implements MainContract.IMainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        Window g=getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        /**
         * Data Binding is used to add logic to the XML files in the "layout.activity_register.xml"
         * It is an alternative way to add logic such as onClickListeners here, and instead add onClick
         * references e.g @{presenter::handleLogin} in the xml files.
         */


        ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);
        /**
         * {@link R.layout#activity_main} also has a variable called presenter, it would need to reference
         * the presenter by using .setPresenter
         */
        MainPresenter presenter = new MainPresenter(MainActivity.this);
        binding.setPresenter(presenter);

    }

    /**
     *ShowLogin() is used to update the UI view from Main page to {@link LoginActivity} view
     * startActivity is used to achieve this. This is only invoked from the listeners
     * from {@link MainPresenter}
     */
    @Override
    public void showLogin() {
        startActivityForResult(new Intent(this, LoginActivity.class),1);
        overridePendingTransition(0, 0);
    }

    /**
     *ShowRegister() like ShowLogin() is used to update the UI to {@link RegisterActivity} view
     */
    @Override
    public void showRegister() {
        startActivityForResult(new Intent(this, RegisterActivity.class),2);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) finish();
    }
}