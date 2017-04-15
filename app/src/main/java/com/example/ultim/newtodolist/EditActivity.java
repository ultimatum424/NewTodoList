package com.example.ultim.newtodolist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ultim.newtodolist.DataBase.DatabaseAdapter;
import com.example.ultim.newtodolist.DataBase.DatabaseHelper;
import com.example.ultim.newtodolist.DataBase.TodoTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    EditText mEditTitle;
    EditText mEditText;
    EditText mEditDate;
    Calendar mCalendar;
    Button mBtnSave;
    RadioGroup mPriorityGroup;
    RadioButton smallPriority;
    RadioButton mediumPriority;
    RadioButton highPriority;
    CheckBox mCheckBoxIsDone;
    int mPriority = 0;
    long id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mEditTitle = (EditText) findViewById(R.id.input_title);
        mEditText = (EditText) findViewById(R.id.input_text);
        mEditDate = (EditText) findViewById(R.id.input_date);
        mCheckBoxIsDone = (CheckBox) findViewById(R.id.checkBoxIsDone);
        mPriorityGroup = (RadioGroup) findViewById(R.id.radioButtonGroup);
        mPriorityGroup.setOnCheckedChangeListener(this);
        smallPriority = (RadioButton) findViewById(R.id.radioButtonSmall);
        mediumPriority = (RadioButton) findViewById(R.id.radioButtonMedium);
        highPriority = (RadioButton) findViewById(R.id.radioButtonHight);
        mEditDate.setOnClickListener(this);
        mCalendar = Calendar.getInstance();
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mBtnSave.setOnClickListener(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        fillingTodo();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd MMM yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            mEditDate.setText(sdf.format(mCalendar.getTime()));

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.input_date:
                new DatePickerDialog(EditActivity.this, date, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                return;
            case R.id.btn_save:
                saveChanges();
                finish();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.radioButtonHight:
                mPriority = 3;
                break;
            case R.id.radioButtonMedium:
                mPriority = 2;
                break;
            case R.id.radioButtonSmall:
                mPriority = 1;
                break;
            default:
                break;
        }
    }

    void fillingTodo(){
        if (id != 0 ){
            DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
            databaseAdapter.open();
            TodoTask todoTask = databaseAdapter.getTodoTask(id);
            databaseAdapter.close();
            mEditTitle.setText(todoTask.getText());
            mEditText.setText(todoTask.getText());
            String myFormat = "dd MMM yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            mEditDate.setText(sdf.format(todoTask.getDate()));
            if (todoTask.isDone() == 0){
                mCheckBoxIsDone.setChecked(false);
            }else {
                mCheckBoxIsDone.setChecked(true);
            }
            switch (todoTask.getPriority()){
                case 1:
                    mPriorityGroup.check(smallPriority.getId());
                    break;
                case 2:
                    mPriorityGroup.check(mediumPriority.getId());
                    break;
                case 3:
                    mPriorityGroup.check(highPriority.getId());
                    break;
                default:
                    break;
            }

        }
    }
    void saveChanges(){
        int isDone = 0;
        if ( mCheckBoxIsDone.isChecked()){
            isDone = 1;
        }else{
            isDone = 0;
        }
        TodoTask todoTask = new TodoTask(
                id,
                mEditTitle.getText().toString(),
                mEditText.getText().toString(),
                mEditDate.getText().toString(),
                mPriority,
                isDone);
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        databaseAdapter.open();
        if (id != 0){
            databaseAdapter.update(todoTask);
        }else {
            databaseAdapter.insert(todoTask);
        }
        databaseAdapter.close();
    }
}
