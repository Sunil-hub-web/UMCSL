package in.co.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

import in.co.adapter.AssignCustomerAdapter;
import in.co.adapter.ReportAdapter;
import in.co.extra.Appurl;
import in.co.extra.SessionManager;
import in.co.modelclass.AssignCustomer_ModelClass;
import in.co.modelclass.Report_ModelClass;
import in.co.umcsl.R;

public class AssignCustomerFragment extends Fragment {

    RecyclerView recyclerAssignReport;
    LinearLayoutManager linearLayoutManager;
    AssignCustomerAdapter assignCustomerAdapter;
    ArrayList<AssignCustomer_ModelClass> assigncustomer = new ArrayList<>();
    SessionManager sessionManager;
    ImageView logout_bar_img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assigncustomer,container,false);

        recyclerAssignReport = view.findViewById(R.id.recyclerAssignReport);
        logout_bar_img = view.findViewById(R.id.logout_bar_img);

        TextView name_text = view.findViewById(R.id.name_text);

        name_text.setText("Assign Customer");

        sessionManager = new SessionManager(getContext());

        String userId = sessionManager.getUserID();

        getGetCustList(userId);

        logout_bar_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.logoutUser();

            }
        });

        return view;
    }

    public void getGetCustList(String agentId){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();

        String apilistData = Appurl.SVCGetCustList+"AgentId="+agentId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apilistData, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String SVCGetAssignedCustomerListToAgentResult = jsonObject.getString("SVCGetAssignedCustomerListToAgentResult");

                    JSONArray jsonArray = new JSONArray(SVCGetAssignedCustomerListToAgentResult);

                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String AStatus = jsonObject1.getString("AStatus");
                        String AccountNumber = jsonObject1.getString("AccountNumber");
                        String AgentMstId = jsonObject1.getString("AgentMstId");
                        String AgentName = jsonObject1.getString("AgentName");
                        String CreatedDate = jsonObject1.getString("CreatedDate");
                        String FatherName = jsonObject1.getString("FatherName");
                        String Gender = jsonObject1.getString("Gender");
                        String Message = jsonObject1.getString("Message");
                        String MobileNumber = jsonObject1.getString("MobileNumber");
                        String Status = jsonObject1.getString("Status");
                        String TotalAmt = jsonObject1.getString("TotalAmt");
                        String UName = jsonObject1.getString("UName");

                        assigncustomer.add(new AssignCustomer_ModelClass(CreatedDate,AccountNumber,UName,MobileNumber,AStatus));

                    }

                    linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                    assignCustomerAdapter = new AssignCustomerAdapter(assigncustomer,getActivity());
                    recyclerAssignReport.setLayoutManager(linearLayoutManager);
                    recyclerAssignReport.setHasFixedSize(true);
                    recyclerAssignReport.setAdapter(assignCustomerAdapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}
