package com.example.agilesavev2.views.ai_analysis;

import android.content.Context;

public interface AIAnalysisContract {
    interface iAIView{
        void displayActivity();
        int getUserID();
        Context getContext();
    }
    interface iAIPresenter{
        void getActivity();
    }
}
