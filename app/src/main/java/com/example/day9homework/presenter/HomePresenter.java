package com.example.day9homework.presenter;

import com.example.day9homework.base.BasePresenter;
import com.example.day9homework.bean.HomeBean;
import com.example.day9homework.callback.HomeCallback;
import com.example.day9homework.model.HomeModel;
import com.example.day9homework.view.HomeView;

public class HomePresenter extends BasePresenter<HomeView> implements HomeCallback {
    private HomeModel homeModel;

    @Override
    protected void initModel() {
        homeModel = new HomeModel();
        addModel(homeModel);
    }

    public void getData() {
        homeModel.getData(this);
    }

    @Override
    public void onSuccess(HomeBean homeBean) {
        mView.setData(homeBean);
    }

    @Override
    public void onFail(String error) {
        mView.showToast(error);
    }
}
