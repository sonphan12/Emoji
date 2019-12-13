package com.horizon.emoji.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.horizon.emoji.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView normalTextView = findViewById(R.id.textEmoji);
        ZEmojiTextView emojiTextView = findViewById(R.id.emojiTextView);
        ZEmojiButton emojiButton = findViewById(R.id.emojiButton);

        emojiButton.setOnClickListener((view) -> {
            emojiTextView.setText(emojiButton.getText());
            normalTextView.setText(emojiTextView.getText().toString());
            emojiButton.setText(String.format("Emoji :%02d:", new Random().nextInt(88) + 1));
        });
    }
}
