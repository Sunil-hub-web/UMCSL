package in.co.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.co.adapter.AssignCustomerAdapter;
import in.co.adapter.ReportAdapter;
import in.co.extra.Appurl;
import in.co.extra.SessionManager;
import in.co.modelclass.AssignCustomer_ModelClass;
import in.co.modelclass.Report_ModelClass;
import in.co.umcsl.R;

public class ReportFragment extends Fragment {

    RecyclerView recyclerReport;
    LinearLayoutManager linearLayoutManager;
    ReportAdapter reportAdapter;
    ArrayList<Report_ModelClass> report = new ArrayList<>();
    EditText edit_ToDate,edit_FromDate;
    int year, month, day, hour, minute;
    String date,time,userId,DateofCollection;
    DatePickerDialog.OnDateSetListener setListener;
    SessionManager sessionManager;
    ImageView logout_bar_img;

    TextView totalPrint;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report,container,false);

        recyclerReport = view.findViewById(R.id.recyclerReport);
       // edit_ToDate = view.findViewById(R.id.edit_ToDate);
        edit_FromDate = view.findViewById(R.id.edit_FromDate);
        logout_bar_img = view.findViewById(R.id.logout_bar_img);
        totalPrint = view.findViewById(R.id.totalPrint);

        TextView name_text = view.findViewById(R.id.name_text);

        name_text.setText("Report");

        edit_FromDate.setText("");

        sessionManager = new SessionManager(getContext());
        userId = sessionManager.getUserID();

        Bundle arguments = getArguments();

        if (arguments != null) {

            DateofCollection = arguments.get("DateofCollection").toString();
            getReport(userId,DateofCollection);
            edit_FromDate.setText(DateofCollection);
        }

        edit_FromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCalender1();
            }
        });

        logout_bar_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.logoutUser();

            }
        });


    /*    edit_ToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showCalender2();
            }
        });*/

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        date = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(new Date());

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;

                String fmonth=""+month;
                String fDate=""+dayOfMonth;


                if(month<10){
                    fmonth ="0"+month;
                }
                if (dayOfMonth<10){
                    fDate="0"+dayOfMonth;
                }

                month = month+1;
                String date = year+"/"+fmonth+"/"+fDate;
                //String date = year+"-"+month+"-"+day;
                edit_FromDate.setText(date);

            }
        };

        totalPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (edit_FromDate.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Select your Date", Toast.LENGTH_SHORT).show();

                }else{

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    TotalPrintDet totalPrintDet = new TotalPrintDet();
                    Bundle bundle=new Bundle();
                    bundle.putString("DateofCollection", edit_FromDate.getText().toString().trim());
                    bundle.putString("AgentId", userId);
                    totalPrintDet.setArguments(bundle);
                    fragmentTransaction.replace(R.id.nav_host_fragment,totalPrintDet);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    edit_FromDate.setText("");
                }

            }
        });

        return view;
    }

    public void showCalender1(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month = month+1;

                String fmonth=""+month;
                String fDate=""+dayOfMonth;

                if(month<10){
                    fmonth ="0"+month;
                }
                if (dayOfMonth<10){
                    fDate="0"+dayOfMonth;
                }

                date = fDate+"/"+fmonth+"/"+year;
                //String date = year+"-"+month+"-"+day;
                edit_FromDate.setText(date);

                getReport(userId,date);
                //getReport("10","01/04/2023");

            }
        },year,month,day);

        datePickerDialog.show();
    }

    public void showCalender2(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month = month+1;

                String fmonth=""+month;
                String fDate=""+dayOfMonth;

                if(month<10){
                    fmonth ="0"+month;
                }
                if (dayOfMonth<10){
                    fDate="0"+dayOfMonth;
                }

                date = year+"/"+fmonth+"/"+fDate;
                //String date = year+"-"+month+"-"+day;
                edit_ToDate.setText(date);

            }
        },year,month,day);

        datePickerDialog.show();
    }

    public void getReport(String agentId, String date){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();

        String apilistData = Appurl.DailyCollection+"AgentId="+agentId+"&DateofCollection="+date;

        Log.d("showdatedata",apilistData);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apilistData, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String SVCGetAgentCollectionResult = jsonObject.getString("SVCGetAgentCollectionResult");

                    JSONArray jsonArray = new JSONArray(SVCGetAgentCollectionResult);

                    report.clear();

                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String AccountNumber = jsonObject1.getString("AccountNumber");
                        String Active = jsonObject1.getString("Active");
                        String AgentId = jsonObject1.getString("AgentId");
                        String Balance = jsonObject1.getString("Balance");
                        String CreatedDate = jsonObject1.getString("CreatedDate");
                        String DepositAmount = jsonObject1.getString("DepositAmount");
                        String Message = jsonObject1.getString("Message");
                        String Status = jsonObject1.getString("Status");
                        String UName = jsonObject1.getString("UName");
                        String VocherNumber = jsonObject1.getString("VocherNumber");

                        report.add(new Report_ModelClass(CreatedDate,"",AccountNumber,DepositAmount,Balance,VocherNumber,UName));

                    }

                    linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                    reportAdapter = new ReportAdapter(report,getActivity());
                    recyclerReport.setLayoutManager(linearLayoutManager);
                    recyclerReport.setHasFixedSize(true);
                    recyclerReport.setAdapter(reportAdapter);

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
