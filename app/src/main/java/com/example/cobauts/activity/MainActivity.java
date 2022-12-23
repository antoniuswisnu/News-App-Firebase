package com.example.cobauts.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cobauts.R;
import com.example.cobauts.bookmark.BookmarkActivity;
import com.example.cobauts.bookmark.HotNewsActivity;
import com.example.cobauts.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView more, btnBerita;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    static SharedPreferences mSharedPref;
    private final String sharedPrefFile = "com.example.cobauts";
    static String KEY = "key";
    private Boolean mLogin;
    private FloatingActionButton btnAddNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        more = findViewById(R.id.more);
        btnAddNews = findViewById(R.id.btnAddNews);
        auth = FirebaseAuth.getInstance();
        mSharedPref = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mLogin = mSharedPref.getBoolean(KEY, false);
        btnBerita = findViewById(R.id.btnBerita);

//        authListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                //Jika Iya atau Null, maka akan berpindah pada halaman Login
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user == null) {
//                    SharedPreferences.Editor editor = mSharedPref.edit();
//                    editor.putBoolean(KEY, false);
//                    editor.apply();
//                    Toast.makeText(MainActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                    finish();
//                }
//            }
//        };

        btnAddNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNewsActivity.class));
            }
        });

        btnBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HotNewsActivity.class));
            }
        });

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                auth.signOut();
//            }
//        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        auth.addAuthStateListener(authListener);
//    }
//
//    //Melepaskan Litener
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(authListener != null){
//            auth.removeAuthStateListener(authListener);
//        }
//    }

    public void pindah(View view){
        Intent intent = new Intent(MainActivity.this, CariBeritaActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btnBookmark:
                startActivity(new Intent(MainActivity.this, BookmarkActivity.class));
                return true;
            case R.id.Logout:
                SharedPreferences.Editor editor = mSharedPref.edit();
                editor.putBoolean(KEY, false);
                editor.apply();
                Toast.makeText(MainActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}