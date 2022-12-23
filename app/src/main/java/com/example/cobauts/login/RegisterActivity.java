package com.example.cobauts.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cobauts.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText myEmail, myPassword;
    private Button regButtton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private String getEmail, getPassword;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myEmail = findViewById(R.id.regEmail);
        myPassword = findViewById(R.id.regPassword);
        regButtton = findViewById(R.id.regUser);
        auth = FirebaseAuth.getInstance();

        myPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        regButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekDataUser();
            }
        });
    }

    private void cekDataUser(){
        getEmail = Objects.requireNonNull(myEmail.getText()).toString();
        getPassword = Objects.requireNonNull(myPassword.getText()).toString();

        if(TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPassword)){
            Toast.makeText(this, "Email atau Sandi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else{
            if(getPassword.length() < 6){
                Toast.makeText(this, "Sandi Terlalu Pendek, Minimal 6 Karakter", Toast.LENGTH_SHORT).show();
            }else {
                createUserAccount();
            }
        }
    }

    private void createUserAccount(){
        auth.createUserWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(RegisterActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Terjadi Kesalahan, Silakan Coba Lagi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

    }

}