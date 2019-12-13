package com.horizon.emoji.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.horizon.emoji.emoji.ZEmojiParser;

/**
 * Copyright (C) 2019, VNG Corporation.
 * Created by quocha2
 * On 13/12/2019
 */
public class ZEmojiButton extends AppCompatButton {

    private CharSequence mRawText = "";

    public ZEmojiButton(Context context) {
        super(context);
    }

    public ZEmojiButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZEmojiButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        mRawText = text != null ? text : "";
        super.setText(ZEmojiParser.parse(mRawText), type);
    }
}
