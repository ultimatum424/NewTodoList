package com.example.ultim.newtodolist;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextText;
        TextView mTextTitle;
        TextView buttonViewOption;


        public ViewHolder(View itemView) {
            super(itemView);
            mTextText = (TextView) itemView.findViewById(R.id.info_text);
            mTextTitle = (TextView) itemView.findViewById(R.id.title_text);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
        }
    }

    public RecyclerAdapter(List<TodoTask> listAdapter, Context mContext)
    {
        mListTask = listAdapter;
        this.mContext = mContext;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextText.setText(mListTask.get(position).getTitle());
        holder.mTextTitle.setText(mListTask.get(position).getText());

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.buttonViewOption);
                popupMenu.inflate(R.menu.options_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuEdit:
                                //handle menu1 click
                                break;
                            case R.id.menuDelete:
                                //handle menu2 click
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListTask.size();
    }
}
