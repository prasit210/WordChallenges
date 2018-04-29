package com.example.prasi.wordchallenges.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prasi.wordchallenges.R;
import com.example.prasi.wordchallenges.manager.firestore.FirestoreTransModel;

import java.util.List;

public class RecyclerAddwordTodayAdapter extends RecyclerView.Adapter<RecyclerAddwordTodayAdapter.ViewHolder> {

    private List<FirestoreTransModel> mModel;
    private Context mContext;

    public RecyclerAddwordTodayAdapter(Context mContext,List<FirestoreTransModel> mModel) {
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
        FirestoreTransModel firestoreTransModel = mModel.get(position);
        //Bind Widget
        holder.txtShow2.setText(firestoreTransModel.getEng().toString());
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtShow1,txtShow2,txtShow3;

        public ViewHolder(View view){
            super(view);

            //txtShow1 = (TextView)view.findViewById(R.id.txt1);
            txtShow2 = (TextView)view.findViewById(R.id.txt2);
            //txtShow3 = (TextView)view.findViewById(R.id.txt3);

        }
    }
}
