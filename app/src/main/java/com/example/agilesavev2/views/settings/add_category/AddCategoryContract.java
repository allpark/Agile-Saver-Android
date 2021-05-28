package com.example.agilesavev2.views.settings.add_category;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public interface AddCategoryContract {
    interface AddCategoryView{
        void goBack();
        boolean formValidation();
        void showMessage(String message);
        String getUserID();
        Context getContextData();
        Intent getIntentData();
        String getCategoryName();

    }
    interface AddCategoryPresenter{
        void handleGoBack(View view);
        void handleSubmit(View view);
    }
}
