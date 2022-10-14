package com.example.cobauts;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        more = findViewById(R.id.more);
    }

    public void pindah(View view){
        Intent intent = new Intent(SecondActivity.this, CariBeritaActivity.class);
        startActivity(intent);
    }

//    public void beritaShow(View view){
//        final BottomSheetDialog bsd = new BottomSheetDialog(
//          SecondActivity.this, R.style.BottomSheetDialogTheme
//        );
//        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
//                R.layout.layout_bottom_sheet, findViewById(R.id.bottomSheetContainer)
//        );
//
//        spinner2 = bottomSheetView.findViewById(R.id.spn1);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kategori,
//                android.R.layout.simple_spinner_item);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        if (spinner2 != null) {
//            spinner2.setAdapter(adapter);
//        }
//
//
//
//        bottomSheetView.findViewById(R.id.datePicker).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment newFragment = new DatePickerFragment();
//                newFragment.show(getSupportFragmentManager(), getString(R.string.date_picker));
//                bsd.dismiss();
//            }
//
//        });
//
//        bottomSheetView.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(SecondActivity.this,  "Coba", Toast.LENGTH_SHORT).show();
//                Bundle bundle = new Bundle();
//                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
//                String message = spinner.getSelectedItem().toString();
//                int message2 = umur;
//                bundle.putString("kodeKategori", message);
//                bundle.putInt("kodeUmur", message2);
////                view.getContext().startActivity(intent);
//                startActivityForResult(intent, 1);
//            }
//        });
//        bsd.setContentView(bottomSheetView);
//        bsd.show();
//    }


}