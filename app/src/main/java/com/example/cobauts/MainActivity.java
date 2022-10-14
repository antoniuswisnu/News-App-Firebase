package com.example.cobauts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        btn1 = findViewById(R.id.btn1);

    }

    public void login(View view){
        String username = "pakjoko";
        String password = "yangpentingcuan";
        if(et1.getText().toString().equals(username) && et2.getText().toString().equals(password)){
            Toast.makeText(MainActivity.this, et1.getText(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "username atau password salah", Toast.LENGTH_SHORT).show();
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Peringatan");
            alertDialog.setMessage("Username atau password yang anda masukkan salah");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Tutup",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }




}