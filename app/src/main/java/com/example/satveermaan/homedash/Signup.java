package com.example.satveermaan.homedash;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.HashMap;
import java.util.Map;

public class Main3Activity extends AppCompatActivity {
    private EditText firstName,lastName,email,password,confirmPassword,phoneNum;
    private Button signup;
    private StringRequest stringRequest;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        init();
    }
    private void init(){
        firstName=(EditText)findViewById(R.id.fname);
        lastName=(EditText)findViewById(R.id.lname);
        email=(EditText)findViewById(R.id.email);
        phoneNum=(EditText)findViewById(R.id.phno);
        password=(EditText)findViewById(R.id.pwd);
        confirmPassword=(EditText)findViewById(R.id.cnfwd);
        signup=(Button)findViewById(R.id.submit);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate())
                {
                   // Toast.makeText(Main3Activity.this, "Data received successfully", Toast.LENGTH_LONG).show();
                    signUp();
                }
                else
                {
                    Toast.makeText(Main3Activity.this, "Please Fill The Required Fields", Toast.LENGTH_LONG).show();
                }
            }
        });
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        validations();
    }
    private void validations(){
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(Main3Activity.this,R.id.fname,"[a-zA-Z\\s]+",R.string.fnameerr);
        awesomeValidation.addValidation(Main3Activity.this,R.id.lname,"[a-zA-Z\\s]+",R.string.lnameerr);
        awesomeValidation.addValidation(Main3Activity.this,R.id.email, android.util.Patterns.EMAIL_ADDRESS,R.string.emailerr);
        awesomeValidation.addValidation(Main3Activity.this,R.id.phno, RegexTemplate.TELEPHONE,R.string.phnerr);
        awesomeValidation.addValidation(Main3Activity.this, R.id.pwd, regexPassword, R.string.pwderr );
        awesomeValidation.addValidation(Main3Activity.this, R.id.cnfwd, R.id.pwd, R.string.cnfpwerr);
    }
    private void signUp(){
       // Toast.makeText(getApplicationContext(),"Signup",Toast.LENGTH_LONG).show();
        stringRequest = new StringRequest(Request.Method.POST, Constants.signupUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext()," Respose Is "+response.toString(),Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error "+error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("firstName",firstName.getText().toString());
                params.put("lastName",lastName.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password",password.getText().toString());
                params.put("phoneNum",phoneNum.getText().toString());
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
