package com.example.dell.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends Activity {

    ImageView imageView;
    ScrollView scrollView;
    LinearLayout linearLayout;
    Button but;
    Button setPaper;
    Button but2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but = (Button) findViewById(R.id.button);
        but2 = (Button) findViewById(R.id.button2);
        setPaper = (Button) findViewById(R.id.setPaper);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        linearLayout = (LinearLayout) findViewById(R.id.line);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "fffff", Toast.LENGTH_SHORT).show();
                //camera
//                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivity(i);
                //pick
                   Intent i = new Intent(Intent.ACTION_PICK,
                           MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i,2);

            }
        });
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSecontIntent = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(toSecontIntent);
            }
        });
        setPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickWallpaper = new Intent(Intent.ACTION_SET_WALLPAPER);
                startActivity(pickWallpaper);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2){
            switch (resultCode){
                case Activity.RESULT_OK:{
                    Uri uri = data.getData();
                    Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                    cursor.moveToFirst();
                    String imgNo = cursor.getString(0);
                    String imgPath = cursor.getString(1);
                    String imgSize = cursor.getString(2);
                    String imgName = cursor.getString(3);
                    Log.e("shouli.gong","imgNo = "+imgNo);
                    Log.e("shouli.gong","imgPath = "+imgPath);
                    Log.e("shouli.gong","imgSize = "+imgSize);
                    Log.e("shouli.gong","imgName = "+imgName);
                    cursor.close();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 10;
                    Bitmap bitmap = BitmapFactory.decodeFile(imgPath,options);
                    imageView = new ImageView(this);
                    imageView.setImageBitmap(bitmap);
                    imageView.setPadding(0,0,0,50);
                    linearLayout.addView(imageView);
//                    imageView.setBackgroundColor(Color.RED);
                }
                break;
                case Activity.RESULT_CANCELED:
                    Log.e("shouli.gong","取消 ");
                    break;
            }
        }
    }
}
