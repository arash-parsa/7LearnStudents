package com.sevenlearn.a7learnstudents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    //   private static RequestQueue requestQueue;
    private static final String TAG = "ApiService";
    private static final String BASE_URL = "http://expertdevelopers.ir/api/v1/";
    //    private String requestTag;
    private RetrofitApiService retrofitApiService;
/*
    private Gson gson;
*/




    public ApiService(Context context, String requestTag) {
       /* this.requestTag =requestTag;
        this.gson=new Gson();*/
        /*  if(requestQueue == null) {

         *//* memory leak = chandin activity dashte bashim k baziya baste beshan vali age context shono pass dade bashim inja dige b khater in ke context negah dari mishe on activity nemitone az hafeze kharej beshe va be memory leak bar mikhorim
            chon ApplicationContext dar tole kole code apk shoma zendast *//*

           requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }*/

        Retrofit retrofit = new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        retrofitApiService = retrofit.create(RetrofitApiService.class);

    }


    public void saveStudents(String firstName, String lastName, int score, String course, final saveStudentCallback callback) {
       /* JSONObject saveJsonObject = new JSONObject();
        Student student = new Student();
        try {
            saveJsonObject.put("first_name", firstName);
            saveJsonObject.put("last_name", lastName);
            saveJsonObject.put("score", score);
            saveJsonObject.put("course", course);

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

       //jSON OBJECT from gson package not jason.org
        JsonObject jsonObject =new JsonObject();
        jsonObject.addProperty("first_name", firstName);
        jsonObject.addProperty("last_name", lastName);
        jsonObject.addProperty("course", course);
        jsonObject.addProperty("score", score);

        retrofitApiService.saveStudents(jsonObject)
                .enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                callback.onError(new Exception(t));
            }
        });

        /*JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL+"experts/student",
                saveJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: "+response);
                //Gson Json to java ....but how it works?
                //java has refelction
                //میاد string جیسون رو نگا میکنه تک تک فیلد هاشو میکشه بیرون/دقیقا باید مطابق تک تک کلیدها باید یک فیلد داخل آن کلاس وجود داشته باشد میره داخل آن کلاس سرچ میکنه فیلدی با آن نام وجود دارد یا ن اگر وجود داشت فیلد رو میگیره و دیتا رو داخلش قرار میده
                Student student = gson.fromJson(response.toString(),Student.class);
                callback.onSuccess(student);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.toString());
                callback.onError(error);
            }
        });
            request.setTag(requestTag);
        requestQueue.add(request);*/
    }


    public void getStudents(final StudentListCallback callback) {
      /*  StringRequest request = new StringRequest(Request.Method.GET, BASE_URL+"experts/student",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        //  req.setRetryPolicy(new DefaultRetryPolicy(10000,3,2));


                        //Gson Json to java ....but how it works?
                        //برای کاتس های جنیرک از جمله این لیست نمی تواند عمل رفلکشن را انجام دهد
                        // کلاسTypeToken یک کلاس جنریک است که باید extend  کنیم تا تایپش(کلاس جنریک) رو بگیره (لیست student ها رو متوجه بشه)
                        //{} means extend form TypeToken
                        List<Student> students = gson.fromJson(response, new TypeToken<List<Student>>(){}.getType());
                        callback.onSuccess(students);
                        // tabdil java to json
                        gson.toJson(students.get(0));


                            Log.i(TAG, "onResponse: " + students.size());
                            callback.onSuccess(students);




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error);
                callback.onError(error);
            }
        });

        request.setTag(requestTag);
        requestQueue.add(request);
*/
        retrofitApiService.getStudents()
                // in ja miyad az hamon method Call k to RetrofitApiService tarif kardim estefade mikone
                .enqueue(new Callback<List<Student>>() {
                    @Override
                    public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                    callback.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Student>> call, Throwable t) {
                        callback.onError(new Exception(t));
                    }
                });




    }


    public void cancel() {
        /*  requestQueue.cancelAll(requestTag);*/
    }


    //chon natije method saveStudents ro nemitonim bargardoinm:
    public interface saveStudentCallback {
        //onSuccess yek student bar migardone :
        void onSuccess(Student student);


        /* void onError(VolleyError student);*/
        void onError(Exception student);
    }

    public interface StudentListCallback {
        void onSuccess(List<Student> students);

        /*     void onError(VolleyError error);*/
        void onError(Exception error);

    }

}







