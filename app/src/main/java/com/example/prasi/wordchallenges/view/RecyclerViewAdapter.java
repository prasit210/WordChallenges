package com.example.prasi.wordchallenges.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import com.example.prasi.wordchallenges.R;
import com.example.prasi.wordchallenges.manager.firestore.FirestoreTransModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<FirestoreTransModel> mModel;
    private Context mContext;

    public RecyclerViewAdapter(List<FirestoreTransModel> mModel, Context mContext) {
        this.mModel = mModel;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Bind Widget
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        public ViewHolder(View view){
            super(view);

        }
    }
}
