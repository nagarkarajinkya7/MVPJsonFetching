package com.appmaker.mvpexample.contractor;

import com.appmaker.mvpexample.model.MainViewDataModel;

import java.util.List;

public interface MainActivityContract {

    interface View {
        void initView();
        void showProgressDialog();
        void hideProgressDialog();
        void setViewData(List<MainViewDataModel> model);
    }

    interface Model {
        String getName();
        String getImageUrl();
        String getCountry();
        String getCity();
    }

    interface Presenter {
        void onClick(android.view.View view);
        void fetchJsonData();
    }
}
