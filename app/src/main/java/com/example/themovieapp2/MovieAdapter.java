package com.example.themovieapp2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.w3c.dom.Text;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context mContext;
    private List<MovieResults.Result> mData;

    public MovieAdapter(Context mContext, List<MovieResults.Result> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.movie_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.title.setText(mData.get(position).getTitle());
            holder.overview.setText(mData.get(position).getOverview());
            holder.rating.setText(String.valueOf((long)(mData.get(position).getVoteAverage())));
            Glide.with(mContext)
                    .load("https://image.tmdb.org/t/p/original"+(mData.get(position).getPosterPath()))
                    .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView overview;
        ImageView img;
        TextView rating;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Title);
            overview = itemView.findViewById(R.id.Description);
            img = itemView.findViewById(R.id.Poster);
            rating = itemView.findViewById(R.id.rating);
        }
    }

}
