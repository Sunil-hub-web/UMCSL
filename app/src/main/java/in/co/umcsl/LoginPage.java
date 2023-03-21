package in.co.umcsl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.co.extra.Appurl;
import in.co.extra.SessionManager;

public class LoginPage extends AppCompatActivity {

    Button btn_signin;
    TextView text_signUp, otpviewtext;
    EditText edit_Password, edit_MobileNo;
    TextInputLayout editEnterOTP, editMobileNo;

    String Email,Id,Message,Mobilenumber,Name,Status;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(LoginPage.this);

        btn_signin = findViewById(R.id.btn_signin);
        edit_Password = findViewById(R.id.edit_Password);
        edit_MobileNo = findViewById(R.id.edit_MobileNo);
        editEnterOTP = findViewById(R.id.editEnterOTP);
        editMobileNo = findViewById(R.id.editMobileNo);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_MobileNo.getText().toString().equals("")) {
                    Toast.makeText(LoginPage.this, "Enater Your Mobile No", Toast.LENGTH_SHORT).show();
                } else if (edit_MobileNo.getText().toString().trim().length() != 10) {
                    Toast.makeText(LoginPage.this, "Enter 10 Digit Mobile No", Toast.LENGTH_SHORT).show();
                } else if (edit_Password.getText().toString().trim().equals("")) {
                    Toast.makeText(LoginPage.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                } else {

                    String mobileNo = edit_MobileNo.getText().toString().trim();
                    String password = edit_Password.getText().toString().trim();

                    userLogin(mobileNo,password);
                }
            }
        });
    }

    public void userLogin(String mobileNo,String password){

        ProgressDialog progressDialog = new ProgressDialog(LoginPage.this);
        progressDialog.setMessage("Login Please Wait....");
        progressDialog.show();

        String urlLogin = Appurl.userLogin+mobileNo+"/"+password;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,urlLogin,response -> {

            progressDialog.dismiss();

            try {
                JSONObject jsonObject = new JSONObject(response);

                String ChkULogin2Result = jsonObject.getString("ChkULogin2Result");
                JSONArray jsonArray_login = new JSONArray(ChkULogin2Result);

                for (int i= 0;i<jsonArray_login.length();i++){

                    JSONObject jsonObject_logindata = jsonArray_login.getJSONObject(0);

                    Email = jsonObject_logindata.getString("Email");
                    Id = jsonObject_logindata.getString("Id");
                    Message = jsonObject_logindata.getString("Message");
                    Mobilenumber = jsonObject_logindata.getString("Mobilenumber");
                    Name = jsonObject_logindata.getString("Name");
                    Status = jsonObject_logindata.getString("Status");

                }

                if (Status.equals("0")){

                    Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();

                }else{

                    sessionManager.setUserID(Id);
                    sessionManager.setUserPhonenumber(Mobilenumber);
                    sessionManager.setUserPassword(password);
                    sessionManager.setUserName(Name);
                    sessionManager.setLogin();

                    Intent intent = new Intent(LoginPage.this, DeshBoard.class);
                    startActivity(intent);

                }


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }



        },error -> {

            progressDialog.dismiss();
            Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();

        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(LoginPage.this);
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(sessionManager.isLogin()){
            startActivity(new Intent(LoginPage.this,DeshBoard.class));
        }
    }
}