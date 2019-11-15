package com.example.myapplication;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {
    private Context context;
    private List<Cats> notes;
    public CatAdapter(Context context, List<Cats> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cat, parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Cats cat = notes.get(position);
        holder.mTvName.setText(cat.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.setOnItemClickListener(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTvName;
        public ViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.name);
        }
    }
    private ItemClickListener listener;
    public void setOnItemClickListener(ItemClickListener listener ){
        this.listener = listener;
    }
    public interface ItemClickListener{
        void setOnItemClickListener(int position);
    }
}
