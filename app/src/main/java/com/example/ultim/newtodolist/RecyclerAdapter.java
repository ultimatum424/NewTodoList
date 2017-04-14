package com.example.ultim.newtodolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ultim.newtodolist.DataBase.TodoTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ultim on 14.04.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private List<TodoTask> mListTask;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextText;
        TextView mTextTitle;



        public ViewHolder(View itemView) {
            super(itemView);
            mTextText = (TextView) itemView.findViewById(R.id.info_text);
            mTextTitle = (TextView) itemView.findViewById(R.id.title_text);
        }
    }

    public RecyclerAdapter(List<TodoTask> listAdapter)
    {
        mListTask = listAdapter;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextText.setText(mListTask.get(position).getTitle());
        holder.mTextTitle.setText(mListTask.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mListTask.size();
    }
}
