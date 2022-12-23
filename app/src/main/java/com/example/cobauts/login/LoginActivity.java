package com.example.cobauts.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.cobauts.activity.MainActivity;
import com.example.cobauts.R;
import com.example.cobauts.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button Login;
    private TextInputEditText myEmail, myPassword;
    private ProgressBar progressBar;
    private CardView btnEmail;
    private TextView btnUsername;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener listener;
    private String getEmail, getPassword;
    public static SharedPrefManager sharedPrefManager;
    static SharedPreferences mSharedPref;
    private final String sharedPrefFile = "com.example.cobauts";
    static String KEY = "key";
    private Boolean mLogin;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

// implements View.OnClickListener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myEmail = findViewById(R.id.getEmail);
        myPassword = findViewById(R.id.getPassword);
        btnEmail = findViewById(R.id.btnEmail);
//        Register.setOnClickListener(this);
        Login = findViewById(R.id.login);
//        Login.setOnClickListener(this);
        sharedPrefManager = new SharedPrefManager(this);
        btnUsername = findViewById(R.id.btnUsername);

        mSharedPref = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mLogin = mSharedPref.getBoolean(KEY, false);

        if(mLogin){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        myPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

//        auth = FirebaseAuth.getInstance();
//        listener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if(user != null){
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
//                }
//            }
//        };

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUsername();
            }
        });

        btnUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterUsernameActivity.class));
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });
    }

    private void loginUsername(){

        String email = myEmail.getText().toString();
        String password = myPassword.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Email atau Sandi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        } else {
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(email)){
                        mLogin = true;
                        SharedPreferences.Editor editor = mSharedPref.edit();
                        editor.putBoolean(KEY, true);
                        editor.apply();
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



//    //Menerapkan Listener
//    @Override
//    protected void onStart() {
//        super.onStart();
////        auth.addAuthStateListener(listener);
//        FirebaseUser currentUser = auth.getCurrentUser();
//        if(currentUser != null){
//            currentUser.reload();
//        }
//    }
//
//    //Melepaskan Litener
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(listener != null){
//            auth.removeAuthStateListener(listener);
//        }
//    }
//
//    private void loginUserAccount(){
//        auth.signInWithEmailAndPassword(getEmail, getPassword)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            mLogin = true;
//                            SharedPreferences.Editor editor = mSharedPref.edit();
//                            editor.putBoolean(KEY, true);
//                            editor.apply();
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(LoginActivity.this, "Tidak Dapat Masuk, Silakan Coba Lagi", Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                });
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.register:
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//                break;
//
//            case R.id.login:
//                getEmail = myEmail.getText().toString();
//                getPassword = myPassword.getText().toString();
//
//                if(TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPassword)){
//                    Toast.makeText(this, "Email atau Sandi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
//                }else{
//                    loginUserAccount();
//                    progressBar.setVisibility(View.VISIBLE);
//                }
//                break;
//        }
//
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
//    }
}