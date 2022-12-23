package com.example.cobauts.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cobauts.R;
import com.example.cobauts.RV.DAONews;
import com.example.cobauts.data.News;
import com.example.cobauts.data.Sport;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AddNewsActivity extends AppCompatActivity {

    private TextInputEditText etJudul, etIsi;
    private Button btnAdd;
    DatabaseReference mDRNews;
    News mNews;
    String key;
    RadioGroup rdg;
    Spinner spinner;
    RadioButton rdb;
    TextView datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        etJudul = findViewById(R.id.getJudul);
        etIsi = findViewById(R.id.getIsi);
        btnAdd = findViewById(R.id.btnAdd);
        spinner = findViewById(R.id.spnAddNews);
        rdg = findViewById(R.id.radioGroup);
        mDRNews = FirebaseDatabase.getInstance().getReference(News.class.getSimpleName());
        datePicker = findViewById(R.id.datePicker);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kategori, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String spnKategori = spinner.getSelectedItem().toString();

        DAONews dao = new DAONews();
        News news_edit = (News) getIntent().getSerializableExtra("Edit");
        if(news_edit != null){
            btnAdd.setText("Update");
            etJudul.setText(news_edit.getTitle());
            etIsi.setText(news_edit.getDesc());
        } else {
            btnAdd.setText("Submit");
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = rdg.getCheckedRadioButtonId();
                rdb = findViewById(selectedId);

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

                News news = new News(etJudul.getText().toString(), etIsi.getText().toString(),
                        spinner.getSelectedItem().toString(),rdb.getText().toString(),df.format(c));
                if (news_edit == null){
                    dao.add(news).addOnSuccessListener(suc ->{
                        Toast.makeText(AddNewsActivity.this, "Data has been pushed", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(er -> {
                        Toast.makeText(AddNewsActivity.this, er.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("title", etJudul.getText().toString());
                    hashMap.put("desc", etIsi.getText().toString());
                    dao.update(news_edit.getKey(), hashMap).addOnSuccessListener(suc -> {
                        Toast.makeText(AddNewsActivity.this, "Data has been updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }).addOnFailureListener(er -> {
                        Toast.makeText(AddNewsActivity.this, er.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }
}