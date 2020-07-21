package com.example.day9homework;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.day9homework.bean.MessageEvent;
import com.example.day9homework.net.ApiService;

import org.greenrobot.eventbus.EventBus;

import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class ShangService extends Service {
    public ShangService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        download();
        return super.onStartCommand(intent, flags, startId);
    }

    private void download() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiService.downbaseUrl)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<ResponseBody> observable = apiService.getApk();
        observable.subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        InputStream inputStream = responseBody.byteStream();
                        saveFile(inputStream, "/storage/emulated/0/bbb.apk", responseBody.contentLength());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private int count;

    private void saveFile(InputStream inputStream, String path, long contentLength) {
        count = 1;
        //最大值
        EventBus.getDefault().post(new MessageEvent(0, (int) contentLength, 0));

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, length);
                count += length;
                EventBus.getDefault().post(new MessageEvent(1, (int) contentLength, count));//发送最新进度
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
