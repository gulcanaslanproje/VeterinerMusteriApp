package com.example.veterineruygulamasi.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veterineruygulamasi.Models.AsiModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.ChangeFragments;
import com.example.veterineruygulamasi.Utils.GetSharedPreferences;
import com.example.veterineruygulamasi.Utils.Warnings;
import com.squareup.picasso.Picasso;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AsiFragment extends Fragment {

    private  View view;
    private CalendarPickerView calendarPickerView;
    private DateFormat format;
    private Calendar nextYear;
    private Date today;
    private List<AsiModel> asiList;
    private  List<Date> dateList;
    private GetSharedPreferences getSharedPreferences;
    private  String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_asi, container, false);
        tanim();
        getAsi();
        clickToCalender();
        return view;
    }

    public  void tanim()
    {
        calendarPickerView = view.findViewById(R.id.calendarPickerView);
        format = new SimpleDateFormat("dd/MM/yyyy");
        nextYear =Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);
        today = new Date();
        calendarPickerView.init(today,nextYear.getTime());
        asiList = new ArrayList<>();
        dateList =new ArrayList<>();
        getSharedPreferences = new GetSharedPreferences(getActivity());
        id= getSharedPreferences.getSession().getString("id",null);

    }

    public  void getAsi()
    {
        Call<List<AsiModel>> req = ManagerAll.getInstance().getAsi(id);

        req.enqueue(new Callback<List<AsiModel>>() {
            @Override
            public void onResponse(Call<List<AsiModel>> call, Response<List<AsiModel>> response) {

                if(response.isSuccessful())
                {
                    if(response.body().get(0).isTf()) {
                        asiList = response.body();

                        for (int i = 0; i < asiList.size(); i++) {
                            String dataString = asiList.get(i).getAsitarih().toString();

                            try {
                                Date date = format.parse(dataString);
                                dateList.add(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        calendarPickerView.init(today, nextYear.getTime())
                                .withHighlightedDates(dateList);
                    }
                }
                else
                {
                    ChangeFragments changeFragments = new ChangeFragments(getContext());
                    changeFragments.change(new HomeFragment());
                    Toast.makeText(getContext(), "Petinize Ait Gelecek Tarihte Aşı Yoktur...", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<AsiModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText , Toast.LENGTH_LONG).show();

            }
        });
    }

    public void clickToCalender()
    {
        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                for(int i=0; i<dateList.size();i++)
                {
                    if(date.toString().equals(dateList.get(i).toString()))
                        openAsiAlert(asiList.get(i).getPetisim().toString(),asiList.get(i).getAsitarih().toString(),
                                asiList.get(i).getAsiisim().toString(),asiList.get(i).getPetresim().toString());
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }
    public void openAsiAlert(String petIsim,String tarih,String asiIsmi,String resimUrl ) {

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.asi_takip_alert_layout,null);

        TextView petisismtext =(TextView)view.findViewById(R.id.petisismtext);
        TextView petasitakipbilgitext =(TextView)view.findViewById(R.id.petasitakipbilgitext);
        CircleImageView asitakipCircleImageView =(CircleImageView)view.findViewById(R.id.asitakipCircleImageView);

        petisismtext.setText(petIsim);
        petasitakipbilgitext.setText(petIsim + "isimli petinizin "+ tarih +" tarihinde "+asiIsmi +" aşısı yapılacaktır.");
        Picasso.get().load(resimUrl).into(asitakipCircleImageView);

        final AlertDialog.Builder alert =new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog =alert.create();
        alertDialog.show();
    }
}















