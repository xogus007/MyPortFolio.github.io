package com.lkw.searchbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lkw.searchbar.retrofit_login.RetrofitInterfaceLog;
import com.lkw.searchbar.retrofit_login.model.LoginResponse;
import com.lkw.searchbar.unlogin.UnLoginMainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText loginIdEdt;
    private EditText loginPwEdt;

    private Retrofit retrofit;
    private RetrofitInterfaceLog loginRetro;
    private Call<LoginResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginIdEdt = findViewById(R.id.login_ID);
        loginPwEdt = findViewById(R.id.login_PW);

        Button loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
                // 로그인 서버 오류에도 실행 가능하도록
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.signup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });

        // 비회원 입장
        Button nonMember = findViewById(R.id.non_member_btn);
        nonMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UnLoginMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Retrofit 객체 생성
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.5.3:18021/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Retrofit 인터페이스 생성
        loginRetro = retrofit.create(RetrofitInterfaceLog.class);
    }

    void login() {
        // 데이터베이스 에서 데이터 조회
        call = loginRetro.login(
                loginIdEdt.getText().toString(),
                loginPwEdt.getText().toString()
        );

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 200) {
                    LoginResponse loginResponse = response.body();
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else if(response.code() == 204) {
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(response.code() == 403) {
                    Toast.makeText(getApplicationContext(), "잘못된 아이디 또는 비밀번호입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // 서버 응답 실패
                    Log.e("Retrofit", "Server response error");
                    Toast.makeText(getApplicationContext(), "서버 오류", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버 오류", Toast.LENGTH_SHORT).show();

                // 통신 실패
                Log.e("Retrofit", "Communication failure: " + t.getMessage());

            }
        });
    }
}

