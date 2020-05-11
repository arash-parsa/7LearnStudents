package com.sevenlearn.a7learnstudents;


import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitApiService  {

@GET ("experts/student")
    Call<List<Student>> getStudents();

@POST ("experts/student")
    Call<Student> saveStudents(@Body JsonObject body);
}
