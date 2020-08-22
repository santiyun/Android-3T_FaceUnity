package com.tttrtclive.live.callback;

import com.wushuangtech.bean.TTTVideoFrame;

public interface OnLocalVideoDataCallBack {

    void onLocalVideoFrameCaptured(TTTVideoFrame frame);
}
