package com.example.placement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class Registration extends AppCompatActivity {

    EditText Email,Name,Phone,Password,CPassword;
    RadioGroup Type;
    RadioButton Student,Company,Utype;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Email = findViewById(R.id.retppemail);
        Name = findViewById(R.id.retppname);
        Phone = findViewById(R.id.retppphone);
        Password = findViewById(R.id.retpppassword);
        CPassword = findViewById(R.id.retppcpassword);
        Type = findViewById(R.id.rrgpptype);
        Student = findViewById(R.id.rrbppstudent);
        Company = findViewById(R.id.rrbppcompany);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),HomeCompany.class));
//            finish();
//        }

    }

    boolean isEmail(EditText text){
        CharSequence Email = text.getText().toString();
        return (!TextUtils.isEmpty(Email) && Patterns.EMAIL_ADDRESS.matcher(Email).matches());
    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkEnteredData(){
        String pswd = Password.getText().toString();
        String cpswd = CPassword.getText().toString();
        String pn = Phone.getText().toString();
        int i = pn.length();

        if(i != 10){
            Phone.getText().clear();
            Phone.setError("Enter valid phone number");
        }

        if(pswd.length()<6){
            Password.getText().clear();
            CPassword.getText().clear();
//            Password.setError("Password length must be 6 or more");
            CPassword.setError("Password length must be 6 or more");
        }

        if(pswd.equals(cpswd)) {
            Password.setError(null);
        }
        else{
            CPassword.getText().clear();
            CPassword.setError("Enter correct password");
        }
    }

    public void SubmitBtn(View view) {
        checkEnteredData();
        int tc = Type.getCheckedRadioButtonId();

        String vpass = Password.getText().toString();
        int l = vpass.length();

        if(isEmpty(Email)){
            Email.setError("Enter email address");
        }
        else if(!isEmail(Email)){
            Email.setError("Enter valid email!");
            Email.getText().clear();
        }
        else if(isEmpty(Name)){
            Name.setError("Enter Your Full Name");
        }
        else if(isEmpty(Phone)){
            Phone.setError("Enter your contact number");
        }
        else if(isEmpty(Password)){
            Password.setError("Enter Password");
        }
        else if(isEmpty(CPassword)){
            CPassword.setError("Enter Confirm password");
        }
        else{

            int a = Type.getCheckedRadioButtonId();
            Utype = findViewById(a);

            String email = Email.getText().toString();
            String password = Password.getText().toString();
            String name = Name.getText().toString();
            String phone = Phone.getText().toString();
            String type = Utype.getText().toString();

            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = fAuth.getCurrentUser();
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Registration.this,"Verification email has been sent", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"onFailure: Email not sent" + e.getMessage());
                            }
                        });
                        Toast.makeText(Registration.this,"User Created",Toast.LENGTH_SHORT).show();
                        userId = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fstore.collection("users").document(userId);
                        Map<String,Object> user1 = new HashMap<>();
                        user1.put("email",email);
                        user1.put("password",password);
                        user1.put("name",name);
                        user1.put("phone",phone);
                        user1.put("type",type);
                        documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG","OnSuccess: user Profile is created for "+ userId);
                            }
                        });

                        DocumentReference documentReference1 = fstore.collection("Utype").document(userId);
                        Map<String,Object> user2 = new HashMap<>();
                        user2.put("type",type);
                        documentReference1.set(user2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG","OnSuccess: user type is created for "+ userId);
                            }
                        });

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));

                    }
                    else{
                        Toast.makeText(Registration.this,"Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}