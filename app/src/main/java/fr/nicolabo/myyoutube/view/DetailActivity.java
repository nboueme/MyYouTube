package fr.nicolabo.myyoutube.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.nicolabo.myyoutube.R;
import fr.nicolabo.myyoutube.model.Video;
import fr.nicolabo.myyoutube.model.helper.Constants;

/**
 * Created by nicolasboueme on 16/05/2016.
 */
public class DetailActivity extends AppCompatActivity {
    
    private ImageView videoThumbnail;
    private TextView videoName;
    private TextView videoDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        Video video = (Video) intent.getSerializableExtra(Constants.REFERENCE.VIDEO);
        
        configViews();

        Picasso.with(getApplicationContext()).load(video.getImageUrl()).into(videoThumbnail);
        videoName.setText(video.getName());
        videoDescription.setText(video.getDescription());
    }

    private void configViews() {
        videoThumbnail = (ImageView) findViewById(R.id.video_thumbnail);
        videoName = (TextView) findViewById(R.id.video_name);
        videoDescription = (TextView) findViewById(R.id.video_description);
    }
}
