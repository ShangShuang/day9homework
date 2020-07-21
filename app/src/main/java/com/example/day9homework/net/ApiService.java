package com.example.day9homework.net;

import com.example.day9homework.bean.HomeBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

public interface ApiService {
    String BaseUrl = "https://www.wanandroid.com/";
    String downbaseUrl = "https://alissl.ucdl.pp.uc.cn/";

    @GET("project/list/1/json?cid=294")

    Observable<HomeBean> getData();

    @GET("fs08/2019/07/05/1/110_17e4089aa3a4b819b08069681a9de74b.apk")
    @Streaming
    Observable<ResponseBody> getApk();

}
