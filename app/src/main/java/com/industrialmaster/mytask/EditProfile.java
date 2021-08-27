package com.industrialmaster.mytask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditProfile extends AppCompatActivity {
    private ImageButton btnProfilePic;
    private EditText name,address,mobile,email,password;


    @Override
    protected void onResume() {
        super.onResume();
        name=findViewById(R.id.etNameEdit);
        address=findViewById(R.id.etAddressEdit);
        mobile=findViewById(R.id.etContactEdit);
        email=findViewById(R.id.etEmailEdit);
        password=findViewById(R.id.etPasswordEdit);
        btnProfilePic = findViewById(R.id.addProfileImageEdit);

        btnProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(openCamera,100);
            }
        });



    }

    private String pro_name;
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


    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name=findViewById(R.id.etNameEdit);
        address=findViewById(R.id.etAddressEdit);
        mobile=findViewById(R.id.etContactEdit);
        email=findViewById(R.id.etEmailEdit);
        password=findViewById(R.id.etPasswordEdit);


        btnProfilePic = findViewById(R.id.addProfileImageEdit);


        DataBase th=new DataBase(this);
        SQLiteDatabase db=th.getReadableDatabase();

        String sql="SELECT _id,name,address,contact_number,email,password,profile_image_path FROM person";
        Cursor cursor=db.rawQuery(sql,null);


        try {

            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                id=cursor.getInt(0);
                String nameOfProfile=cursor.getString(1);

                address.setText(cursor.getString(2));
                mobile.setText(cursor.getString(3));
                email.setText(cursor.getString(4));
                password.setText(cursor.getString(5));
                String imagePath = cursor.getString(6);

                name.setText(nameOfProfile);

                File f=new File(imagePath,nameOfProfile);
                Bitmap image=null;

                image = BitmapFactory.decodeStream(new FileInputStream(f));
                btnProfilePic.setImageBitmap(image);



                cursor.moveToNext();
            }

        }
        catch (Exception e){

            e.printStackTrace();
        }


    }

    public void addProfilePic(View view) {
    }

    public void edit(View view) {
        name=findViewById(R.id.etNameEdit);
        address=findViewById(R.id.etAddressEdit);
        mobile=findViewById(R.id.etContactEdit);
        email=findViewById(R.id.etEmailEdit);
        password=findViewById(R.id.etPasswordEdit);
        btnProfilePic = findViewById(R.id.addProfileImageEdit);

        name.setText("");

        address.setText("");
        mobile.setText("");
        email.setText("");
        password.setText("");
        btnProfilePic.setImageBitmap(null);

    }

    private String profilePicPath;
    public void saveAll(View view) {

        EditText et_Name=findViewById(R.id.etNameEdit);
        EditText et_Address=findViewById(R.id.etAddressEdit);
        EditText et_Contact=findViewById(R.id.etContactEdit);
        EditText et_Email=findViewById(R.id.etEmailEdit);
        EditText et_Password=findViewById(R.id.etPasswordEdit);

        pro_name=et_Name.getText().toString();

        String name=et_Name.getText().toString();
        String address=et_Address.getText().toString();
        String contact=et_Contact.getText().toString();
        String email=et_Email.getText().toString();
        String password=et_Password.getText().toString();


        profilePicPath=saveToInternalStorage(imageAsBitmap);

        DataBase th=new DataBase(this);
        SQLiteDatabase db=th.getWritableDatabase();
        String sql1="DELETE FROM person WHERE _id == '"+id+"'";
        db.execSQL(sql1);

        String sql="INSERT INTO person(name,address,contact_number,email,password,profile_image_path) VALUES('"+name+"','"+address+"','"+contact+"','"+email+"','"+password+"','"+profilePicPath+"')";
        db.execSQL(sql);

        Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_LONG).show();
    }
}