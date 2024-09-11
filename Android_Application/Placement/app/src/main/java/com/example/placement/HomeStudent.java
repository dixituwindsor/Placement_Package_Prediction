package com.example.placement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeStudent extends AppCompatActivity {

    EditText ssc_p,hsc_p,degree_p,etest_p,mba_p;
    RadioGroup Gender,SSCboard,HSCboard,HSCstream,Degreet,Workex,Specialisation;
    RadioButton SG,SSB,SHB,SHS,SD,SW,SS,Male,Female,SCentral,SOther,HCentral,HOther,HScience,HCommerce,HArts,DST,DCM,DO,WYes,WNo,SMH,SMF;
    String gender_encoded,ssc_b_encoded,hsc_b_encoded,workex_encoded,specialisation_encoded,hsc_s_science,hsc_s_commerce,hsc_s_arts,degree_t_scitech,degree_t_commmgmt,degree_t_others;
    TextView Result;
    Button Submit;
    String url = "https://campus-placement-pred.herokuapp.com/predict";
    String userId,data;

    RadioButton gnf,sbf,hbf,hsf,dtf,wxf,spf;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);

        Gender = findViewById(R.id.rgppgender);
        ssc_p = findViewById(R.id.etppsscper);
        SSCboard = findViewById(R.id.rgppsscboard);
        hsc_p = findViewById(R.id.etpphscper);
        HSCboard = findViewById(R.id.rgpphscboard);
        HSCstream = findViewById(R.id.rgpphscstream);
        degree_p = findViewById(R.id.etppdegreeper);
        Degreet = findViewById(R.id.rgppdegreet);
        Workex = findViewById(R.id.rgppworkex);
        etest_p = findViewById(R.id.etppetestper);
        Specialisation = findViewById(R.id.rgppspecialisation);
        mba_p = findViewById(R.id.etppmbaper);
        Result = findViewById(R.id.tvppca);
        Submit = findViewById(R.id.btns);
        Male = findViewById(R.id.rbppmale);
        Female = findViewById(R.id.rbppfemale);
        SCentral = findViewById(R.id.rbppssccentral);
        SOther = findViewById(R.id.rbppsscother);
        HCentral = findViewById(R.id.rbpphsccentral);
        HOther = findViewById(R.id.rbpphscother);
        HScience = findViewById(R.id.rbpphscscience);
        HCommerce = findViewById(R.id.rbpphsccommerce);
        HArts = findViewById(R.id.rbpphscarts);
        DST = findViewById(R.id.rbppdegreescience);
        DCM = findViewById(R.id.rbppdegreecommerce);
        DO = findViewById(R.id.rbppdegreeother);
        WYes = findViewById(R.id.rbppworkexyes);
        WNo = findViewById(R.id.rbppworkexno);
        SMH = findViewById(R.id.rbppmkthr);
        SMF = findViewById(R.id.rbppmktfin);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered(){
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

        if(SCentral.isChecked()){
            SCentral.setError(null);
        }
        else if(SOther.isChecked()) {
            SOther.setError(null);
        }
        else{
            SOther.setError("Select type");
        }

        if(isEmpty(hsc_p)){
            hsc_p.setError("Enter HSC Percentage");
        }

        if(HCentral.isChecked()){
            HCentral.setError(null);
        }
        else if(HOther.isChecked()) {
            HOther.setError(null);
        }
        else{
            HOther.setError("Select type");
        }

        if(HScience.isChecked()){
            HScience.setError(null);
        }
        else if(HCommerce.isChecked()) {
            HCommerce.setError(null);
        }
        else if(HArts.isChecked()) {
            HArts.setError(null);
        }
        else{
            HArts.setError("Select type");
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
    }

    public void SubmitBtn(View view) {

        checkDataEntered();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int sgender = Gender.getCheckedRadioButtonId();
                int ssscb = SSCboard.getCheckedRadioButtonId();
                int shscb = HSCboard.getCheckedRadioButtonId();
                int shscs = HSCstream.getCheckedRadioButtonId();
                int sdegree = Degreet.getCheckedRadioButtonId();
                int sworkex = Workex.getCheckedRadioButtonId();
                int sspecialisation = Specialisation.getCheckedRadioButtonId();

                SG = findViewById(sgender);
                SSB = findViewById(ssscb);
                SHB = findViewById(shscb);
                SHS = findViewById(shscs);
                SD = findViewById(sdegree);
                SW = findViewById(sworkex);
                SS = findViewById(sspecialisation);

                if(SG.getText().toString().equals("Male")==true) {
                    gender_encoded = "1";
                } else{
                    gender_encoded ="0";
                }

                if(SSB.getText().toString().equals("Other")==true) {
                    ssc_b_encoded = "1";
                }else{
                    ssc_b_encoded = "0";
                }

                if(SHB.getText().toString().equals("Other")==true) {
                    hsc_b_encoded = "1";
                }else{
                    hsc_b_encoded = "0";
                }

                if(SW.getText().toString().equals("Yes")==true) {
                    workex_encoded = "1";
                }else{
                    workex_encoded = "0";
                }

                if(SS.getText().toString().equals("Marketing HR")==true) {
                    specialisation_encoded = "1";
                }else{
                    specialisation_encoded = "0";
                }

                if(SHS.getText().toString().equals("Science")==true) {
                    hsc_s_science = "1";
                }else{
                    hsc_s_science = "0";
                }
                if(SHS.getText().toString().equals("Commerce")==true){
                    hsc_s_commerce = "1";
                }else{
                    hsc_s_commerce = "0";
                }
                if(SHS.getText().toString().equals("Arts")==true){
                    hsc_s_arts = "1";
                }else{
                    hsc_s_arts = "0";
                }

                if(SD.getText().toString().equals("Science and Technology")==true){
                    degree_t_scitech = "1";
                }else{
                    degree_t_scitech = "0";
                }
                if(SHS.getText().toString().equals("Commerce and Management")==true){
                    degree_t_commmgmt = "1";
                }else{
                    degree_t_commmgmt = "0";
                }
                if(SHS.getText().toString().equals("Other")==true){
                    degree_t_others = "1";
                }else{
                    degree_t_others = "0";
                }


                StringRequest stringrequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    data = jsonObject.getString("placement");

                                    if(data.equals("Can not be placed")){
                                        Intent intent = new Intent(HomeStudent.this,NotPlaced.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Intent intent1 = new Intent(HomeStudent.this,CompanyList.class);
                                        startActivity(intent1);
                                    }

                                    Result.setText(data);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(HomeStudent.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("ssc_p",ssc_p.getText().toString());
                        params.put("hsc_p",hsc_p.getText().toString());
                        params.put("degree_p",degree_p.getText().toString());
                        params.put("etest_p",etest_p.getText().toString());
                        params.put("mba_p",mba_p.getText().toString());
                        params.put("gender_encoded",gender_encoded);
                        params.put("ssc_b_encoded",ssc_b_encoded);
                        params.put("hsc_b_encoded",hsc_b_encoded);
                        params.put("workex_encoded",workex_encoded);
                        params.put("specialisation_encoded",specialisation_encoded);
                        params.put("degree_t_commmgmt",degree_t_commmgmt);
                        params.put("degree_t_others",degree_t_others);
                        params.put("degree_t_scitech",degree_t_scitech);
                        params.put("hsc_s_arts",hsc_s_arts);
                        params.put("hsc_s_commerce",hsc_s_commerce);
                        params.put("hsc_s_science",hsc_s_science);

                        return  params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(HomeStudent.this);
                queue.add(stringrequest);
            }
        });

        int a = Gender.getCheckedRadioButtonId();
        gnf = findViewById(a);
        int b = SSCboard.getCheckedRadioButtonId();
        sbf = findViewById(b);
        int c = HSCboard.getCheckedRadioButtonId();
        hbf = findViewById(c);
        int d = HSCstream.getCheckedRadioButtonId();
        hsf = findViewById(d);
        int e = Degreet.getCheckedRadioButtonId();
        dtf = findViewById(e);
        int f = Workex.getCheckedRadioButtonId();
        wxf = findViewById(f);
        int g = Specialisation.getCheckedRadioButtonId();
        spf = findViewById(g);

        String gender = gnf.getText().toString();
        String sscp = ssc_p.getText().toString();
        String sscb = sbf.getText().toString();
        String hscp = hsc_p.getText().toString();
        String hscb = hbf.getText().toString();
        String hscs = hsf.getText().toString();
        String degreep = degree_p.getText().toString();
        String degreet = dtf.getText().toString();
        String workex = wxf.getText().toString();
        String etestp = etest_p.getText().toString();
        String specialization = spf.getText().toString();
        String mbap = mba_p.getText().toString();

        userId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("studentdata").document(userId);
        Map<String,Object> user1 = new HashMap<>();
        user1.put("gender",gender);
        user1.put("sscp",sscp);
        user1.put("sscb",sscb);
        user1.put("hscp",hscp);
        user1.put("hscb",hscb);
        user1.put("hscs",hscs);
        user1.put("degreep",degreep);
        user1.put("degreet",degreet);
        user1.put("workex",workex);
        user1.put("etestp",etestp);
        user1.put("specialization",specialization);
        user1.put("mbap",mbap);
        user1.put("package",data);
        documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","OnSuccess: user Profile is created for "+ userId);
            }
        });
    }

    public void LogOut(View view) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
    }

    public void companyList(View view) {
        startActivity(new Intent(HomeStudent.this,CompanyList.class));
    }
}