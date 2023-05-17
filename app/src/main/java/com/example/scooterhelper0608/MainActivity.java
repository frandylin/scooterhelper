package com.example.scooterhelper0608;
import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class    MainActivity extends AppCompatActivity {

    TextView tvResult, tvResult2;
    Button Parking_bt , oil_bt, warning_bt, fix_bt, read_bt;
    String result1, result2;
    private Double Lat, Lng;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tvResult);
        tvResult2 = findViewById(R.id.tvResult2);


        //油耗
        oil_bt = (Button) findViewById(R.id.oil_bt);
        oil_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOilActivity();
            }
        });

       //map marker
        Parking_bt = (Button) findViewById(R.id.Parking_bt);
        Parking_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent();
                intent.setClass(MainActivity.this,MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("Lat",Lat);
                bundle.putDouble("Lag",Lng);
                intent.putExtras(bundle);
                startActivity(intent);*/
                openMapsActivity();
            }
        });

        //倒車
        warning_bt = (Button) findViewById(R.id.warning_bt);
        warning_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openWarningActivity();}
        });

        //倒車
        fix_bt = (Button) findViewById(R.id.fix_bt);
        fix_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openFixActivity();}
        });

        //抓取保養訊息
        read_bt = (Button) findViewById(R.id.read_bt);
        read_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread1 = new Thread(mutiThread1);
                thread1.start(); // 開始執行
                Thread thread2 = new Thread(mutiThread2);
                thread2.start(); // 開始執行
                Thread thread3 = new Thread(mutiThread3);
                thread3.start(); // 開始執行
            }
        });

        //GPS
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }*/

    }


    public void openOilActivity() {
        Intent intent = new Intent(this, OilActivity.class);
        startActivity(intent);
    }

    public void openWarningActivity() {
        Intent intent = new Intent(this, WarningActivity.class);
        startActivity(intent);
    }

    public void openMapsActivity () {
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    public void openFixActivity () {
        Intent intent = new Intent(this, FixActivity.class);
        startActivity(intent);
    }

    private    Runnable mutiThread1 = new Runnable(){

        public  void  run() {
            try {
                URL url = new URL("https://nodemcuconnection.000webhostapp.com/getmileagethousand.php");
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

                int responseCode = connection.getResponseCode();
                // 建立取得回應的物件
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream = connection.getInputStream(); // 取得輸入串流
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    // 讀取輸入串流的資料
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        JSONArray dataJson = new JSONArray(line);
                        int i = dataJson.length() - 1;
                        JSONObject info = dataJson.getJSONObject(i);
                        result1 ="該保養了喔!!!";
                    }
                    inputStream.close(); // 關閉輸入串流
                }

            } catch (Exception e) {
                result1 = "車子還不需保養"; // 如果出事，回傳錯誤訊息
            }
            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvResult.setText(result1);

                }
            });
        }
    };

    private    Runnable mutiThread2 = new Runnable(){

        public  void  run() {
            try {
                URL url = new URL("https://nodemcuconnection.000webhostapp.com/getwarningdata.php");
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

                int responseCode = connection.getResponseCode();
                // 建立取得回應的物件
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream = connection.getInputStream(); // 取得輸入串流
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    // 讀取輸入串流的資料
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        JSONArray dataJson = new JSONArray(line);
                        int i = dataJson.length() - 1;
                        JSONObject info = dataJson.getJSONObject(i);
                        result2 ="機車有倒車跡象喔!!!";
                    }
                    inputStream.close(); // 關閉輸入串流
                }

            } catch (Exception e) {
                result2 = "未有倒車跡象"; // 如果出事，回傳錯誤訊息
            }
            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvResult2.setText(result2);

                }
            });
        }
    };

    private    Runnable mutiThread3 = new Runnable(){

        public  void  run() {
            try {
                URL url = new URL("https://nodemcuconnection.000webhostapp.com/appjson.php");
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

                int responseCode = connection.getResponseCode();
                // 建立取得回應的物件
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream = connection.getInputStream(); // 取得輸入串流
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    // 讀取輸入串流的資料
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        JSONArray dataJson = new JSONArray(line);
                        int i = dataJson.length() - 1;
                        JSONObject info = dataJson.getJSONObject(i);
                        double lat = info.getDouble("lat");
                        double lng = info.getDouble("lng");
                        Lat = lat;
                        Lng = lng;
                    }
                    inputStream.close(); // 關閉輸入串流
                }

            } catch (Exception e) {
                //result1 = "未有倒車跡象"; // 如果出事，回傳錯誤訊息
            }
            // 當這個執行緒完全跑完後執行

        }
    };

}