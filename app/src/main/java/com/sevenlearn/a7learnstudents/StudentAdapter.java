
package com.sevenlearn.a7learnstudents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.SudentViewHolder> {

    private List<Student> students;

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }


    @NonNull
    @Override
    public SudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SudentViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SudentViewHolder holder, int position) {
        holder.bind(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class SudentViewHolder extends RecyclerView.ViewHolder {
        private TextView fullNameTv;
        private TextView courseTv;
        private TextView firstCharTv;
        private TextView scoreTv;


        public SudentViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameTv = itemView.findViewById(R.id.tv_student_fullName);
            courseTv = itemView.findViewById(R.id.tv_student_course);
            firstCharTv = itemView.findViewById(R.id.tv_student_first_character);
            scoreTv = itemView.findViewById(R.id.tv_student_score);
        }

        public void bind(Student student){
           fullNameTv.setText(student.getFirstName()+" "+student.getLastName());
            courseTv.setText(student.getCourse());
            firstCharTv.setText(student.getFirstName().substring(0,1));
            scoreTv.setText(String.valueOf(student.getScore()));
        }
    }
    public void addStudents(Student student){
        this.students.add(0,student);
        notifyItemInserted(0);

    }
}