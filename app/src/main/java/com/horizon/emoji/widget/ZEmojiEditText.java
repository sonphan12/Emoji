package com.horizon.emoji.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.horizon.emoji.emoji.ZEmojiParser;

public class ZEmojiEditText extends AppCompatEditText {
    public ZEmojiEditText(Context context) {
        super(context);
    }

    public ZEmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZEmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        ZEmojiParser.parse(text, this.getPaint().getFontMetricsInt());
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }
}
