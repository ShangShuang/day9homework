package com.example.day9homework.callback;

import com.example.day9homework.bean.HomeBean;

public interface HomeCallback {
    void onSuccess(HomeBean homeBean);

    void onFail(String error);
}
