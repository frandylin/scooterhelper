package com.example.scooterhelper0608;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;

public class LoginActivity extends AppCompatActivity {
    Button login_bt, register_bt;
    EditText et1,et2;
    ProgressBar progressBar;


    public static final String LOG_TAG = "AppsFlyerApp_Frandy";
    private static final String afDevKey = "ai5L2MSEK83mGBuk6nhdfe";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        AppsFlyerLib.getInstance().setDebugLog(true);
        AppsFlyerLib.getInstance().setMinTimeBetweenSessions(0);
        Log.e(LOG_TAG, "open");
        AppsFlyerLib.getInstance().init(afDevKey, null, this);
        Log.e(LOG_TAG, "install");
        AppsFlyerLib.getInstance().start(this);


        setContentView(R.layout.activity_login);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        login_bt = (Button) findViewById(R.id.login_bt);
        register_bt = (Button) findViewById(R.id.register_bt);
        register_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri= Uri.parse("https://nodemcuconnection.000webhostapp.com/input.html");
                Intent i=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(i);


            }
        });

        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPOST();
                openMainActivity();
                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.LOGIN, null);
            }
        });
    }

    private void sendPOST() {


        /**建立連線*/
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
        /**設置傳送所需夾帶的內容*/
        String account = et1.getEditableText().toString();
        String password = et2.getEditableText().toString();
        FormBody formBody = new FormBody.Builder()
                .add("account", account)
                .add("password", password)
                .build();
        /**設置傳送需求*/
        Request request = new Request.Builder()
                .url("https://nodemcuconnection.000webhostapp.com/LoginA.php")
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
                String res = response.body().string();
                if (res.equals("True")){
                    openMainActivity();
                }
                else{
                    //openMainActivity();
                }
            }

        });

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}