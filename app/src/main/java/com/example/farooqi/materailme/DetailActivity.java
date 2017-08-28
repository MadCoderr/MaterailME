package com.example.farooqi.materailme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    TextView sportTitle;
    ImageView sportImage;

    TextView sportDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        sportTitle = (TextView) findViewById(R.id.title_detail);
        sportImage = (ImageView) findViewById(R.id.imv_sports_detail);
        sportDescription = (TextView) findViewById(R.id.subTitle_detail);

        Intent intent = getIntent();
        sportTitle.setText(intent.getStringExtra("title"));
        sportDescription.setText(intent.getStringExtra("description"));
        Glide.with(this).load(intent.getIntExtra("image", 0)).into(sportImage);

    }
}
