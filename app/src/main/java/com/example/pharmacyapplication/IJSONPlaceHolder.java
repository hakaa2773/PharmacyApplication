package com.example.pharmacyapplication;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IJSONPlaceHolder {
    @GET("users")
    Call<List<Example_user>> getUsers();

    @GET("druggetbyid/{id}")
    Call<Drugs> getDrugs(@Path("id") Integer drug_id);


    @POST("savecustomer")
    Call<ResponseBody> savecustomer (@Body Customer customer);

    @GET("checkcustomerlogin/{email}")
    Call<CustomerLogin> checkCustomer(@Path("email") String email);

    @GET("getbynames")
    Call<List<DrugAll>> getAllDrugs();

    @POST("saveprescription")
    Call<ResponseBody> saveprescription (@Body Prescription prescription);

    @GET("getprescriptionById/{id}")
    Call<Prescription> getprescription (@Path("id") Integer prescription_id);

    @PUT("updateprescription/{id}")
    Call<ResponseBody>Updateprescription(@Path("id") Integer prescription_id,@Body Prescription prescription);

    @GET("deleteprescription/{id}")
    Call<ResponseBody> deletePrescription(@Path("id") Integer prescription_id);

    @GET("/checkcustomeremail/{email}")
    Call<Customer> checkCustomerEmail(@Path("email") String email);





}
