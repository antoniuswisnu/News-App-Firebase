package com.example.cobauts.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cobauts.R;
import com.example.cobauts.data.Users;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegisterUsernameActivity extends AppCompatActivity {

    private TextInputEditText myEmail, myPassword, myUsername;
    private Button btnDaftar;
    DatabaseReference mDRRegister;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://news-app-77e9c-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_username);

        myEmail = findViewById(R.id.regEmail);
        myPassword = findViewById(R.id.regPassword);
        myUsername = findViewById(R.id.regUsername);
        btnDaftar = findViewById(R.id.regUser);
        mDRRegister = FirebaseDatabase.getInstance().getReference(Users.class.getSimpleName());

        myPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRegisterUsername();
            }
        });
    }

    private void insertRegisterUsername(){
        String email = myEmail.getText().toString();
        String password = myPassword.getText().toString();
        String username = myUsername.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(RegisterUsernameActivity.this, "Isi Semua Form!", Toast.LENGTH_SHORT).show();
        } else {
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(username)){
                        Toast.makeText(RegisterUsernameActivity.this, "Username Telah Terpakai", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference.child("users").child(username).child("email").setValue(email);
                        databaseReference.child("users").child(username).child("password").setValue(password);

                        Toast.makeText(RegisterUsernameActivity.this, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    private void insertRegister(){
        String email = myEmail.getText().toString();
        String password = myPassword.getText().toString();
        String username = myUsername.getText().toString();

        Users newUsers = new Users();
        newUsers.setUsername(username);
        newUsers.setEmail(email);
        newUsers.setPassword(password);

        mDRRegister.push().setValue(newUsers);
        Toast.makeText(RegisterUsernameActivity.this, "Data has been pushed", Toast.LENGTH_SHORT).show();
    }
}