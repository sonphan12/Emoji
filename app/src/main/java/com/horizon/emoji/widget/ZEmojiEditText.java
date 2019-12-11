package com.horizon.emoji.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.horizon.emoji.emoji.ZEmojiParser;

public class ZEmojiEditText extends AppCompatEditText {
    public ZEmojiEditText(Context context) {
        super(context);
        init();
    }

    public ZEmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZEmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        addTextChangedListener(new EmojiTextWatcher(this));
    }

    private static class EmojiTextWatcher implements TextWatcher {

        ZEmojiEditText mEmojiEditText;

        EmojiTextWatcher(ZEmojiEditText emojiEditText) {
            mEmojiEditText = emojiEditText;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ZEmojiParser.parse(s, mEmojiEditText.getPaint().getFontMetricsInt());
        }
    }
}
