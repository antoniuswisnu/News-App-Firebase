package com.example.cobauts;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailActivity extends AppCompatActivity {

    TextView title, desc;
    String txtTitle, txtDesc;
    int img_photo;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        txtTitle = bundle.getString("title");
        txtDesc = bundle.getString("desc");
        img_photo = bundle.getInt("picture");

        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        img = findViewById(R.id.image);

        title.setText(txtTitle);
        desc.setText(txtDesc);

        Glide.with(DetailActivity.this)
                .load(img_photo)
                .apply(new RequestOptions().override(370,370))
                .into(img);
    }
}