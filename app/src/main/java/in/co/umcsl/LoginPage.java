package in.co.umcsl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class LoginPage extends AppCompatActivity {

    Button btn_signin;
    TextView text_signUp,otpviewtext;
    EditText edit_Password,edit_MobileNo;
    TextInputLayout editEnterOTP,editMobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

                Intent intent = new Intent(LoginPage.this,DeshBoard.class);
                startActivity(intent);

            }
        });
    }
}