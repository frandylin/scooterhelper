package com.example.scooterhelper0608;

import androidx.appcompat.app.AppCompatActivity;

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

public class WarningActivity extends AppCompatActivity {
    TextView date_TextView, time_textView , location_textView;
    String result1 ,result2;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);

        // 找到視圖的元件並連接
        date_TextView = findViewById(R.id.date_TextView);
        time_textView = findViewById(R.id.time_textView);
        button = (Button) findViewById(R.id.catch_bt);

        // 宣告按鈕的監聽器監聽按鈕是否被按下
        // 跟上次在 View 設定的方式並不一樣
        // 我只是覺得好像應該也教一下這種寫法
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 按下之後會執行的程式碼
                // 宣告執行緒
                Thread thread = new Thread(mutiThread);
                thread.start(); // 開始執行
                //fetchData();
            }
        });
    }

    /*private Runnable mutiThread = new Runnable(){
        public void run()
        {
            try {
                URL url = new URL("https://nodemcuconnection.000webhostapp.com/dbreadjson.php");
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

                int responseCode =
                        connection.getResponseCode();
                // 建立取得回應的物件
                if(responseCode ==
                        HttpURLConnection.HTTP_OK){
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream =
                            connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    // 讀取輸入串流的資料
                    String box = ""; // 宣告存放用字串
                    String line = null; // 宣告讀取用的字串
                    while((line = bufReader.readLine()) != null) {
                        box += line + "\n";
                        // 每當讀取出一列，就加到存放字串後面
                    }
                    inputStream.close(); // 關閉輸入串流
                    result1 = box; // 把存放用字串放到全域變數
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch(Exception e) {
                result1 = e.toString(); // 如果出事，回傳錯誤訊息
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                public void run() {
                    date_TextView.setText(result1); // 更改顯示文字
                }
            });
        }
    };*/

    private    Runnable mutiThread = new Runnable(){

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
                        String dB = info.getString("date");
                        String time = info.getString("time");
                        result1 = dB.toString();
                        result2 = time.toString();

                    }
                    inputStream.close(); // 關閉輸入串流
                }

            } catch (Exception e) {
                result1 = e.toString(); // 如果出事，回傳錯誤訊息
                result2 = e.toString();
            }
            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    date_TextView.setText(result1);
                    time_textView.setText(result2);
                }
            });

        }
    };


    private    Runnable mutiThread2 = new Runnable(){

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
                        String lat = info.getString("lat");
                        String lng = info.getString("lng");
                        result1 = lat.toString();
                        result2 = lng.toString();

                    }
                    inputStream.close(); // 關閉輸入串流
                }

            } catch (Exception e) {
                result1 = e.toString(); // 如果出事，回傳錯誤訊息
                result2 = e.toString();
            }
            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    location_textView.setText(result1 + result2) ;

                }
            });

        }
    };
}