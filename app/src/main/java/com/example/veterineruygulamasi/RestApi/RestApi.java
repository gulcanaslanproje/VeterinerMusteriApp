package com.example.veterineruygulamasi.RestApi;


import com.example.veterineruygulamasi.Models.AnswersModel;
import com.example.veterineruygulamasi.Models.AsiModel;
import com.example.veterineruygulamasi.Models.AskQuestionModel;
import com.example.veterineruygulamasi.Models.DeleteAnswerModel;
import com.example.veterineruygulamasi.Models.KampanyaModel;
import com.example.veterineruygulamasi.Models.LoginModel;
import com.example.veterineruygulamasi.Models.PetModel;
import com.example.veterineruygulamasi.Models.RegisterPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    @FormUrlEncoded
    @POST("/veterinerservis/kayitol.php")
    Call<RegisterPojo> registerUser(@Field("mailAdres") String mailAdres, @Field("kadi") String kadi, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("/veterinerservis/girisyap.php")
    Call<LoginModel> loginUser(@Field("mailadres") String mailAdres, @Field("sifre") String pass);

    @FormUrlEncoded
    @POST("/veterinerservis/petlerim.php")
    Call<List<PetModel>> getPets(@Field("musid") String mus_id);

    @FormUrlEncoded
    @POST("/veterinerservis/sorusor.php")
    Call<AskQuestionModel> soruSor(@Field("id") String id, @Field("soru") String soru);

    @FormUrlEncoded
    @POST("/veterinerservis/cevaplar.php")
    Call<List<AnswersModel>> getAnswers(@Field("mus_id") String mus_id);

    @FormUrlEncoded
    @POST("/veterinerservis/cevapsil.php")
    Call<DeleteAnswerModel> deleteAnswer(@Field("cevap") String cevapid, @Field("soru") String soruid);

    @GET("/veterinerservis/kampanya.php")
    Call<List<KampanyaModel>> getKampanya();

    @FormUrlEncoded
    @POST("/veterinerservis/asitakip.php")
    Call<List<AsiModel>> getAsi(@Field("id") String id);

    @FormUrlEncoded
    @POST("/veterinerservis/gecmisasi.php")
    Call<List<AsiModel>> getgecmisAsi(@Field("id") String id ,@Field("petid") String petid);
}
