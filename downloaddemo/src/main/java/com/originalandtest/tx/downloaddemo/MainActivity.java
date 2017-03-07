package com.originalandtest.tx.downloaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.originalandtest.tx.downloaddemo.download.DownloadInfo;
import com.originalandtest.tx.downloaddemo.entity.ApkModel;

public class MainActivity extends AppCompatActivity {

    /*龙之谷url，633M*/
    private String testPath = "http://imtt.dd.qq.com/16891/EB8256FDB1936894A734FA57057FDAD5.apk?fsname=com.tencent.tmgp.dragonnest_1.11.0_170225.apk&csr=1bbd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnDownload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
    }

    private void download() {
        ApkModel apkModel = new ApkModel();
        apkModel.setName("龙之谷");
        apkModel.setUrl(testPath);

        DownloadInfo downloadInfo = getDownloadInfo(apkModel);



        //这里，通过转化得到对应的downloadInfo

    }

    private DownloadInfo getDownloadInfo(ApkModel apkModel) {
        DownloadInfo info = new DownloadInfo();
        info.setUrl(apkModel.getUrl());
        return info;
    }
}
