package com.example.day9homework.model;

import com.example.day9homework.base.BaseModel;
import com.example.day9homework.bean.HomeBean;
import com.example.day9homework.callback.HomeCallback;
import com.example.day9homework.net.ApiService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeModel extends BaseModel {
    public void getData(HomeCallback homeCallback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<HomeBean> observable = apiService.getData();
        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<HomeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(HomeBean homeBean) {
                        homeCallback.onSuccess(homeBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        homeCallback.onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
