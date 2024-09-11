package com.example.placement;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllCompany extends AppCompatActivity {

    RecyclerView Company;
    ArrayList<DataModel> dataModelArrayList;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_company);

        Company = findViewById(R.id.acrvppcompany);
        dataModelArrayList = new ArrayList<>();
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        loadDatainListview();
    }

    private void loadDatainListview(){
        fStore.collection("jobdetail").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        DataModel dataModal = d.toObject(DataModel.class);
                        dataModelArrayList.add(dataModal);
                    }
                    CLAdapter adapter = new CLAdapter(AllCompany.this, dataModelArrayList);
                    Company.setAdapter(adapter);
                } else {
                    Toast.makeText(AllCompany.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AllCompany.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}