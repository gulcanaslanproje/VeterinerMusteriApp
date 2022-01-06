package com.example.veterineruygulamasi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.veterineruygulamasi.Adapters.SanalKarneGecmisAdapter;
import com.example.veterineruygulamasi.Models.AsiModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.ChangeFragments;
import com.example.veterineruygulamasi.Utils.GetSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsiDetayFragment extends Fragment {

    View view;
    private GetSharedPreferences getSharedPreferences;
    String musid,petid;
    private RecyclerView asiDetayRecyclerView;
    private SanalKarneGecmisAdapter adapter;
    private List<AsiModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_asi_detay, container, false);
        tanimla();
        getGecmisAsi();
        return view;
    }
    public void tanimla() {

        getSharedPreferences = new GetSharedPreferences(getActivity());
        musid= getSharedPreferences.getSession().getString("id",null);
        petid = getArguments().getString("petid").toString();
        asiDetayRecyclerView = (RecyclerView)view.findViewById(R.id.asiDetayRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        asiDetayRecyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
    }
    private void getGecmisAsi() {

        Call<List<AsiModel>> req= ManagerAll.getInstance().getgecmisAsi(musid,petid);

        req.enqueue(new Callback<List<AsiModel>>() {
            @Override
            public void onResponse(Call<List<AsiModel>> call, Response<List<AsiModel>> response) {

                if(response.body().get(0).isTf())
                {
                    list = response.body();
                     adapter = new SanalKarneGecmisAdapter(list,getContext());
                     asiDetayRecyclerView.setAdapter(adapter);
                    Toast.makeText(getContext(), "Petinize ait "+list.size()+" adet geçmişte yapılan aşı bulunmaktadır.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    ChangeFragments changeFragments = new ChangeFragments(getContext());
                    changeFragments.change(new SanalKarneFragment());
                    Toast.makeText(getContext(), "Petinize ait geçmişte yapılan herhangi bir aşı Yoktur...", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<AsiModel>> call, Throwable t) {

            }
        });
    }

}