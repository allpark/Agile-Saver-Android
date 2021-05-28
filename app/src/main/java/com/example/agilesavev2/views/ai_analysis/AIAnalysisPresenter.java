package com.example.agilesavev2.views.ai_analysis;

import android.content.Intent;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.services.Services;

public class AIAnalysisPresenter implements AIAnalysisContract.iAIPresenter, Presenter {

    AIAnalysisContract.iAIView view;
    Services services;

    public AIAnalysisPresenter(Intent intent,AIAnalysisContract.iAIView view) {
        this.view = view;
        services = new Services(this, this.view.getContext());
    }


    public void test(){
    }


    @Override
    public void getActivity() {

    }

    // hmmm
    @Override
    public <T> void handleOnSuccess(int code,T data) {

    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }

}
