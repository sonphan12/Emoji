package com.horizon.emoji;

import android.app.Application;

import com.horizon.emoji.emoji.ZEmojiParser;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ZEmojiParser.init(this);
    }
}
