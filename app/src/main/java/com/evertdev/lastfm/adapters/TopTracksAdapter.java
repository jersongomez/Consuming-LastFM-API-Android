package com.evertdev.lastfm.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.evertdev.lastfm.R;
import com.evertdev.lastfm.models.Track;
import com.evertdev.lastfm.common.DurationConverter;
import com.evertdev.lastfm.common.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evert Dev.
 */

public class TopTracksAdapter extends RecyclerView.Adapter<TopTracksAdapter.ViewHolder> {
    List<Track> mDataset;
    Context mContext;
    View.OnClickListener mOnItemClickListener;

    public TopTracksAdapter(List<Track> items, Context context, View.OnClickListener onClickListener) {
        this.mDataset = items;
        this.mContext = context;
        this.mOnItemClickListener = onClickListener;
    }

    @Override
    public TopTracksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item, parent, false);
        return new TopTracksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Track item = mDataset.get(position);
        ImageLoader.loadImage(mContext, item.getImageUrl(), R.drawable.default_track, holder.trackImageView);
        holder.nameTextView.setText(item.getName());
        holder.artistTextView.setText(item.getArtist().getName());
        holder.playCountTextView.setText(item.getPlaycount());
        holder.durationTextView.setText(DurationConverter.getDurationInMinutesText(Long.parseLong(item.getDuration())));
    }

    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size();
        }
        return 0;
    }

    public void setDataset(List<Track> items) {
        this.mDataset = items;
        notifyDataSetChanged();
    }

    public Track getItemAt(int position) {
        return mDataset.get(position);
    }

    public void clearDataset() {
        if (mDataset != null) {
            mDataset.clear();
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_track)
        ImageView trackImageView;
        @BindView(R.id.txt_track_name)
        TextView nameTextView;
        @BindView(R.id.txt_plays)
        TextView playCountTextView;
        @BindView(R.id.txt_track_artist)
        TextView artistTextView;
        @BindView(R.id.txt_duration)
        TextView durationTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cv_track_item)
        void onTrackClicked(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(view);
            }
        }
    }
}
