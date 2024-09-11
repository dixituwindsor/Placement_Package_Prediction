package com.example.placement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<AppliedStudents> {

    public StudentAdapter(@NonNull Context context, ArrayList<AppliedStudents> appliedStudentsArrayList) {
        super(context, 0, appliedStudentsArrayList);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_student_list, parent, false);
        }
        AppliedStudents appliedStudents = getItem(position);

        TextView sname = listitemView.findViewById(R.id.sltvname);
        TextView semail = listitemView.findViewById(R.id.sltvemail);

        sname.setText(appliedStudents.getName());
        semail.setText(appliedStudents.getEmail());

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Item clicked is : " + appliedStudents.getName(), Toast.LENGTH_SHORT).show();
//                Intent intent10 = new Intent();
//                intent10.putExtra("id",dataModel.getId());
            }
        });
        return listitemView;
    }
}
