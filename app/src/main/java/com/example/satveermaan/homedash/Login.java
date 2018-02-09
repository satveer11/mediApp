package com.example.satveermaan.homedash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {
    AwesomeValidation awesomeValidation;
    private EditText email,password;
    private StringRequest stringRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    public void init(){
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        validations();
    }
    public void signUp(View view){
        Intent i = new Intent(getApplicationContext(), Main3Activity.class);
        startActivity(i);
    }
    private void validations(){
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";

        awesomeValidation.addValidation(MainActivity.this,R.id.email, android.util.Patterns.EMAIL_ADDRESS,R.string.emailerr);
        awesomeValidation.addValidation(MainActivity.this, R.id.pwd, regexPassword, R.string.pwderr );

    }
    public void login(View view){
        stringRequest = new StringRequest(Request.Method.POST, Constants.loginUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
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
                params.put("email", email.getText().toString());
                params.put("password",password.getText().toString());
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
