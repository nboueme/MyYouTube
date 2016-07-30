package fr.nicolabo.myyoutube.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.nicolabo.myyoutube.R;
import fr.nicolabo.myyoutube.controller.AppPreferencesTool;
import fr.nicolabo.myyoutube.model.Video;
import fr.nicolabo.myyoutube.model.VideoResponse;
import fr.nicolabo.myyoutube.model.VideoResponseList;
import fr.nicolabo.myyoutube.model.callback.VideoService;
import fr.nicolabo.myyoutube.model.callback.VideoWebService;
import fr.nicolabo.myyoutube.model.helper.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nicolasboueme on 16/05/2016.
 */
public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.video_thumbnail) ImageView videoThumbnail;
    @BindView(R.id.video_name) TextView videoName;
    @BindView(R.id.video_description) TextView videoDescription;
    @BindView(R.id.video_id_picture) ImageView videoIdPicture;
    private Video video;
    private Boolean inLove;
    private VideoService videoService;
    private AppPreferencesTool appPreferencesTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        appPreferencesTool = new AppPreferencesTool(this);
        videoService = VideoWebService.getInstanceWithToken(this);

        isFavorite();

        Window window = getWindow();
        window.setStatusBarColor(Color.BLACK);

        if (progressBar != null)
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(Color.parseColor("#0288D1"), PorterDuff.Mode.MULTIPLY);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getBackground().setAlpha(75);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        video = (Video) intent.getSerializableExtra(Constants.REFERENCE.VIDEO);

        getSupportActionBar().setTitle(video.getName());

        progressBar.setVisibility(View.VISIBLE);

        Glide.with(getApplicationContext())
                .load(video.getImageUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(videoThumbnail);

        videoName.setText(video.getName());
        videoDescription.setText(video.getDescription());
    }

    private void isFavorite() {
        Call<VideoResponseList> videoListCall = videoService.getAllVideosUser(appPreferencesTool.getUserId());

        videoListCall.enqueue(new Callback<VideoResponseList>() {
            @Override
            public void onResponse(Call<VideoResponseList> call, Response<VideoResponseList> response) {
                if (response.isSuccessful() && !response.body().getError()) {
                    List<Video> videos = new ArrayList<>();
                    videos.addAll(response.body().getVideo());
                    for (int i = 0; i < videos.size(); i++) {
                        if (videos.get(i).getIdVideo().equals(video.getIdVideo())) {
                            inLove = true;
                            break;
                        }
                        else inLove = false;
                    }

                    Log.e("inLove", "" + inLove);
                    if (inLove) videoIdPicture.setBackgroundResource(R.drawable.ic_favorite_black);
                    else videoIdPicture.setBackgroundResource(R.drawable.ic_favorite_border_black);

                } else {

                }
            }

            @Override
            public void onFailure(Call<VideoResponseList> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.video_id)
    public void favorites() {
        if (inLove) {
            Call<VideoResponse> videoCall = videoService.deleteVideoUser(video.getIdVideo());
            videoCall.enqueue(new Callback<VideoResponse>() {
                @Override
                public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                    if (response.isSuccessful() && !response.body().getError()) {

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<VideoResponse> call, Throwable t) {

                }
            });
            videoIdPicture.setBackgroundResource(R.drawable.ic_favorite_border_black);
            inLove = false;

        } else {
            Call<VideoResponse> videoCall = videoService.addVideoUser(video.getIdVideo());
            videoCall.enqueue(new Callback<VideoResponse>() {
                @Override
                public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                    if (response.isSuccessful() && !response.body().getError()) {

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<VideoResponse> call, Throwable t) {

                }
            });
            videoIdPicture.setBackgroundResource(R.drawable.ic_favorite_black);
            inLove = true;
        }
    }

    @OnClick(R.id.video_url)
    public void gotToUrl() {
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getVideoUrl()));
        startActivity(browse);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.main_activity, R.anim.left_to_right);
    }
}
