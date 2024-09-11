package com.example.placement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CompanyList extends AppCompatActivity {

//    ListView Company;
    RecyclerView CompanyList;
    ArrayList<DataModel> dataModelArrayList;
    CLAdapter clAdapter;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    Button logout;
    String userId,gender,sscp,hscp,degreep,degreet,workex,etestp,specialization,mbap;
    int sscpn,hscpn,degreepn,etestpn,mbapn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        logout = findViewById(R.id.clbtnlogout);
//        Company = findViewById(R.id.cllvppcompany);
        CompanyList = findViewById(R.id.clrcvlist);
        CompanyList.setHasFixedSize(true);
        CompanyList.setLayoutManager(new LinearLayoutManager(this));

        dataModelArrayList = new ArrayList<>();
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CompanyList.this,MainActivity.class);
                startActivity(intent);
            }
        });

        loadDatainListview();

//        Company.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(),company_job_action.class);
//                startActivity(intent);
//            }
//        });

    }

    private void loadDatainListview(){

        userId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("studentdata").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    gender = document.getString("gender");
                    sscp = document.getString("sscp");
                    sscpn = Integer.parseInt(sscp);
                    hscp = document.getString("hscp");
                    hscpn = Integer.parseInt(hscp);
                    degreep = document.getString("degreep");
                    degreepn = Integer.parseInt(degreep);
                    degreet = document.getString("degreet");
                    workex = document.getString("workex");
                    etestp = document.getString("etestp");
                    etestpn = Integer.parseInt(etestp);
                    specialization = document.getString("specialization");
                    mbap = document.getString("mbap");
                    mbapn = Integer.parseInt(mbap);
                }
            }
        });

        fStore.collection("jobdetail").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        String g = d.getString("gender");
                        String sp = d.getString("sscp");
                        int spn = Integer.parseInt(sp);
                        String hp = d.getString("hscp");
                        int hpn = Integer.parseInt(hp);
                        String dp = d.getString("degreep");
                        int dpn = Integer.parseInt(dp);
                        String dt = d.getString("degreet");
                        String wx = d.getString("workex");
                        String ep = d.getString("etestp");
                        int epn = Integer.parseInt(ep);
                        String spz = d.getString("specialization");
                        String mp = d.getString("mbap");
                        int mpn = Integer.parseInt(mp);

                        if(g.equals(gender) && (spn <= sscpn) && (hpn <= hscpn) && (dpn <= degreepn) && dt.equals(degreet) && wx.equals(workex) && (epn <= etestpn) && spz.equals(specialization) && (mpn <= mbapn)){
                            DataModel dataModal = d.toObject(DataModel.class);
                            dataModelArrayList.add(dataModal);
                        }

                    }
                    CLAdapter adapter = new CLAdapter(CompanyList.this, dataModelArrayList);
                    CompanyList.setAdapter(adapter);

                } else {
                    Toast.makeText(CompanyList.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CompanyList.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}