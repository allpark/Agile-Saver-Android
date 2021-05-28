package com.example.agilesavev2.views.settings.user_feedback;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public interface UserFeedBackContract {
    interface UserFeedBackView{
        void goBack();
        Context getContext();
        Intent getIntentData();
        String getUserID();
        void showMessage();
        boolean formValidation();

    }
    interface UserFeedBackPresenter{
        void handleGoBack(View view);
        void handleSubmit(View view);
    }
}
