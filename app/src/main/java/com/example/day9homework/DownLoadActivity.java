package com.example.day9homework;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.day9homework.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownLoadActivity extends AppCompatActivity {

    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.btn_dowmload)
    Button btnDowmload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        ButterKnife.bind(this);
        getPermission();
        EventBus.getDefault().register(this);
        initListener();
    }

    private void initListener() {
        btnDowmload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(DownLoadActivity.this, ShangService.class));
            }
        });
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.REQUEST_INSTALL_PACKAGES}, 100);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(MessageEvent msg) {
        if (msg.getFlag() == 0) {
            pb.setMax(msg.getMax());
        } else {
            pb.setProgress(msg.getProgress());//当前进度值
            //计算百分比
            int max = msg.getMax();
            int progress = msg.getProgress();
            int num = (int) (((float) progress / max) * 100);
            tvNum.setText("当前下载进度" + num + "%");
            if (num == 100) {
                Toast.makeText(this, "下载完成", Toast.LENGTH_SHORT).show();
                //发送通知
                send();
            }
        }
    }

    private void send() {
        String channleId = "1";
        String channleName = "2";
        //创建通知管理器
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //设置渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channleId, channleName, NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(channel);
        }
        //创建通知对象
        Notification notification = new NotificationCompat.Builder(this, channleId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("新通知")
                .setContentText("下载完成")
                .build();
        //发送通知
        nm.notify(100, notification);
    }
}
