package fr.nicolabo.myyoutube.view;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.nicolabo.myyoutube.R;
import fr.nicolabo.myyoutube.controller.EndlessRecyclerViewScrollListener;
import fr.nicolabo.myyoutube.controller.ItemClickSupport;
import fr.nicolabo.myyoutube.model.Video;
import fr.nicolabo.myyoutube.model.VideoResponseList;
import fr.nicolabo.myyoutube.model.adapter.VideoAdapter;
import fr.nicolabo.myyoutube.model.callback.VideoService;
import fr.nicolabo.myyoutube.model.callback.VideoWebService;
import fr.nicolabo.myyoutube.model.helper.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nicolasboueme on 16/05/2016.
 */
public class ListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private VideoService videoService;
    private VideoAdapter videoAdapter;
    private List<Video> videos = new ArrayList<>();
    private String urlNextPage;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoService = VideoWebService.getInstanceWithoutToken(getActivity());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("videos", (ArrayList<? extends Parcelable>) videos);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablayout_list, container, false);
        ButterKnife.bind(this, view);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(linearLayoutManager);

        videoAdapter = new VideoAdapter();
        recyclerView.setAdapter(videoAdapter);

        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#0288D1"), PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.VISIBLE);

        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        swipeRefreshLayout.setOnRefreshListener(this);

        if (savedInstanceState != null && savedInstanceState.containsKey("videos")) {
            videos = savedInstanceState.getParcelableArrayList("videos");
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            for (int i = 0; i < videos.size(); i++) {
                Video video = videos.get(i);
                videoAdapter.addVideo(video);
            }
        } else {
            if (videos.isEmpty()) fillRecyclerView(false);
            else {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                for (int i = 0; i < videos.size(); i++) {
                    Video video = videos.get(i);
                    videoAdapter.addVideo(video);
                }
            }
        }

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Video selectedVideo = videoAdapter.getSelectedVideo(position);

                        final Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(Constants.REFERENCE.VIDEO, (Serializable) selectedVideo);

                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.right_to_left, R.anim.main_activity);
                    }
                });

        return view;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                videos.clear();
                Log.e("RECYCLERVIEW COUNT", recyclerView.getChildCount() + "");
                Log.e("VIDEO SWIPE", videos.size() + "");
                fillRecyclerView(true);
                //recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
                //videoAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    private void fillRecyclerView(final Boolean isRefresh) {

        Call<VideoResponseList> videoListCall = videoService.getAllVideos();

        videoListCall.enqueue(new Callback<VideoResponseList>() {
            @Override
            public void onResponse(Call<VideoResponseList> call, Response<VideoResponseList> response) {

                if (response.isSuccessful() && !response.body().getError()) {
                    videos.addAll(response.body().getVideo());
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    urlNextPage = response.body().getPaging();

                    if (isRefresh) {
                        videoAdapter.resetVideos();
                    }

                    for (int i = 0; i < videos.size(); i++) {
                        Video video = videos.get(i);
                        videoAdapter.addVideo(video);
                    }

                } else {
                    Log.e("ListFragment", response.body().getError().toString());
                }
            }

            @Override
            public void onFailure(Call<VideoResponseList> call, Throwable t) {
                Log.e("ListFragment", t.getMessage());
            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                nextPageVideo();
            }
        });

    }

    public void nextPageVideo() {
        Call<VideoResponseList> videoListNextCall = videoService.getAllVideosNext(urlNextPage);

        videoListNextCall.enqueue(new Callback<VideoResponseList>() {
            @Override
            public void onResponse(Call<VideoResponseList> call, Response<VideoResponseList> response) {
                if (response.isSuccessful() && !response.body().getError()) {
                    videos.addAll(response.body().getVideo());
                    urlNextPage = response.body().getPaging();
                    videoAdapter.resetVideos();

                    for (int i = 0; i < videos.size(); i++) {
                        Video video = videos.get(i);
                        videoAdapter.addVideo(video);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<VideoResponseList> call, Throwable t) {

            }
        });
    }
}
