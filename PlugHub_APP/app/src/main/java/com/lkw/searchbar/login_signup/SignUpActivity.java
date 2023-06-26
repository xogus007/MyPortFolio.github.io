package com.lkw.searchbar.login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lkw.searchbar.R;
import com.lkw.searchbar.login_signup.retrofit_signup.RetrofitInterfaceSign;
import com.lkw.searchbar.login_signup.retrofit_signup.model.SignUpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private EditText signUpIdEdt;
    private EditText signUpPwEdt;
    private EditText signUpPwEdt2;

    private Retrofit retrofit;
    private RetrofitInterfaceSign signUpRetro;
    private Call<SignUpResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        signUpIdEdt = findViewById(R.id.signID);
        signUpPwEdt = findViewById(R.id.signPW);
        signUpPwEdt2 = findViewById(R.id.signPW2);

        Button joinBtn = findViewById(R.id.join_btn);
        Button mainBtn = findViewById(R.id.main_btn);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();

            }
        });

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
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
        signUpRetro = retrofit.create(RetrofitInterfaceSign.class);
    }

    void signup() {
        // 데이터베이스 에서 데이터 조회
        call = signUpRetro.signup(
                signUpIdEdt.getText().toString(),
                signUpPwEdt.getText().toString(),
                signUpPwEdt2.getText().toString()
        );
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.code() == 200) {
                    SignUpResponse signUpResponse = response.body();
                    Toast.makeText(getApplicationContext(), "회원가입에 성공 하였습니다.", Toast.LENGTH_SHORT).show();

                } else {
                    if (response.code() == 409) {
                        Toast.makeText(getApplicationContext(), "해당 아이디는 이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 405) {
                        Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인 해주세요.", Toast.LENGTH_SHORT).show();
                    } else if(response.code() == 400) {
                        Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인 해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                // 통신 실패
                Log.e("Retrofit", "Communication failure: " + t.getMessage());

            }
        });
    }
}