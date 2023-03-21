package in.co.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.co.extra.Appurl;
import in.co.extra.SessionManager;
import in.co.umcsl.DeshBoard;
import in.co.umcsl.LoginPage;
import in.co.umcsl.R;

public class ProfileFragment extends Fragment {

    String Email,Id,Message,Mobilenumber,Name,Status,userId;

    SessionManager sessionManager;

    EditText text_UserName,text_driverContactNo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        TextView name_text = view.findViewById(R.id.name_text);
        name_text.setText("Profile");

        text_driverContactNo = view.findViewById(R.id.text_driverContactNo);
        text_UserName = view.findViewById(R.id.text_UserName);

        sessionManager = new SessionManager(getContext());
        userId = sessionManager.getUserID();
        getProfile(userId);


        return view;
    }

    public void getProfile(String userId){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Login Please Wait....");
        progressDialog.show();

        String urlLogin = Appurl.Proview+userId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,urlLogin, response -> {

            progressDialog.dismiss();

            try {
                JSONObject jsonObject = new JSONObject(response);

                String SUProviewResult = jsonObject.getString("SUProviewResult");
                JSONArray jsonArray_login = new JSONArray(SUProviewResult);

                for (int i= 0;i<jsonArray_login.length();i++){

                    JSONObject jsonObject_logindata = jsonArray_login.getJSONObject(0);

                    Email = jsonObject_logindata.getString("Email");
                    Id = jsonObject_logindata.getString("Id");
                    Message = jsonObject_logindata.getString("Message");
                    Mobilenumber = jsonObject_logindata.getString("Mobilenumber");
                    Name = jsonObject_logindata.getString("Name");
                    Status = jsonObject_logindata.getString("Status");
                }

                if(Status.equals("0")){

                    Toast.makeText(getContext(), Message, Toast.LENGTH_SHORT).show();

                }else{

                    text_driverContactNo.setText(Mobilenumber);
                    text_UserName.setText(Name);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        },error -> {

            progressDialog.dismiss();
            Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();

        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}
