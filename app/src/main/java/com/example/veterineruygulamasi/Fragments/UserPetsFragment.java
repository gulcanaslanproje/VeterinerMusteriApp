package com.example.veterineruygulamasi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.veterineruygulamasi.Adapters.PetsAdapter;
import com.example.veterineruygulamasi.Models.PetModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.ChangeFragments;
import com.example.veterineruygulamasi.Utils.GetSharedPreferences;
import com.example.veterineruygulamasi.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPetsFragment extends Fragment {
    //bacaf7 e9dafd
    View view;
    private RecyclerView petlistrecyclerview;
    private PetsAdapter petsAdapter;
    private List<PetModel> petList;
    private ChangeFragments changeFragments;
    private String mus_id;
    private GetSharedPreferences getSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_pets, container, false);
        tanimlar();
        getPets(mus_id);
        return view;
    }

    private void tanimlar() {

        petList = new ArrayList<>();
        petlistrecyclerview = view.findViewById(R.id.petlistrecyclerview);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(), 2);
        petlistrecyclerview.setLayoutManager(mng);
        changeFragments = new ChangeFragments(getContext());
        getSharedPreferences = new GetSharedPreferences(getActivity());
        mus_id = getSharedPreferences.getSession().getString("id", null);
    }

    public void getPets(String mus_id) {
        Call<List<PetModel>> req = ManagerAll.getInstance().getPets(mus_id);

        req.enqueue(new Callback<List<PetModel>>() {
            @Override
            public void onResponse(Call<List<PetModel>> call, Response<List<PetModel>> response) {

                if (response.body().get(0).isTf()) {

                    petList = response.body();
                    petsAdapter = new PetsAdapter(petList, getContext());
                    petlistrecyclerview.setAdapter(petsAdapter);
                    Toast.makeText(getContext(),"Sistemde kay??tl?? "+petList.size()+" petiniz bulunmaktad??r",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "Sistemde kay??tl?? petiniz yok...", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }

            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }


}
