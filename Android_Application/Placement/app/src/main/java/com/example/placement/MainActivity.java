package com.example.placement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.android.volley.VolleyLog.TAG;

public class MainActivity extends AppCompatActivity {

    TextView registration,emailagain;
    EditText Email,Password;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String userId,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Email = findViewById(R.id.letppemail);
        Password = findViewById(R.id.letpppassword);
        emailagain =findViewById(R.id.ltvppemailagain);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            final FirebaseUser user1 = fAuth.getCurrentUser();
            if(user1.isEmailVerified()) {
                userId = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("Utype").document(userId);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            type = document.getString("type");
                            if (type.equals("Student")) {
                                startActivity(new Intent(getApplicationContext(), HomeStudent.class));
                            } else {
                                startActivity(new Intent(getApplicationContext(), HomeCompany.class));
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
                finish();
            }
        }

        registration = findViewById(R.id.ltvppregister);
        registration.setMovementMethod(LinkMovementMethod.getInstance());
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Registration.class);
                startActivity(intent);
            }
        });
    }

    boolean isEmail(EditText text){
        CharSequence Email = text.getText().toString();
        return (!TextUtils.isEmpty(Email) && Patterns.EMAIL_ADDRESS.matcher(Email).matches());
    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public void LoginBtn(View view) {

        if(isEmpty(Email)){
            Toast.makeText(this, "You must entered email to login!", Toast.LENGTH_SHORT).show();
        }
        else if(!isEmail(Email)){
            Email.setError("Enter valid email!");
        }
        else if(isEmpty(Password)){
            Password.setError("Enter valid password");
        }
        else{
            String email = Email.getText().toString();
            String password = Password.getText().toString();

            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                final FirebaseUser user = fAuth.getCurrentUser();
                userId = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("Utype").document(userId);
                documentReference.get().addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()){
                        DocumentSnapshot document = task1.getResult();
                        type = document.getString("type");
                        if(document.exists()){
                            if(user.isEmailVerified()){
                                Toast.makeText(MainActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this,type,Toast.LENGTH_SHORT).show();
                                if(type.equals("Student")) {
                                    startActivity(new Intent(getApplicationContext(),HomeStudent.class));
                                }
                                else {
                                    startActivity(new Intent(getApplicationContext(),HomeCompany.class));
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this,"Verify your EMAIL first",Toast.LENGTH_SHORT).show();
                                emailagain.setVisibility(View.VISIBLE);
                                emailagain.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view1) {
                                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(MainActivity.this,"Verification email has been sent", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG,"onFailure: Email not sent" + e.getMessage());
                                            }
                                        });
                                    }
                                });
//                                Toast.makeText(MainActivity.this,"Email or Password is wrong", Toast.LENGTH_SHORT).show();
                            }
//                            Toast.makeText(MainActivity.this,type,Toast.LENGTH_SHORT).show();
//                            if(type.equals("Student")) {
//                                startActivity(new Intent(getApplicationContext(),HomeStudent.class));
//                            }
//                            else {
//                                startActivity(new Intent(getApplicationContext(),HomeCompany.class));
//                            }
                        }
                        else {
                            Log.d(TAG, "No such document");
                        }
                    }
                    else {
                        Log.d(TAG, "get failed with ", task1.getException());
                        Toast.makeText(MainActivity.this,"Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
//                if(task.isSuccessful()){
//                    Toast.makeText(MainActivity.this,"Logged in successfully", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(MainActivity.this,"Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                }
//                if(task.isSuccessful()){
//                    if(user.isEmailVerified()){
//                        Toast.makeText(MainActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText(MainActivity.this,"Verify your EMAIL first",Toast.LENGTH_SHORT).show();
//                        emailagain.setVisibility(View.VISIBLE);
//                        emailagain.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view1) {
//                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(MainActivity.this,"Verification email has been sent", Toast.LENGTH_SHORT).show();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.d(TAG,"onFailure: Email not sent" + e.getMessage());
//                                    }
//                                });
//                            }
//                        });
//                    }
//                }
//                else{
//                    Toast.makeText(MainActivity.this,"Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                }
            });
        }
    }
}