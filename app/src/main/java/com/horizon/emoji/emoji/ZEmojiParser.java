package com.horizon.emoji.emoji;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.Log;

import androidx.annotation.NonNull;

import com.horizon.emoji.Utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ZEmojiParser {
    private static final String TAG = ZEmojiParser.class.getSimpleName();

    private static Pattern ALIAS_PATTERN = Pattern.compile("(:\\d\\d:)");

    private static final String[] emojiData = {
            ":01:", ":02:", ":03:", ":04:", ":05:", ":06:", ":07:", ":08:", ":09:", ":10:",
            ":11:", ":12:", ":13:", ":14:", ":15:", ":16:", ":17:", ":18:", ":19:", ":20:",
            ":21:", ":22:", ":23:", ":24:", ":25:", ":26:", ":27:", ":28:", ":29:", ":30:",
            ":31:", ":32:", ":33:", ":34:", ":35:", ":36:", ":37:", ":38:", ":39:", ":40:",
            ":41:", ":42:", ":43:", ":44:", ":45:", ":46:", ":47:", ":48:", ":49:", ":50:",
            ":51:", ":52:", ":53:", ":54:", ":55:", ":56:", ":57:", ":58:", ":59:", ":60:",
            ":61:", ":62:", ":63:", ":64:", ":65:", ":66:", ":67:", ":68:", ":69:", ":70:",
            ":71:", ":72:", ":73:", ":74:", ":75:", ":76:", ":77:", ":78:", ":79:", ":80:",
            ":81:", ":82:", ":83:", ":84:", ":85:", ":86:", ":87:", ":88:"
    };

    private static final int MAX_EMOJI = emojiData.length;
    private static final int MAX_EMOJI_PER_COL = 41;

    private static final int EMOJI_FULL_SIZE = 64;

    private static final String EMOJI_SHEET_FILE = "emoji.png";

    private static Bitmap emojiSheet;

    private static Map<CharSequence, DrawableInfo> emojiMap = new HashMap<>();

    private ZEmojiParser() {
    }

    public static void init(Context context) {
        loadEmojiSheet(context);
        buildEmojiMap();
    }

    public static CharSequence parse(CharSequence text, FontMetricsInt fontMetrics) {
        Spannable emojiText;
        if (text instanceof Spannable) {
            emojiText = (Spannable) text;
        } else {
            emojiText = Spannable.Factory.getInstance().newSpannable(text);
        }

        Matcher matcher = ALIAS_PATTERN.matcher(text);

        while (matcher.find()) {
            String alias = matcher.group();

            if (emojiMap.containsKey(alias)) {
                EmojiDrawable drawable = new EmojiDrawable(emojiMap.get(alias));

                int start = matcher.start();
                int end = matcher.end();

                EmojiSpan span = new EmojiSpan(drawable, DynamicDrawableSpan.ALIGN_BOTTOM, fontMetrics);
                emojiText.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return emojiText;
    }

    private static void buildEmojiMap() {
        for (int i = 0; i < MAX_EMOJI; i++) {
            int rowIndex = i % MAX_EMOJI_PER_COL;
            int colIndex = i / MAX_EMOJI_PER_COL;

            int left = colIndex * EMOJI_FULL_SIZE;
            int top = rowIndex * EMOJI_FULL_SIZE;
            int right = left + EMOJI_FULL_SIZE;
            int bottom = top + EMOJI_FULL_SIZE;

            Rect rect = new Rect(left, top, right, bottom);

            emojiMap.put(emojiData[i], new DrawableInfo(rect, i));
        }
    }

    private static void loadEmojiSheet(Context context) {
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open(EMOJI_SHEET_FILE);

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = 1;
            if (Build.VERSION.SDK_INT >= 26) {
                opts.inPreferredConfig = Bitmap.Config.HARDWARE;
            }

            emojiSheet = BitmapFactory.decodeStream(is, null, opts);
            is.close();
        } catch (Throwable e) {
            Log.e(TAG, "loadEmoji: " + e.toString());
        }
    }

    // Nested classes
    private static class DrawableInfo {
        Rect rect;
        int emojiIndex;

        DrawableInfo(Rect r, int index) {
            rect = r;
            emojiIndex = index;
        }
    }

    private static class EmojiDrawable extends Drawable {
        private DrawableInfo info;
        private static Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        private static Rect rect = new Rect();

        EmojiDrawable(DrawableInfo i) {
            info = i;
        }

        DrawableInfo getDrawableInfo() {
            return info;
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            canvas.drawBitmap(emojiSheet, info.rect, getBounds(), paint);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSPARENT;
        }

        @Override
        public void setAlpha(int alpha) {
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
        }
    }

    private static class EmojiSpan extends ImageSpan {
        private FontMetricsInt fontMetrics;
        private int size;

        EmojiSpan(EmojiDrawable d, int verticalAlignment, FontMetricsInt original) {
            super(d, verticalAlignment);

            fontMetrics = original;

            if (fontMetrics != null) {
                size = Math.abs(fontMetrics.descent) + Math.abs(fontMetrics.ascent);
            }

            //Fallback size
            if (size == 0) {
                size = Utils.dp(20);
            }

            if (getDrawable() != null) {
                getDrawable().setBounds(0, 0, size, size);
            }
        }

        @Override
        public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, FontMetricsInt fm) {
            return size;
        }
    }
}
