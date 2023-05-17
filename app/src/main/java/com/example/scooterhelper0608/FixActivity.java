package com.example.scooterhelper0608;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class FixActivity extends AppCompatActivity {

    TextView date_TextView, km_textView;
    String result1 ,result2;
    Button catch_bt, clear_bt;
    String clear ="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix);
        // 找到視圖的元件並連接
        date_TextView = findViewById(R.id.date_TextView);
        km_textView = findViewById(R.id.km_textView);
        catch_bt = (Button) findViewById(R.id.catch_bt);
        clear_bt = (Button) findViewById(R.id.clear_bt);

        // 宣告按鈕的監聽器監聽按鈕是否被按下
        // 跟上次在 View 設定的方式並不一樣
        // 我只是覺得好像應該也教一下這種寫法
        catch_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 按下之後會執行的程式碼
                // 宣告執行緒
                Thread thread = new Thread(mutiThread);
                thread.start(); // 開始執行
                //fetchData();
            }
        });
        clear_bt.setOnClickListener(v ->  {
            sendPOST();
        });

    }

    private void sendPOST() {
        /**建立連線*/
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
        /**設置傳送所需夾帶的內容*/
        FormBody formBody = new FormBody.Builder()
                .add("reset", "1")
                .build();
        /**設置傳送需求*/
        Request request = new Request.Builder()
                .url("https://nodemcuconnection.000webhostapp.com/dropmileage.php")
                .post(formBody)
                .build();
        /**設置回傳*/
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                /**如果傳送過程有發生錯誤*/
                //tvRes.setText(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                /**取得回傳*/
                //tvRes.setText("POST回傳：\n" + response.body().string());
            }
        });
    }

    private    Runnable mutiThread = new Runnable(){

        public  void  run() {
            try {
                URL url = new URL("https://nodemcuconnection.000webhostapp.com/gettotal.php");
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
                        String mileage = info.getString("total");
                        result1 = dB.toString();
                        result2 = mileage.toString();

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
                    km_textView.setText(result2);
                }
            });
        }
    };



}