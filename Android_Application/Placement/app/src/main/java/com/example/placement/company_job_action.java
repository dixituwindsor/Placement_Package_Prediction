package com.example.placement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class company_job_action extends AppCompatActivity {

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userId,name,email,id,title;
    Button delete;
    ListView listS;
    ArrayList<AppliedStudents> appliedStudentsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_job_action);

        delete = findViewById(R.id.jabtndelete);
        listS = findViewById(R.id.jalvppcompany);
        appliedStudentsArrayList = new ArrayList<>();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth = FirebaseAuth.getInstance();
                userId = fAuth.getCurrentUser().getUid();
                id = getIntent().getStringExtra("IDC");
                title = getIntent().getStringExtra("TITLEC");
                fStore = FirebaseFirestore.getInstance();
                fStore.collection("jobdetail").document(title).delete();
                fStore.collection(userId).document(title).
                        delete().
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(company_job_action.this, "job has been deleted from Database.", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(company_job_action.this, HomeCompany.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(company_job_action.this, "Fail to delete the job. ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        id = getIntent().getStringExtra("IDC");
        title = getIntent().getStringExtra("TITLEC");
        fStore.collection(userId).document(title).collection("StudentList").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        name = d.getString("name");
                        email = d.getString("email");

                        AppliedStudents appliedStudents = d.toObject(AppliedStudents.class);
                        appliedStudentsArrayList.add(appliedStudents);
                    }
                    StudentAdapter adapter = new StudentAdapter(company_job_action.this, appliedStudentsArrayList);
                    listS.setAdapter(adapter);

                } else {
                    Toast.makeText(company_job_action.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(company_job_action.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });


    }
}