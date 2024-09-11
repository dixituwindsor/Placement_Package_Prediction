package com.example.placement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class JobDetails extends AppCompatActivity {

    RadioGroup Gender,Degreet,Workex,Specialization;
    RadioButton gj,dtj,wj,spj,Male,Female,DST,DCM,DO,WYes,WNo,SMH,SMF;;
    EditText name,ssc_p,hsc_p,degree_p,etest_p,mba_p,Salary,Location,Bond;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        name = findViewById(R.id.jetppcname);
        Gender = findViewById(R.id.jrgppgender);
        Degreet = findViewById(R.id.jrgppdegreet);
        Workex = findViewById(R.id.jrgppworkex);
        Specialization = findViewById(R.id.jrgppspecialisation);
        ssc_p = findViewById(R.id.jetppsscper);
        hsc_p = findViewById(R.id.jetpphscper);
        degree_p = findViewById(R.id.jetppdegreeper);
        etest_p = findViewById(R.id.jetppetestper);
        mba_p = findViewById(R.id.jetppmbaper);
        Male = findViewById(R.id.jrbppmale);
        Female = findViewById(R.id.jrbppfemale);
        DST = findViewById(R.id.jrbppdegreescience);
        DCM = findViewById(R.id.jrbppdegreecommerce);
        DO = findViewById(R.id.jrbppdegreeother);
        WYes = findViewById(R.id.jrbppworkexyes);
        WNo = findViewById(R.id.jrbppworkexno);
        SMH = findViewById(R.id.jrbppmkthr);
        SMF = findViewById(R.id.jrbppmktfin);
        Salary = findViewById(R.id.jetppsalary);
        Location = findViewById(R.id.jetpplocation);
        Bond = findViewById(R.id.jetppbond);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered(){
        if(isEmpty(name)){
            name.setError("Enter your company name");
        }

        if(Male.isChecked()){
            Male.setError(null);
        }
        else if(Female.isChecked()) {
            Female.setError(null);
        }
        else{
            Female.setError("Select type");
        }

        if(isEmpty(ssc_p)){
            ssc_p.setError("Enter SSC Percentage");
        }

        if(isEmpty(hsc_p)){
            hsc_p.setError("Enter HSC Percentage");
        }

        if(isEmpty(degree_p)){
            degree_p.setError("Enter Degree Percentage");
        }

        if(DST.isChecked()){
            DST.setError(null);
        }
        else if(DCM.isChecked()) {
            DCM.setError(null);
        }
        else if(DO.isChecked()) {
            DO.setError(null);
        }
        else{
            DO.setError("Select type");
        }

        if(WYes.isChecked()){
            WYes.setError(null);
        }
        else if(WNo.isChecked()) {
            WNo.setError(null);
        }
        else{
            WNo.setError("Select type");
        }

        if(isEmpty(etest_p)){
            etest_p.setError("Enter ETest Percentage");
        }

        if(SMH.isChecked()){
            SMH.setError(null);
        }
        else if(SMF.isChecked()) {
            SMF.setError(null);
        }
        else{
            SMF.setError("Select type");
        }

        if(isEmpty(mba_p)){
            mba_p.setError("Enter MBA Percentage");
        }

        if(isEmpty(Salary)){
            Salary.setError("Enter salary");
        }

        if(isEmpty(Location)){
            Location.setError("Enter work location");
        }

        if(isEmpty(Bond)){
            Bond.setError("Enter work bond");
        }
    }

    public void SubmitBtn(View view) {

        checkDataEntered();

        int a = Gender.getCheckedRadioButtonId();
        gj = findViewById(a);
        int e = Degreet.getCheckedRadioButtonId();
        dtj = findViewById(e);
        int f = Workex.getCheckedRadioButtonId();
        wj = findViewById(f);
        int g = Specialization.getCheckedRadioButtonId();
        spj = findViewById(g);

        String cname = name.getText().toString();
        String gender = gj.getText().toString();
        String sscp = ssc_p.getText().toString();
        String hscp = hsc_p.getText().toString();
        String degreep = degree_p.getText().toString();
        String degreet = dtj.getText().toString();
        String workex = wj.getText().toString();
        String etestp = etest_p.getText().toString();
        String specialization = spj.getText().toString();
        String mbap = mba_p.getText().toString();
        String salary  = Salary.getText().toString();
        String location = Location.getText().toString();
        String bond = Bond.getText().toString();
//        String title = cname.concat(specialization);

        userId = fAuth.getCurrentUser().getUid();
        String id = userId.toString();
        DocumentReference documentReference = fStore.collection("jobdetail").document(cname+specialization);
        Map<String,Object> user1 = new HashMap<>();
        user1.put("id",id);
        user1.put("title",cname+specialization);
        user1.put("cname",cname);
        user1.put("gender",gender);
        user1.put("sscp",sscp);
        user1.put("hscp",hscp);
        user1.put("degreep",degreep);
        user1.put("degreet",degreet);
        user1.put("workex",workex);
        user1.put("etestp",etestp);
        user1.put("specialization",specialization);
        user1.put("mbap",mbap);
        user1.put("salary",salary);
        user1.put("location",location);
        user1.put("bond",bond);
        documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","OnSuccess: user Profile is created for "+ userId);
            }
        });

            DocumentReference documentReference1 = fStore.collection(userId).document(cname+specialization);
            Map<String, Object> user2 = new HashMap<>();
            user2.put("id", id);
            user2.put("title",cname+specialization);
            user2.put("cname", cname);
            user2.put("gender", gender);
            user2.put("sscp", sscp);
            user2.put("hscp", hscp);
            user2.put("degreep", degreep);
            user2.put("degreet", degreet);
            user2.put("workex", workex);
            user2.put("etestp", etestp);
            user2.put("specialization", specialization);
            user2.put("mbap", mbap);
            user2.put("salary", salary);
            user2.put("location", location);
            user2.put("bond", bond);
            documentReference1.set(user2).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("TAG", "OnSuccess: user Profile is created for " + userId);
                }
            });

        Intent intent = new Intent(JobDetails.this,HomeCompany.class);
        startActivity(intent);
    }
}