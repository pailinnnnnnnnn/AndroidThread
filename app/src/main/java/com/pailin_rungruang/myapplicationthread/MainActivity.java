package com.pailin_rungruang.myapplicationthread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.btn_click);
        Button button2 = (Button)findViewById(R.id.btn_click2);
        imageView = (ImageView) findViewById(R.id.iv_image_url);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);


    }

    private void show1() throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;

                try {
                    url = new URL("http://www.dreamworks.com/kungfupanda/au/images/uploads/pages/_1095/KFP3_Pos_Jumping_Adventure_lg.jpg");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                Bitmap bmp = null;
                try {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final Bitmap finalBmp = bmp;
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(finalBmp);
                    }
                });
            }
        }).start();

    }

    private class LoadImageTask extends AsyncTask <String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {

            URL url = null;

            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            final Bitmap finalBmp = bmp;

            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_click:
                try {
                    show1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case R.id.btn_click2:
                new LoadImageTask().execute("http://media-channel.nationalgeographic.com/media/uploads/photos/content/photo/2016/05/18/65725_PandaBabies.jpg");
                break;
        }
    }
}
