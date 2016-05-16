package fr.nicolabo.myyoutube.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fr.nicolabo.myyoutube.R;
import fr.nicolabo.myyoutube.model.Video;

/**
 * Created by nicolasboueme on 15/05/2016.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private static final String TAG = VideoAdapter.class.getSimpleName();
    private List<Video> videos;
    private final VideoClickListener listener;

    public VideoAdapter(VideoClickListener listener) {
        videos = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);

        return new VideoHolder(row);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        Video currentVideo = videos.get(position);

        holder.videoName.setText(currentVideo.getName());
        holder.videoDescription.setText(currentVideo.getDescription());

        Picasso.with(holder.itemView.getContext()).load(currentVideo.getImageUrl()).into(holder.videoThumbnail);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void addVideo(Video video) {
        Log.d(TAG, video.getName());
        videos.add(video);
        notifyDataSetChanged();
    }

    public Video getSelectedVideo(int position) {
        return videos.get(position);
    }

    public class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView videoThumbnail;
        private TextView videoName;
        private TextView videoDescription;

        public VideoHolder(View itemView) {
            super(itemView);

            videoThumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);
            videoName = (TextView) itemView.findViewById(R.id.video_name);
            videoDescription = (TextView) itemView.findViewById(R.id.video_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(getLayoutPosition());
        }
    }

    public interface VideoClickListener {
        void onClick(int position);
    }
}
