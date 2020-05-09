package com.sevenlearn.a7learnstudents;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Student implements Parcelable {
    //برای یکسان شدن نام فیلدها در اینجا با نام فیلدها در سرور(فقط برای فیلدهای که متفاوتند):
    private int id;
    private int score;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String course;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
//parcelable codes
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.score);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.course);
    }

    public Student() {
    }

    protected Student(Parcel in) {
        this.id = in.readInt();
        this.score = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.course = in.readString();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
