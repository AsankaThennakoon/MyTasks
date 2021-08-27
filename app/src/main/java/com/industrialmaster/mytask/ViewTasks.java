package com.industrialmaster.mytask;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ViewTasks extends AppCompatActivity {


    private ImageButton imageDeleteButton;
    private int taskId;
    private TextView emptyBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);



        DataBase th=new DataBase(this);
        SQLiteDatabase db=th.getReadableDatabase();


        String sql="SELECT title,date,time,description,_id FROM tasks";
        Cursor cursor=db.rawQuery(sql,null);


        String[] columns={"title","date","time","description","_id"};
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
            cursor.getString(0);
            cursor.getString(1);
            cursor.getString(2);
            cursor.getString(3);
            cursor.getString(4);


            cursor.moveToNext();
        }

        ListView lv=findViewById(R.id.task_list);


        int layout=R.layout.one_task;


        int[] labels={R.id.task_name,R.id.task_date,R.id.task_time,R.id.task_description,R.id.task_id};


        SimpleCursorAdapter adapter=new SimpleCursorAdapter(this,layout,cursor,columns,labels);

        lv.setAdapter(adapter);

    }


    @Override
    protected void onResume() {
        super.onResume();




    }

    public void deleteTasks(int id){


        emptyBox=findViewById(R.id.task_id);
        taskId=emptyBox.getId();
        imageDeleteButton=findViewById(R.id.deleteTasks);

        imageDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTasks(taskId);

            }
        });
        DataBase th=new DataBase(this);
        SQLiteDatabase db=th.getWritableDatabase();
        String sql1="DELETE FROM tasks WHERE _id == '"+id+"'";
        db.execSQL(sql1);



    }
}