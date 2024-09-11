package com.example.placement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class ApplyCompany extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId,name,email,id,title;
    TextView applied;
    Button logout;
    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_company);

        applied = findViewById(R.id.actvapplied);
        logout = findViewById(R.id.acbtnlogout);
        apply = findViewById(R.id.acbtnapply);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();



        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                email = document.getString("email");
//                Intent intent = new Intent();
                id = getIntent().getStringExtra("ID");
                title = getIntent().getStringExtra("TITLE");
                DocumentReference documentReference1 = fStore.collection(id).document(title).collection("StudentList").document(userId);
                documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "Document exists!");
                                applied.setVisibility(View.VISIBLE);
//                                Unapplied.setVisibility(View.VISIBLE);
                                apply.setVisibility(View.INVISIBLE);
                            } else {
                                Log.d(TAG, "Document does not exist!");
                            }
                        } else {
                            Log.d(TAG, "Failed with: ", task.getException());
                        }
                    }
                });
            }

        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ApplyCompany.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void Apply(View view) {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                name = document.getString("name");
                email = document.getString("email");
                id = getIntent().getStringExtra("ID");
                title = getIntent().getStringExtra("TITLE");
                DocumentReference documentReference2 = fStore.collection(id).document(title).collection("StudentList").document(userId);
                Map<String,Object> user = new HashMap<>();
                user.put("email",email);
                user.put("name",name);

                documentReference2.set(user).addOnSuccessListener(aVoid -> Log.d("TAG","OnSuccess: user Profile is created for "+ userId));
            }
        });

        Intent intent = new Intent(ApplyCompany.this,CompanyList.class);
        startActivity(intent);
    }
}