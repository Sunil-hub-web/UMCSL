package in.co.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import in.co.umcsl.R;

public class ShowAccount extends Fragment {

    EditText edit_AccountNo,edit_CustomerName,edit_MobileNumber,edit_BalanceAmount;
    String accNo,AccountNumber,Active,Address,AgentId,Applicant_FullName,Applicant_Occupation,Applicant_Status,COName,CreatedDate,
            DOB,Email,FatherName,Gender,MarkofIdenity,Memberid,MembershipNumber,Message,Status, APhotoPath,ASignaturePath,BalanceAmt,
            MobileNo,Pcontactno;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_showaccount,container,false);

        edit_AccountNo = view.findViewById(R.id.edit_AccountNo);
        edit_CustomerName = view.findViewById(R.id.edit_CustomerName);
        edit_MobileNumber = view.findViewById(R.id.edit_MobileNumber);
        edit_BalanceAmount = view.findViewById(R.id.edit_BalanceAmount);

        Bundle arguments = getArguments();

        if (arguments!=null){

            accNo = arguments.get("product_id").toString();
            getMemberDetails(accNo);
            //singleProduct(product_id);

        }

        return view;
    }

    public void getMemberDetails(String accno){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Account Details Please Wait....");
        progressDialog.show();

        String urlLogin = Appurl.SearchAccount+accno;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,urlLogin, response -> {

            progressDialog.dismiss();

            try {
                JSONObject jsonObject = new JSONObject(response);

                String SVCSearchAccountResult = jsonObject.getString("SVCSearchAccountResult");
                JSONArray jsonArray_login = new JSONArray(SVCSearchAccountResult);

                for (int i= 0;i<jsonArray_login.length();i++){

                    JSONObject jsonObject_logindata = jsonArray_login.getJSONObject(0);

                    APhotoPath = jsonObject_logindata.getString("APhotoPath");
                    ASignaturePath = jsonObject_logindata.getString("ASignaturePath");
                    AccountNumber = jsonObject_logindata.getString("AccountNumber");
                    Active = jsonObject_logindata.getString("Active");
                    Address = jsonObject_logindata.getString("Address");
                    AgentId = jsonObject_logindata.getString("AgentId");
                    Applicant_FullName = jsonObject_logindata.getString("Applicant_FullName");
                    Applicant_Occupation = jsonObject_logindata.getString("Applicant_Occupation");
                    Applicant_Status = jsonObject_logindata.getString("Applicant_Status");
                    BalanceAmt = jsonObject_logindata.getString("BalanceAmt");
                    CreatedDate = jsonObject_logindata.getString("CreatedDate");
                    DOB = jsonObject_logindata.getString("DOB");
                    COName = jsonObject_logindata.getString("COName");
                    Email = jsonObject_logindata.getString("Email");
                    FatherName = jsonObject_logindata.getString("FatherName");
                    Gender = jsonObject_logindata.getString("Gender");
                    MarkofIdenity = jsonObject_logindata.getString("MarkofIdenity");
                    Memberid = jsonObject_logindata.getString("Memberid");
                    MembershipNumber = jsonObject_logindata.getString("MembershipNumber");
                    Message = jsonObject_logindata.getString("Message");
                    Status = jsonObject_logindata.getString("Status");
                    MobileNo = jsonObject_logindata.getString("MobileNo");
                    Pcontactno = jsonObject_logindata.getString("Pcontactno");

                }

                if (Status.equals("0")){

                    Toast.makeText(getContext(), Message, Toast.LENGTH_SHORT).show();

                }else{

                    edit_AccountNo.setText(AccountNumber);
                    edit_CustomerName.setText(Applicant_FullName);
                    edit_MobileNumber.setText(MobileNo);
                    edit_BalanceAmount.setText(BalanceAmt);

//                    edit_AccountNo.setEnabled(false);
//                    edit_CustomerName.setEnabled(false);
//                    edit_MobileNumber.setEnabled(false);
//                    edit_BalanceAmount.setEnabled(false);
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
