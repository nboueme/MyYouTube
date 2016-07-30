package fr.nicolabo.myyoutube.model.holder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import fr.nicolabo.myyoutube.R;
import fr.nicolabo.myyoutube.model.Video;
import fr.nicolabo.myyoutube.model.helper.ItemTouchHelperViewHolder;

/**
 * Created by nicolasboueme on 31/05/2016.
 */
public class VideoHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    private ImageView videoThumbnail;
    private TextView videoName;
    private TextView videoDescription;

    public VideoHolder(View itemView) {
        super(itemView);

        videoThumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);
        videoName = (TextView) itemView.findViewById(R.id.video_name);
        videoDescription = (TextView) itemView.findViewById(R.id.video_description);
    }

    public void bind(Video currentVideo) {
        videoName.setText(currentVideo.getName());
        videoDescription.setText(currentVideo.getDescription());

        Glide.with(itemView.getContext()).load(currentVideo.getImageUrl()).into(videoThumbnail);
    }

    @Override
    public void onItemSelected() {
        //itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        //itemView.setBackgroundColor(0);
    }
}
