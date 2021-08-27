package com.industrialmaster.mytask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddTasks extends AppCompatActivity {

    private TextView Date;
    private TextView time;
    private DatePickerDialog datePickerDialog;
    private  int hour,minutes;

    java.util.Date date1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);
        Date= findViewById(R.id.tv_date);

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                datePickerDialog = new DatePickerDialog(AddTasks.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                            date1=dateFormat.parse(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);


                            String date=dateFormat.format(date1);

                            Date.setText(date);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        time=findViewById(R.id.tv_time);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog =new TimePickerDialog(AddTasks.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour=hourOfDay;
                        minutes=minute;

                        String time2=hour+":"+minutes;

                        SimpleDateFormat formatTime=new SimpleDateFormat("hh:mm");
                        try {

                            java.util.Date date=formatTime.parse(time2);
                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(
                                    "hh:mm aaa"
                            );




                            String timeString=simpleDateFormat.format(date );
                            time.setText(timeString);

                        }catch (Exception e){
                            e.printStackTrace();


                        }



                    }
                }, 12, 0, false);
                timePickerDialog.updateTime(hour,minutes);
                timePickerDialog.show();

            }
        });




    }



    public void clearAll(View view) {
        etTitle=findViewById(R.id.et_title);
        etDescription=findViewById(R.id.et_description);
        Date=findViewById(R.id.tv_date);
        time=findViewById(R.id.tv_time);



        try {

            etTitle.setText("");
            etDescription.setText("");
            Date.setText("");
            time.setText("");

        }catch (Exception e){

            e.printStackTrace();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private EditText etTitle;
    private EditText etDescription;
    public void saveAll(View view) {

        try {
                etTitle=findViewById(R.id.et_title);
                String title =etTitle.getText().toString();

                etDescription=findViewById(R.id.et_description);
                String description=etDescription.getText().toString();

                String dateS=Date.getText().toString();


                time=findViewById(R.id.tv_time);
                String timeS=time.getText().toString();


                DataBase th = new DataBase(this);
                SQLiteDatabase db = th.getWritableDatabase();

                String sql = "INSERT INTO tasks(title,date,time,description) VALUES('" +title+ "','" + dateS + "','" + timeS+ "','" + description + "')";
                db.execSQL(sql);

                Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_LONG).show();
        // Toast.makeText(this, Emp_name_from_attendance_activity+state+att_date, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }





    }
}