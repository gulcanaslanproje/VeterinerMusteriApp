package com.example.veterineruygulamasi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veterineruygulamasi.Models.LoginModel;
import com.example.veterineruygulamasi.Models.RegisterPojo;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.GetSharedPreferences;
import com.example.veterineruygulamasi.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GirisYapActivity extends AppCompatActivity {

    private EditText loginMailAdress,loginPassword;
    private Button loginButton;
    private TextView loginText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);
        tanimla();
        click();
    }

    public void tanimla()
    {
        loginButton = (Button)findViewById(R.id.loginButton);
        loginMailAdress = (EditText)findViewById(R.id.loginMailAdress);
        loginPassword = (EditText)findViewById(R.id.loginPassword);
        loginText =(TextView) findViewById(R.id.loginText);
    }

    public void click()
    {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  mail = loginMailAdress.getText().toString();
                String password = loginPassword.getText().toString();
                login(mail,password);
                delete();
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GirisYapActivity.this , KayitOlActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void login(String mailAdress , String parola)
    {
        Call<LoginModel> req = ManagerAll.getInstance().girisYap(mailAdress,parola);
        req.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.body().isTf())
                {
                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GirisYapActivity.this , MainActivity.class);
                    GetSharedPreferences getSharedPreferences =new GetSharedPreferences(GirisYapActivity.this);
                    getSharedPreferences.setSession(response.body().getId(),response.body().getUsername(),response.body().getMailadres());
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Warnings.internetProblemText ,Toast.LENGTH_LONG).show();
            }
        });
    }
    public  void delete()
    {
        loginMailAdress.setText("");
        loginPassword.setText("");
    }
}
