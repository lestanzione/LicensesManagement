package com.stanzione.licensesmanagement.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stanzione.licensesmanagement.R;

/**
 * Created by Leandro Stanzione on 06/04/2016.
 */
public class DrawerRecyclerAdapter extends RecyclerView.Adapter<DrawerRecyclerAdapter.ViewHolder> {

    public interface OnDrawerItemListener {
        void onDrawerItemSelected(View view);
    }

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String titles[];
    private int icons[];

    private String name;
    private int profile;
    private String email;
    private OnDrawerItemListener activity;

    public DrawerRecyclerAdapter(OnDrawerItemListener activity, String titles[],int icons[],String name, String email, int profile){
        this.activity = activity;
        this.titles = titles;
        this.icons = icons;
        this.name = name;
        this.email = email;
        this.profile = profile;
    }

    @Override
    public DrawerRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_item,parent,false);
            ViewHolder vhItem = new ViewHolder(v,viewType);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onDrawerItemSelected(view);
                }
            });

            return vhItem;

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType);
            return vhHeader;

        }

        return null;

    }

    @Override
    public void onBindViewHolder(DrawerRecyclerAdapter.ViewHolder holder, int position) {
        if(holder.holderId ==1) {
            holder.textView.setText(titles[position - 1]);
            holder.imageView.setImageResource(icons[position -1]);
        }
        else{
            holder.navProfileImageView.setImageResource(profile);
            holder.navNameTextView.setText(name);
            holder.navEmailTextView.setText(email);
        }
    }

    @Override
    public int getItemCount() {
        return titles.length+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        int holderId;

        TextView textView;
        ImageView imageView;
        ImageView navProfileImageView;
        TextView navNameTextView;
        TextView navEmailTextView;

        public ViewHolder(View itemView, int ViewType){
            super(itemView);

            if(ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.navItemText);
                imageView = (ImageView) itemView.findViewById(R.id.navItemIcon);
                holderId = 1;
            }
            else{
                navNameTextView = (TextView) itemView.findViewById(R.id.navHeaderNameTextView);
                navEmailTextView = (TextView) itemView.findViewById(R.id.navHeaderEmailTextView);
                navProfileImageView = (ImageView) itemView.findViewById(R.id.navHeaderImageView);
                holderId = 0;
            }
        }

    }

}