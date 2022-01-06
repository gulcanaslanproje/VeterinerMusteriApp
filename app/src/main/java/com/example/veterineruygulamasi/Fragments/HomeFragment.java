package com.example.veterineruygulamasi.Fragments;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.veterineruygulamasi.Adapters.AnswersAdapter;
import com.example.veterineruygulamasi.Models.AnswersModel;
import com.example.veterineruygulamasi.Models.AskQuestionModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.ChangeFragments;
import com.example.veterineruygulamasi.Utils.GetSharedPreferences;
import com.example.veterineruygulamasi.Utils.Warnings;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public View view;
    private LinearLayout petlerimLayout,sorusorLayout,answersLayout, kampanyaLayout,asiTakipLayout, sanalKarneLayout;
    private ChangeFragments changeFragments;
    private GetSharedPreferences getSharedPreferences;
    private  String id;
    private AnswersAdapter answersAdapter;
    private  List<AnswersModel> answersList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        tanimla();
        action();
        return view;
    }

    public void tanimla() {

        petlerimLayout =(LinearLayout) view.findViewById(R.id.petlerimLayout);
        sorusorLayout = (LinearLayout) view.findViewById(R.id.sorusorLayout);
        answersLayout = (LinearLayout) view.findViewById(R.id.answersLayout);
        kampanyaLayout = (LinearLayout) view.findViewById(R.id.kampanyaLayout);
        asiTakipLayout =(LinearLayout)view.findViewById(R.id.asiTakipLayout);
        sanalKarneLayout =(LinearLayout)view.findViewById(R.id.sanalKarneLayout);
        changeFragments = new ChangeFragments(getContext());
        getSharedPreferences = new GetSharedPreferences(getActivity());
        id= getSharedPreferences.getSession().getString("id",null);
        answersList = new ArrayList<>();
    }

    private void action() {
        petlerimLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new UserPetsFragment());
            }
        });

        sorusorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openQuestionAlert();
            }
        });

        answersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswers(id);
            }
        });

        kampanyaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new KampanyaFragment());
            }
        });

        asiTakipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new AsiFragment());

            }
        });
        sanalKarneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new SanalKarneFragment());

            }
        });
    }



    public void openQuestionAlert() {

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.soru_sor_alert_layout,null);

        final EditText sorusoredittext =(EditText)view.findViewById(R.id.sorusoredittext);
        Button sorusorbutton =(Button)view.findViewById(R.id.sorusorbutton);

        final AlertDialog.Builder alert =new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog =alert.create();
        sorusorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                askQuestion(id,sorusoredittext.getText().toString(), alertDialog);

            }
        });
        alertDialog.show();
    }

    private void askQuestion(String mus_id, String soru, final AlertDialog alertDialog) {

        Call<AskQuestionModel> req = ManagerAll.getInstance().soruSor(mus_id,soru);
        req.enqueue(new Callback<AskQuestionModel>() {
            @Override
            public void onResponse(Call<AskQuestionModel> call, Response<AskQuestionModel> response) {
               if(response.body().isTf())
               {
                   Toast.makeText(getContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                   alertDialog.cancel();
               }
               else
               {
                   Toast.makeText(getContext(), response.body().getText(), Toast.LENGTH_LONG).show();
               }

            }

            @Override
            public void onFailure(Call<AskQuestionModel> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText , Toast.LENGTH_LONG).show();
            }
        });


    }
    private void getAnswers(String id) {

        Call<List<AnswersModel>> req = ManagerAll.getInstance().getAnswers(id);

        req.enqueue(new Callback<List<AnswersModel>>() {
            @Override
            public void onResponse(Call<List<AnswersModel>> call, Response<List<AnswersModel>> response) {

                if(response.body().get(0).isTf())
                {
                    if(response.isSuccessful())
                    {
                        answersList = response.body();
                        answersAdapter = new AnswersAdapter(answersList,getContext());
                        openAnswersList();
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"Herhangi bir cevap yok...",Toast.LENGTH_LONG).show();
                   // changeFragments.change(new HomeFragment());
                }

            }

            @Override
            public void onFailure(Call<List<AnswersModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText , Toast.LENGTH_LONG).show();
            }
        });
    }
    public void openAnswersList() {

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.cevap_alert_layout,null);
        RecyclerView cevapRecyclerView = (RecyclerView) view.findViewById(R.id.cevapRecyclerView);

        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        cevapRecyclerView.setLayoutManager(layoutManager);
        cevapRecyclerView.setAdapter(answersAdapter);
        alertDialog.show();
    }
}
