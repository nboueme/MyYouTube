package fr.nicolabo.myyoutube.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.nicolabo.myyoutube.R;
import fr.nicolabo.myyoutube.model.Video;
import fr.nicolabo.myyoutube.model.helper.ItemTouchHelperAdapter;
import fr.nicolabo.myyoutube.model.holder.VideoHolder;

/**
 * Created by nicolasboueme on 15/05/2016.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoHolder> implements ItemTouchHelperAdapter {

    private static final String TAG = VideoAdapter.class.getSimpleName();
    private List<Video> videos;

    public VideoAdapter() {
        videos = new ArrayList<>();
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);

        return new VideoHolder(row);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        Video currentVideo = videos.get(position);
        holder.bind(currentVideo);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void resetVideos() {
        videos.clear();
        notifyDataSetChanged();
    }

    public void addVideo(Video video) {
        videos.add(video);
        notifyDataSetChanged(); // refresh the RecyclerView
    }

    public Video getSelectedVideo(int position) {
        return videos.get(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Video prev = videos.remove(fromPosition);
        videos.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        videos.remove(position);
        notifyItemRemoved(position);
    }
}
