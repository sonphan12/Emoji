package com.horizon.emoji.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.horizon.emoji.emoji.ZEmojiParser;

/**
 * Copyright (C) 2019, VNG Corporation.
 * Created by quocha2
 * On 11/12/2019
 */
public class ZEmojiTextView extends AppCompatTextView {

    private CharSequence mRawText = "";

    public ZEmojiTextView(Context context) {
        super(context);
    }

    public ZEmojiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZEmojiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence rawText, BufferType type) {
        mRawText = rawText != null ? rawText : "";
        super.setText(ZEmojiParser.parse(mRawText), type);
    }
}