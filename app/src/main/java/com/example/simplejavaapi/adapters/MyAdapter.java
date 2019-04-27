package com.example.simplejavaapi.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.simplejavaapi.R;
import com.example.simplejavaapi.models.UnsplashImage;
import com.example.simplejavaapi.utils.OnBottomReachListener;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<UnsplashImage> list;
    private Context context;
    private OnBottomReachListener onBottomReachListener;

    public MyAdapter(Context context, List<UnsplashImage> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnBottomReachListener(OnBottomReachListener onBottomReachListener){
        this.onBottomReachListener = onBottomReachListener;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        String imageUrl = list.get(position).getUrls().getThumb();
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(holder.iv_photo);
        holder.tv_photographer.setText(list.get(position).getUser().getUsername());

        holder.iv_photo.setOnClickListener(v -> Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show());

//        if(position == list.size() -1){
//            onBottomReachListener.onBottomReached(position);
//        }
        Log.d("POSITION DEBUG", String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_photo;
        TextView tv_photographer;

        public ViewHolder(View v) {
            super(v);
            iv_photo = v.findViewById(R.id.iv_photo);
            tv_photographer = v.findViewById(R.id.tv_photographer);
        }
    }
}
