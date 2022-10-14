package com.example.cobauts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class CariBeritaActivity extends AppCompatActivity {

    Button btnSubmit;
    Spinner spinner;
    TextView tvHasilUsia, datePicker;
    int umur;
    public static final String MESSAGE_EXTRA = "MESSAGE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_berita);

        btnSubmit = findViewById(R.id.btnSubmit);
        spinner = findViewById(R.id.spn1);
        tvHasilUsia = findViewById(R.id.tvHasilUsia);
        datePicker = findViewById(R.id.datePicker);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kategori,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
    }

    public void showDatePickerDialog1(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.date_picker));
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);

        String dateMessage = month_string + "-" + day_string + "-" + year_string;
        datePicker.setText(dateMessage);

        umur = (2022 - year);
        String totalUmur = Integer.toString(umur);
        tvHasilUsia.setText(totalUmur);
    }


    public void cariBerita(View view){
        Intent intent = new Intent(CariBeritaActivity.this, ThirdActivity.class);
        String message = spinner.getSelectedItem().toString();
        int message2 = umur;
        intent.putExtra(MESSAGE_EXTRA, message);
        intent.putExtra("kodeUmur",message2);
        startActivityForResult(intent, 1);
    }

}