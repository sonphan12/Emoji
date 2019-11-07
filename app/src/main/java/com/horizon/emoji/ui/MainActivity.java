package com.horizon.emoji.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.horizon.emoji.R;
import com.horizon.emoji.emoji.ZEmojiParser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textEmoji = findViewById(R.id.textEmoji);

        textEmoji.setText(ZEmojiParser.parse("hello :11: :01:", textEmoji.getPaint().getFontMetricsInt()));
    }
}
