package edu.binghamton.cs.cs441_a5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Integer> mData;
    private LayoutInflater mInflater;
    //private ItemClickListener mClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    MyAdapter(Context context, List<Integer> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView view = (TextView) mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        String num = Integer.toString(mData.get(position));
        holder.textView.setText(num);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}