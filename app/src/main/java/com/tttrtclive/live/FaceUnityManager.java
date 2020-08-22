package com.tttrtclive.live;

import android.content.Context;

import com.faceunity.nama.FURenderer;
import com.faceunity.nama.ui.BeautyControlView;
import com.tttrtclive.live.callback.MyTTTRtcEngineEventHandler;
import com.tttrtclive.live.ui.MainActivity;
import com.wushuangtech.bean.TTTVideoFrame;

/**
 * 相芯美颜管理类
 */
public class FaceUnityManager {

    private FURenderer mFURenderer;

    /**
     * 初始化相芯美颜 SDK ，建议在 Application 里初始化，比较耗时，不要在主线程操作。
     *
     * @param context 使用 Application 级别的 Context
     */
    public static void initFaceUnity(Context context) {
        FURenderer.initFURenderer(context);
    }

    /**
     * 配置相芯美颜 SDK，需要在主线程操作。
     */
    public void setupFaceUnity(MainActivity activity) {
        mFURenderer = new FURenderer.Builder(activity)
                .setMaxFaces(1) // 人脸识别数量越大，越耗性能
                .setInputTextureType(FURenderer.INPUT_2D_TEXTURE)
                .build();
        mFURenderer.onSurfaceCreated();
        // 获取操作相芯美颜的 UI 控件
        BeautyControlView beautyControlView = activity.findViewById(R.id.beauty_control);
        // 注册到 FURenderer 上
        beautyControlView.setOnFaceUnityControlListener(mFURenderer);
        // 注册上报本地视频裸数据和texture的回调，将三体SDK上报的视频数据传递给相芯美颜
        MyTTTRtcEngineEventHandler myTTTRtcEngineEventHandler = ((MainApplication) activity.getApplicationContext()).getMyTTTRtcEngineEventHandler();
        myTTTRtcEngineEventHandler.setOnLocalVideoDataCallBack(activity);
    }

    /**
     * 清理相芯美颜 SDK 资源，需要在主线程操作。
     */
    public void destoryFaceUnity() {
        mFURenderer.onSurfaceDestroyed();
        mFURenderer = null;
    }

    /**
     * 接收每一帧视频数据，传递给相芯美颜 SDK 进行处理。
     */
    public void onDrawFrame(TTTVideoFrame frame) {
        FURenderer fuRenderer = mFURenderer;
        if (fuRenderer != null) {
            frame.textureID = fuRenderer.onDrawFrameSingleInput(frame.textureID, frame.stride, frame.height);
        }
    }
}
