package com.sevenlearn.a7learnstudents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AddNewStudentFormActivity extends AppCompatActivity {

    private static final String TAG = "AddNewStudentFormActivity";

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student_form);
        apiService = new ApiService(this,TAG);



        Toolbar toolbar = findViewById(R.id.toolbar_addStudent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white_24dp);


        final TextInputEditText firstNameEt = findViewById(R.id.et_addNewStudent_firstName);
        final TextInputEditText lastNameEt = findViewById(R.id.et_addNewStudent_lastName);
        final TextInputEditText scoreEt = findViewById(R.id.et_addNewStudent_score);
        final TextInputEditText courseEt = findViewById(R.id.et_addNewStudent_course);

        View saveBtn = findViewById(R.id.fab_addNewStudent_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNameEt.length() > 0 &&
                    lastNameEt.length() > 0 &&
                            scoreEt.length() > 0 &&
                            courseEt.length() > 0){
                        apiService.saveStudents(
                                firstNameEt.getText().toString(),
                                lastNameEt.getText().toString(),
                                Integer.parseInt(scoreEt.getText().toString()),
                                        courseEt.getText().toString(),
                                new ApiService.saveStudentCallback() {
                                    @Override
                                    public void onSuccess(Student student) {
                                        Intent intent = new Intent();
                                        intent.putExtra("student",student);
                                        setResult(Activity.RESULT_OK,intent);
                                        finish();
                                    }

                                    @Override
                                    public void onError(Exception student) {
                                        Toast.makeText(AddNewStudentFormActivity.this, "خطای نامشخص", Toast.LENGTH_SHORT).show();
                                    }
                                });
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiService.cancel();
    }
}



/*
    //put extra typesh as noe primitive hast vase hamoon data inja b byte tabdil va sepas ersal mishe
    //serialaize bayad konim ba parcelable ya serializable
    //joft tabdil b byte
    //seriallaize java
    //parcelable android + better performance
    //key delkhah tarif kon ba estefade az in key to MAinActivity behesh dastresi gharare dashte bashim
   */
