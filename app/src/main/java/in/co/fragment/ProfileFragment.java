package in.co.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

    String Email,Id,Message,Mobilenumber,Name,Status,userId,upMessage,upStatus;
    SessionManager sessionManager;
    EditText text_UserName,text_driverContactNo;
    TextView editProfile;
    ImageView logout_bar_img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        TextView name_text = view.findViewById(R.id.name_text);
        editProfile = view.findViewById(R.id.editProfile);
        logout_bar_img = view.findViewById(R.id.logout_bar_img);
        name_text.setText("Profile");

        text_driverContactNo = view.findViewById(R.id.text_driverContactNo);
        text_UserName = view.findViewById(R.id.text_UserName);

        sessionManager = new SessionManager(getContext());
        userId = sessionManager.getUserID();
        getProfile(userId);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editProfile.getText().toString().trim().equals("Edit")){

                    editProfile.setText("Update");

                    text_driverContactNo.setEnabled(false);
                    text_UserName.setEnabled(false);

                }else{

                    if (text_driverContactNo.getText().toString().trim().equals("")){

                        Toast.makeText(getContext(), "Enter Your Contact Number", Toast.LENGTH_SHORT).show();
                    } else if (text_UserName.getText().toString().trim().equals("")){

                        Toast.makeText(getContext(), "Enter Your UserName", Toast.LENGTH_SHORT).show();
                    }else{
                        editProfile(userId,text_driverContactNo.getText().toString().trim(),
                                "",text_UserName.getText().toString().trim(),"");
                    }

                }
            }
        });

        logout_bar_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.logoutUser();

            }
        });

        return view;
    }

    public void getProfile(String userId){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Profile View Please Wait....");
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

                    text_driverContactNo.setEnabled(true);
                    text_UserName.setEnabled(true);
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
    public void editProfile(String AgentId,String AgentMobileno, String BranchName, String AgentName, String AgentAddress){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Edit Profile Please Wait....");
        progressDialog.show();

        String updateProfile = Appurl.UpdateAgentProfile+"AgentId="+AgentId+"&AgentMobileno="+AgentMobileno+"&BranchName="
                +BranchName+"&AgentName="+AgentName+"&AgentAddress="+AgentAddress;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, updateProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    progressDialog.dismiss();

                    JSONObject jsonObject = new JSONObject(response);
                    String SVCUpdateAgentProfileResult = jsonObject.getString("SVCUpdateAgentProfileResult");

                    JSONArray jsonArray = new JSONArray(SVCUpdateAgentProfileResult);

                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        String AgentCode = jsonObject1.getString("AgentCode");
                        String AgentMstId = jsonObject1.getString("AgentMstId");
                        String AgentName = jsonObject1.getString("AgentName");
                        String BranchName = jsonObject1.getString("BranchName");
                        String Email = jsonObject1.getString("Email");
                        upMessage = jsonObject1.getString("Message");
                        upStatus = jsonObject1.getString("Status");
                        String Userid = jsonObject1.getString("Userid");
                        String Vch_Moblieno = jsonObject1.getString("Vch_Moblieno");
                        String Vch_address = jsonObject1.getString("Vch_address");
                        String vch_AadharCardPath = jsonObject1.getString("vch_AadharCardPath");
                        String vch_AadharCardno = jsonObject1.getString("vch_AadharCardno");
                        String vch_addresproofname = jsonObject1.getString("vch_addresproofname");
                        String vch_addresproofpath = jsonObject1.getString("vch_addresproofpath");
                        String vch_username = jsonObject1.getString("vch_username");
                    }
                    if (upStatus.equals("1")){

                        Toast.makeText(getActivity(),upMessage,Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(getActivity(),upMessage,Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
