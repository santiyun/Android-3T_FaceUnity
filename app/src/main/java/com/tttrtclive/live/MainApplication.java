package com.tttrtclive.live;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Environment;

import com.tttrtclive.live.callback.MyTTTRtcEngineEventHandler;
import com.wushuangtech.utils.PviewLog;
import com.wushuangtech.wstechapi.TTTRtcEngine;

import java.io.File;
import java.util.Random;

public class MainApplication extends Application {

    /**
     * 回调类引用，用于接收SDK各种回调信令。
     */
    public MyTTTRtcEngineEventHandler mMyTTTRtcEngineEventHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        //1.创建自定义的 SDK 的回调接收类，继承自SDK的回调基类 TTTRtcEngineEventHandler
        mMyTTTRtcEngineEventHandler = new MyTTTRtcEngineEventHandler(getApplicationContext());
        //2.创建SDK的实例对象，APPID需要去官网上申请获取。
        TTTRtcEngine mTTTEngine = TTTRtcEngine.create(getApplicationContext(), <三体appid，官网申请>,
                false, mMyTTTRtcEngineEventHandler);
        if (mTTTEngine == null) {
            System.exit(0);
            return;
        }
        // ------ SDK初始化完成，以下为 Demo 逻辑或 SDK 的可选操作接口。
        //生成一个随机的用户ID，Demo无需手动输入。
        Random mRandom = new Random();
        LocalConfig.mLocalUserID = mRandom.nextInt(999999);
        if (!isApkDebugable()) {
            //手机日志到SD卡的指定文件夹内。
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                String abs = externalStorageDirectory.toString() + "/3T_Live_Face";
                mTTTEngine.setLogFile(abs);
            } else {
                PviewLog.i("Collection log failed! , No permission!");
            }
        }

    }

    public boolean isApkDebugable() {
        try {
            ApplicationInfo info = this.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception ignored) {
        }
        return false;
    }
}
