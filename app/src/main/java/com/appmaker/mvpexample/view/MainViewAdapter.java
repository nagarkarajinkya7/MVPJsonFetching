package com.appmaker.mvpexample.view;

import android.animation.LayoutTransition;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmaker.mvpexample.R;
import com.appmaker.mvpexample.model.MainViewDataModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.ViewHolder> {

    private Context mContext;
    private List<MainViewDataModel> model;
    private LayoutInflater inflater;

    public MainViewAdapter(Context mContext, List<MainViewDataModel> model) {
        this.mContext = mContext;
        this.model = model;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View mView = inflater.inflate(R.layout.row_view, viewGroup, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        MainViewDataModel currentData = model.get(i);

        viewHolder.tv_name.setText(currentData.getName());
        viewHolder.tv_country.setText(currentData.getCountry());
        viewHolder.tv_city.setText(currentData.getCity());

        Glide.with(mContext).load(currentData.getImageUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.image_view);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_country, tv_city;
        ImageView image_view;

        public ViewHolder(View viewItem) {
            super(viewItem);

            tv_name = viewItem.findViewById(R.id.tv_name);
            tv_country = viewItem.findViewById(R.id.tv_country);
            tv_city = viewItem.findViewById(R.id.tv_city);
            image_view = viewItem.findViewById(R.id.imageView);
        }
    }
}
