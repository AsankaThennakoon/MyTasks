package com.industrialmaster.mytask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Registration extends AppCompatActivity {
    private ImageButton btnProfilePic;

    @Override
    protected void onResume() {
        super.onResume();

        btnProfilePic = findViewById(R.id.addProfileImage);

        btnProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(openCamera,100);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }


    String pro_name;
    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory, pro_name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


    private Bitmap imageAsBitmap;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageAsBitmap=(Bitmap)data.getExtras().get("data");
        btnProfilePic.setImageBitmap(imageAsBitmap);
    }


    private String profilePicPath;

    public void REGISTER(View view) {

        EditText et_Name=findViewById(R.id.etName);
        EditText et_Address=findViewById(R.id.etAddress);
        EditText et_Contact=findViewById(R.id.etContact);
        EditText et_Email=findViewById(R.id.etEmail);
        EditText et_Password=findViewById(R.id.etPassword);

        pro_name=et_Name.getText().toString();

        String name=et_Name.getText().toString();
        String address=et_Address.getText().toString();
        String contact=et_Contact.getText().toString();
        String email=et_Email.getText().toString();
        String password=et_Password.getText().toString();


        profilePicPath=saveToInternalStorage(imageAsBitmap);

        DataBase th=new DataBase(this);
        SQLiteDatabase db=th.getWritableDatabase();

        String sql="INSERT INTO person(name,address,contact_number,email,password,profile_image_path) VALUES('"+name+"','"+address+"','"+contact+"','"+email+"','"+password+"','"+profilePicPath+"')";
        db.execSQL(sql);

        Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_LONG).show();
    }

    public void login(View view) {
        Intent gotologin=new Intent(this,Login.class);
        startActivity(gotologin);
    }

    public void addProfilePic(View view) {
    }
}