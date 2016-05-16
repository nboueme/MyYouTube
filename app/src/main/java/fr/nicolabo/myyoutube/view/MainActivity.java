package fr.nicolabo.myyoutube.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import fr.nicolabo.myyoutube.R;
import fr.nicolabo.myyoutube.controller.RestManager;
import fr.nicolabo.myyoutube.model.Video;
import fr.nicolabo.myyoutube.model.adapter.VideoAdapter;
import fr.nicolabo.myyoutube.model.helper.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements VideoAdapter.VideoClickListener {

    private RecyclerView recyclerView;
    private RestManager manager;
    private VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configViews();

        manager = new RestManager();
        Call<List<Video>> listCall = manager.getVideoService().getAllVideos();
        listCall.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                if (response.isSuccessful()) {
                    List<Video> videos = response.body();

                    for (int i = 0; i < videos.size(); i++) {
                        Video video = videos.get(i);
                        videoAdapter.addVideo(video);
                    }
                } else {
                    int sc = response.code();
                    switch (sc) {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {

            }
        });
    }

    private void configViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        videoAdapter = new VideoAdapter(this);
        recyclerView.setAdapter(videoAdapter);
    }

    @Override
    public void onClick(int position) {
        Video selectedVideo = videoAdapter.getSelectedVideo(position);

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Constants.REFERENCE.VIDEO, selectedVideo);
        startActivity(intent);
    }
}
