package com.industrialmaster.mytask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {

        EditText et_Name_B=findViewById(R.id.etName);
        EditText et_Password=findViewById(R.id.etPassword);
        String name=et_Name_B.getText().toString();
        String password=et_Password.getText().toString();

        DataBase th=new DataBase(this);
        SQLiteDatabase db=th.getReadableDatabase();

        String sql="SELECT name,password FROM person WHERE name=='"+name+"'";
        try{




            Cursor cursor = db.rawQuery(sql, null);

            cursor.moveToFirst();
            String password_db = cursor.getString(1);


            if(!cursor.isNull(0) && !cursor.isNull(1)){





                if(password_db.equals(password))
                {
                    Toast.makeText(this, "Password Correct", Toast.LENGTH_LONG).show();

                    Intent gotomain=new Intent(this,MainActivity.class);
                    startActivity(gotomain);
                }
                else {


                    Toast.makeText(this, "Invalid  Password try Again", Toast.LENGTH_LONG).show();

                    et_Password.setText("");

                }
            }



            cursor.close();

        }catch (Exception e){



            Toast.makeText(this, "There is No Such a Account Registered in The Application", Toast.LENGTH_LONG).show();
            et_Name_B.setText("");
            et_Password.setText("");

        }

    }

    public void register(View view) {

        Intent gotoregister=new Intent(this,Registration.class);
        startActivity(gotoregister);
    }
}