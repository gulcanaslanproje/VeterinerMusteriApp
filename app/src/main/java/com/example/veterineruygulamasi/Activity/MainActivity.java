package com.example.veterineruygulamasi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.veterineruygulamasi.Fragments.HomeFragment;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.ChangeFragments;
import com.example.veterineruygulamasi.Utils.GetSharedPreferences;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences getSharedPreferences;
    private GetSharedPreferences preferences;
    private ImageView anasayfaButon, aramayapButon ,cikisyapButon;
    private ChangeFragments changeFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragment();
        tanim();
        kontrol();
        action();
    }

    private void action() {
        anasayfaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new HomeFragment());
            }
        });

        aramayapButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:00000000000"));
                startActivity(intent);
            }
        });

        cikisyapButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSharedPreferences getSharedPreferences = new GetSharedPreferences(MainActivity.this);
                getSharedPreferences.deleteSession();
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void getFragment() {
        changeFragments = new ChangeFragments(MainActivity.this);
        changeFragments.change(new HomeFragment());
    }

    private void tanim() {
        preferences = new GetSharedPreferences(MainActivity.this);
        getSharedPreferences = preferences.getSession();
        anasayfaButon =(ImageView) findViewById(R.id.anasayfaButon);
        aramayapButon =(ImageView) findViewById(R.id.aramayapButon);
        cikisyapButon =(ImageView) findViewById(R.id.cikisyapButon);

    }

    private void kontrol() {
        if(getSharedPreferences.getString("id",null)==null && getSharedPreferences.getString("username",null)==null
                &&getSharedPreferences.getString("mailadres",null)==null)
        {
            Intent intent = new Intent( MainActivity.this ,GirisYapActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
