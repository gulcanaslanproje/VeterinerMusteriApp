package com.example.veterineruygulamasi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veterineruygulamasi.Models.RegisterPojo;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KayitOlActivity extends AppCompatActivity {

   private EditText registerMailAdress,registerUserName,registerPassword;
   private Button kayitOlButton;
    private TextView registerText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        tanimla();
        registerToUser();
        changedActivity();
    }

    public void tanimla()
    {
        kayitOlButton = (Button)findViewById(R.id.kayitOlButton);
        registerMailAdress = (EditText)findViewById(R.id.registerMailAdress);
        registerUserName = (EditText)findViewById(R.id.registerUserName);
        registerPassword = (EditText)findViewById(R.id.registerPassword);
        registerText =(TextView) findViewById(R.id.registerText);
    }

    public void changedActivity()
    {
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KayitOlActivity.this , GirisYapActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void registerToUser()
    {
        kayitOlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  mail =registerMailAdress.getText().toString();
                String name =registerUserName.getText().toString();
                String password =registerPassword.getText().toString();
                register(mail,name,password);
                delete();
            }
        });
    }

    public void  register(String userMailAdress,String userName,String userPass)
    {
        Call<RegisterPojo> req = ManagerAll.getInstance().kayitOl(userMailAdress,userName,userPass);
        req.enqueue(new Callback<RegisterPojo>() {
            @Override
            public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                if(response.body().isTf())
                {
                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(KayitOlActivity.this , GirisYapActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<RegisterPojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Warnings.internetProblemText ,Toast.LENGTH_LONG).show();
            }
        });
    }

    public  void delete()
    {
        registerMailAdress.setText("");
        registerUserName.setText("");
        registerPassword.setText("");
    }
}
