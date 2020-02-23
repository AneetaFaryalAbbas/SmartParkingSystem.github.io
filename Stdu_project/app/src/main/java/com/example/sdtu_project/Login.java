package com.example.sdtu_project;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {

    EditText t1,t2;
    TextView txt1,txt2;
    Button b1;
    AlertDialog alertDialog;
    ProgressDialog pd;
    String url=MainActivity.UrlAddress+"SignIn.php";
    StringRequest stringRequest;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Typeface myfont= Typeface.createFromAsset(getAssets(),"font/Trebuchet MS.ttf");

        queue= Volley.newRequestQueue(this);
        pd= new ProgressDialog(this);
        pd.setProgressStyle(pd.STYLE_SPINNER);
        pd.setTitle("Login __ !");
        pd.setMessage("Plz Wait ___  !");


        t1=(EditText) findViewById(R.id.username);
        t2=(EditText) findViewById(R.id.password);


        txt1=(TextView) findViewById(R.id.loginstatus);
        alertDialog= new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Message");



    }


    private void checkLogin(){

        pd.show();
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                pd.cancel();
                if(response==null){

                    alertDialog.setMessage("No Response From the server __  !");
                    alertDialog.show();
                }
                else {
                    try {
                        JSONArray jsonarray = new JSONArray(response);
                        //  alertDialog.setMessage("Signing In ___  !");
                        // alertDialog.show();
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String s1 = jsonobject.getString("id");
                            String s2 = jsonobject.getString("username");
                            String s3 = jsonobject.getString("password");
                            String s4 = jsonobject.getString("cnic");
                            String s5 = jsonobject.getString("license");
                            String s6 = jsonobject.getString("contact");
                            String s7 = jsonobject.getString("address");
                            String s8 = jsonobject.getString("balance");
                            String s9 = jsonobject.getString("joindate");

                            MainActivity.smartPark_userid=s1;
                            MainActivity.smartPark_username=s2;
                            MainActivity.smartPark_password=s3;
                            MainActivity.smartPark_cnicNo=s4;
                            MainActivity.smartPark_license=s5;
                            MainActivity.smartPark_contactNo=s6;
                            MainActivity.smartPark_address=s7;
                            MainActivity.smartPark_balance= s8;
                            MainActivity.smartPark_joinDate=s9;

                            Intent t= new Intent(Login.this,MainPage.class);
                            startActivity(t);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        txt1.setText("Login Failed Plz try Again");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError){
                pd.dismiss();
                pd.cancel();
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                alertDialog.setMessage("" + message);
                alertDialog.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("s1", t1.getText().toString());
                params.put("s2", t2.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }
    //   code to go to the signup page
    public void signup_page(View view) {
        Intent t= new Intent(Login.this,CreateAccount.class);
        startActivity(t);
    }

    public void login_page(View view) {

        if(t1.getText().toString().equals("") || t2.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Please Fill Fields First",Toast.LENGTH_SHORT).show();
        }
        else{
            checkLogin();
        }
    }
}
