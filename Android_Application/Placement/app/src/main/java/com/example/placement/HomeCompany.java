package com.example.placement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

public class HomeCompany extends AppCompatActivity {

//    ListView Company;
    RecyclerView CompanyList;
    ArrayList<DataModel> dataModelArrayList;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userId,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_company);

//        Company = findViewById(R.id.hclvppcompany);
        CompanyList = findViewById(R.id.hcrcvjobs);
        CompanyList.setHasFixedSize(true);
        CompanyList.setLayoutManager(new LinearLayoutManager(this));
        dataModelArrayList = new ArrayList<>();
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        loadDatainListview();

//        Company.setAdapter(new ListviewAdapter(this));

//        Company.setOnItemClickListener((parent, view, position, id) -> {
//            String itemChosen = (String) parent.getItemAtPosition(position);
//            Toast.makeText(HomeCompany.this,itemChosen,Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(HomeCompany.this, company_job_action.class);
//            startActivity(intent);
//            finish();
////                intent.putExtra("selected_job", itemChosen);
//        });
//        Company.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent10 = getIntent();
//                String id1 = intent10.getStringExtra("id");
//                Toast.makeText(HomeCompany.this,"String ID is = "+id1,Toast.LENGTH_SHORT).show();
//            }
//        });

//        CompanyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                String name = parent.getItemAtPosition(position).toString();
////                Toast.makeText(HomeCompany.this,name,Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(HomeCompany.this, company_job_action.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    public void JobBtn(View view) {
        Intent intent = new Intent(HomeCompany.this,JobDetails.class);
        startActivity(intent);
    }

    public void LogOut(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    private void loadDatainListview(){
        userId = fAuth.getCurrentUser().getUid();
        fStore.collection(userId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    userId = fAuth.getCurrentUser().getUid();
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                            DataModel dataModal = d.toObject(DataModel.class);
                            dataModelArrayList.add(dataModal);
                    }
                    AdapterHC adapter = new AdapterHC(HomeCompany.this, dataModelArrayList);
                    CompanyList.setAdapter(adapter);

                } else {
                    makeText(HomeCompany.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                makeText(HomeCompany.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void JobdeleteBtn(View view) {
//        Intent intent = new Intent(HomeCompany.this,company_job_action.class);
//        startActivity(intent);
//    }


//    public void selectedItem(View view) {
//        Company.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String itemChosen = (String) parent.getItemAtPosition(position);
//                Intent intent = new Intent(HomeCompany.this, company_job_action.class);
//                intent.putExtra("selected_job", itemChosen);
//                startActivity(intent);
//            }
//        });
//    }
}