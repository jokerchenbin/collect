package com.example.longjoy.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.longjoy.myapplication.textview.URLDrawable;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class TextViewActivity extends AppCompatActivity {

    private TextView mTextView;
    final String sText = "测试图片信息：<br><img src=\"http://img.dnbcw.info/2012320/3890981.gif\" />";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        mTextView = (TextView) findViewById(R.id.textView);

        mTextView.setText(Html.fromHtml(sText, new URLImageParser(mTextView), null));
    }


    public class URLImageParser implements Html.ImageGetter {

        TextView mTextView;

        public URLImageParser(TextView textView) {
            this.mTextView = textView;
        }

        @Override
        public Drawable getDrawable(String source) {
            final URLDrawable urlDrawable = new URLDrawable();
            // Log.d("ChapterActivity", Consts.BASE_URL + source);
            ImageLoader.getInstance().loadImage(source, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    urlDrawable.bitmap = loadedImage;
                    urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());
                    mTextView.invalidate();
                    mTextView.setText(mTextView.getText()); // 解决图文重叠
                }
            });
            return urlDrawable;
        }
    }


    public class URLDrawable extends BitmapDrawable {
        protected Bitmap bitmap;


        @Override
        public void draw(Canvas canvas) {
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }
}
