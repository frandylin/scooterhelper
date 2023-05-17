
package com.example.scooterhelper0608;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class OilActivity extends AppCompatActivity {
    EditText Oil_et,Km_et,Price_et;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil);
        // 取得 View 物件
        Oil_et = findViewById(R.id.oil_et);
        Km_et = findViewById(R.id.km_et);
        Price_et = findViewById(R.id.price_et);
        btnStart = findViewById(R.id.btnStart);
        // 設置監聽器
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 取得EditText中資料
                double Oil = Double.parseDouble(Oil_et.getText().toString());
                double Km = Double.parseDouble(Km_et.getText().toString());
                double Price = Double.parseDouble(Price_et.getText().toString());
                // 計算油耗
                double km_oil = Km/Oil;
                double oil_km = Oil/Km*100;

                // 顯示出油耗
                Toast.makeText(OilActivity.this,"每公升可行駛:"+String.valueOf(km_oil)+"公里",Toast.LENGTH_LONG).show();
                Toast.makeText(OilActivity.this,"油耗:"+String.valueOf(oil_km)+"L/100Km",Toast.LENGTH_LONG).show();


            }
        });
    }
}