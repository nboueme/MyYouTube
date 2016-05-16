package fr.nicolabo.myyoutube.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.nicolabo.myyoutube.R;
import fr.nicolabo.myyoutube.controller.RestManager;
import fr.nicolabo.myyoutube.model.Video;
import fr.nicolabo.myyoutube.model.adapter.VideoAdapter;
import fr.nicolabo.myyoutube.model.helper.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nicolasboueme on 16/05/2016.
 */
public class ListFragment extends Fragment implements VideoAdapter.VideoClickListener {

    private RecyclerView recyclerView;
    private RestManager manager;
    private VideoAdapter videoAdapter;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablayout_list, container, false);

        Log.e("INFLATE", "ok");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        videoAdapter = new VideoAdapter(this);
        recyclerView.setAdapter(videoAdapter);

        manager = new RestManager();
        Call<List<Video>> listCall = manager.getVideoService().getAllVideos();
        listCall.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                if (response.isSuccessful()) {
                    final List<Video> videos = response.body();
                    Log.e("VIDEO", videos.size() + "");

                    /*getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            videoAdapter.addVideos(videos);
                        }
                    });*/

                    for (int i = 0; i < videos.size(); i++) {
                        Video video = videos.get(i);
                        videoAdapter.addVideos(video);
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

        return view;
    }

    @Override
    public void onClick(int position) {
        Video selectedVideo = videoAdapter.getSelectedVideo(position);

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(Constants.REFERENCE.VIDEO, selectedVideo);
        startActivity(intent);
    }
}
