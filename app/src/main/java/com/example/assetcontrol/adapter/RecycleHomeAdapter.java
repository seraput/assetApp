package com.example.assetcontrol.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assetcontrol.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleHomeAdapter extends RecyclerView.Adapter<RecycleHomeAdapter.ViewHolder> {

    private static final String TAG = "RecycleHomeAdapter";

    private ArrayList<String> mDesc = new ArrayList<>();
    private ArrayList<String> mImage = new ArrayList<>();
    private Context mContext;

    public RecycleHomeAdapter(Context context, ArrayList<String> description, ArrayList<String> imageUrl){
        mDesc = description;
        mImage = imageUrl;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_home_menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onCreateViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImage.get(position))
                .into(holder.imageView);
        holder.desc.setText(mDesc.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick :Click on Image: " +mDesc.get(position));
                Toast.makeText(mContext, mDesc.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imageView;
        TextView desc;

        public ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.imageY);
            desc = view.findViewById(R.id.descY);
        }
    }
}
