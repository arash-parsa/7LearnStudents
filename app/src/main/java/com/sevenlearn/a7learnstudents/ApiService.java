package com.sevenlearn.a7learnstudents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiService {
    private static RequestQueue requestQueue;
    private static final String TAG = "ApiService";
    private static  final  String BASE_URL = "http://expertdevelopers.ir/api/v1/";
    private String requestTag;

    public ApiService(Context context,String requestTag){
        this.requestTag =requestTag;
        if(requestQueue == null) {

           /* memory leak = chandin activity dashte bashim k baziya baste beshan vali age context shono pass dade bashim inja dige b khater in ke context negah dari mishe on activity nemitone az hafeze kharej beshe va be memory leak bar mikhorim
            chon ApplicationContext dar tole kole code apk shoma zendast */

           requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }



    public void saveStudents(String firstName, String lastName, int score, String course, final saveStudentCallback callback){
        JSONObject saveJsonObject = new JSONObject();
        Student student = new Student();
        try {
            saveJsonObject.put("first_name",firstName);
            saveJsonObject.put("last_name",lastName);
            saveJsonObject.put("score",score);
            saveJsonObject.put("course",course);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL+"experts/student",
                saveJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: "+response);
                Student student = new Student();

                try {
                    student.setLastName(response.getString("last_name"));
                    student.setFirstName(response.getString("first_name"));
                    student.setId(response.getInt("id"));
                    student.setScore(response.getInt("score"));
                    student.setCourse(response.getString("course"));
                    //به این شکل این نتیجه و ریسپانس بر می گرده به اون اکتیویتی که کالش کرده(AddNewStudentFormActivity)
                    callback.onSuccess(student);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.toString());
                callback.onError(error);
            }
        });
            request.setTag(requestTag);
        requestQueue.add(request);
    }



    public void getStudents(final StudentListCallback callback){
        StringRequest request = new StringRequest(Request.Method.GET, BASE_URL+"experts/student",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        //  req.setRetryPolicy(new DefaultRetryPolicy(10000,3,2));
                        List<Student> students = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject studentJsonObject = jsonArray.getJSONObject(i);
                                Student student = new Student();
                                student.setId(studentJsonObject.getInt("id"));
                                student.setScore(studentJsonObject.getInt("score"));
                                student.setCourse(studentJsonObject.getString("course"));
                                student.setFirstName(studentJsonObject.getString("first_name"));
                                student.setLastName(studentJsonObject.getString("last_name"));
                                students.add(student);

                            }


                            Log.i(TAG, "onResponse: " + students.size());
                            callback.onSuccess(students);


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
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

    }

    public void cancel(){
        requestQueue.cancelAll(requestTag);
    }


    //chon natije method saveStudents ro nemitonim bargardoinm:
    public interface saveStudentCallback{
//onSuccess yek student bar migardone :
        void onSuccess(Student student);


        void onError(VolleyError student);
    }

    public interface StudentListCallback{
        void onSuccess(List<Student> students);

        void onError(VolleyError error);

    }

}







