package com.example.ultim.newtodolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.ultim.newtodolist.DataBase.DatabaseAdapter;
import com.example.ultim.newtodolist.DataBase.TodoTask;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ultim on 14.04.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter{
    private List<TodoTask> mListTask;
    private Context mContext;
    private int mSelectPosition;

    public RecyclerAdapter(List<TodoTask> mListTask, Context mContext) {
        this.mListTask = mListTask;
        this.mContext = mContext;
        mSelectPosition = -1;
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        TextView mTextText;
        TextView mTextTitle;
        TextView mTextDate;
        CardView cardView1;
        CheckBox mCheckBox;

        public ViewHolder1(View itemView) {
            super(itemView);
            mTextText = (TextView) itemView.findViewById(R.id.info_text);
            mTextTitle = (TextView) itemView.findViewById(R.id.title_text);
            mTextDate = (TextView) itemView.findViewById(R.id.date_text);
            cardView1 = (CardView) itemView.findViewById(R.id.card_view);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox1);
        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView mTextText;
        TextView mTextTitle;
        TextView mTextDate;
        Button mEditBtn;
        Button mDeleteBtn;
        CardView cardView2;
        CheckBox mCheckBox;

        public ViewHolder2(View itemView) {
            super(itemView);
            mTextText = (TextView) itemView.findViewById(R.id.info_text);
            mTextTitle = (TextView) itemView.findViewById(R.id.title_text);
            mTextDate = (TextView) itemView.findViewById(R.id.date_text);
            mEditBtn = (Button) itemView.findViewById(R.id.button_edit);
            mDeleteBtn = (Button) itemView.findViewById(R.id.button_delete);
            cardView2 = (CardView) itemView.findViewById(R.id.card_view2);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkBox2);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mSelectPosition){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item2, parent, false);
                return new ViewHolder2(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item1, parent, false);
                return new ViewHolder1(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final TodoTask object = mListTask.get(position);
        if (object != null) {
            if (mSelectPosition != position) {
                ((ViewHolder1) holder).mTextTitle.setText(object.getTitle());
                ((ViewHolder1) holder).mTextText.setText(object.getText());
                ((ViewHolder1) holder).mTextDate.setText(object.getDate());
                ((ViewHolder1) holder).cardView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseAdapter adapter = null;
                        try {
                            adapter = new DatabaseAdapter(mContext);
                            adapter.open();
                            mSelectPosition = position;
                            mListTask = adapter.getTodoTasks();
                            notifyDataSetChanged();
                        } finally {
                            if (adapter != null){
                                adapter.close();
                            }
                        }
                    }
                });

                ((ViewHolder1) holder).mCheckBox.setOnCheckedChangeListener(null);
                ((ViewHolder1) holder).mCheckBox.setChecked(isDone(object.isDone()));
                ((ViewHolder1) holder).mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        DatabaseAdapter adapter = null;
                        try {
                            adapter = new DatabaseAdapter(mContext);
                            adapter.open();
                            TodoTask newObject = changeDone(object);
                            adapter.update(newObject);
                            //mListTask = adapter.getTodoTasks();
                            //notifyDataSetChanged();
                        } finally {
                            if (adapter != null){
                                adapter.close();
                            }
                        }

                    }
                });
            }
            else {
                ((ViewHolder2) holder).mTextTitle.setText(object.getTitle());
                ((ViewHolder2) holder).mTextText.setText(object.getText());
                ((ViewHolder2) holder).mTextDate.setText(object.getDate());
                ((ViewHolder2) holder).cardView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseAdapter adapter = null;
                        try {
                            adapter = new DatabaseAdapter(mContext);
                            adapter.open();
                            mSelectPosition = -1;
                            mListTask = adapter.getTodoTasks();
                            notifyDataSetChanged();
                        } finally {
                            if (adapter != null){
                                adapter.close();
                            }
                        }
                    }
                });
                ((ViewHolder2) holder).mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseAdapter adapter = null;
                        try {
                            adapter = new DatabaseAdapter(mContext);
                            adapter.open();
                            adapter.delete(object.getId());
                            mSelectPosition = -1;
                            mListTask.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                        } finally {
                            if (adapter != null){
                                adapter.close();
                            }
                        }

                    }
                });
                ((ViewHolder2) holder).mEditBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(mContext, EditActivity.class);
                        myIntent.putExtra("id", object.getId());
                        mContext.startActivity(myIntent);
                        notifyItemChanged(position);
                    }
                });
                ((ViewHolder2) holder).mCheckBox.setOnCheckedChangeListener(null);
                ((ViewHolder2) holder).mCheckBox.setChecked(isDone(object.isDone()));
                ((ViewHolder2) holder).mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        DatabaseAdapter adapter = null;
                        try{
                            adapter = new DatabaseAdapter(mContext);
                            adapter.open();
                            TodoTask newObject = changeDone(object);
                            adapter.update(newObject);
                           // mListTask = adapter.getTodoTasks();
                            mSelectPosition = -1;
                            //notifyDataSetChanged();
                        } finally {
                            if (adapter != null){
                                adapter.close();
                            }
                        }

                    }
                });
            }
        }
    }


    @Override
    public int getItemCount() {
        return mListTask.size();
    }

    private boolean isDone(int value){
        return value == 1;
    }

    private TodoTask changeDone(TodoTask object){
        int intDone;
        if (object.isDone() == 1){
            intDone = 0;
        } else {
            intDone = 1;
        }
        TodoTask todoTask = new TodoTask(object.getId(), object.getTitle(), object.getText(),
                object.getDate(), object.getPriority(), intDone);
        return todoTask;
    }
}
