package in.co.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import in.co.adapter.ReportAdapter;
import in.co.extra.Appurl;
import in.co.modelclass.Report_ModelClass;
import in.co.umcsl.R;

public class TotalPrintDet extends Fragment {

    RecyclerView recyclerReport;
    LinearLayoutManager linearLayoutManager;
    ReportAdapter reportAdapter;
    ArrayList<Report_ModelClass> report = new ArrayList<>();
    String agentId, date;
    MaterialButton btn_TotalPrint, btn_BackOption;
    public static final int PERMISSION_BLUETOOTH = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.totalprintdetails, container, false);

        recyclerReport = view.findViewById(R.id.recyclerReport);
        btn_TotalPrint = view.findViewById(R.id.btn_TotalPrint);
        btn_BackOption = view.findViewById(R.id.btn_BackOption);
        Bundle arguments = getArguments();

        if (arguments != null) {

            agentId = arguments.get("AgentId").toString();
            date = arguments.get("DateofCollection").toString();

            getReport(agentId, date);

            btn_BackOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    ReportFragment reportFragment = new ReportFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("product_id", "PrintPdf");
                    bundle.putString("DateofCollection", date);
                    reportFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.nav_host_fragment, reportFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });

            btn_TotalPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("reportSizecll", String.valueOf(report.size()));

                    if (report.size() != 0) {

                        String datalist1 = Arrays.toString(report.toArray());

                        String datalist2 = datalist1.replace("[", "");
                        String datalist3 = datalist2.replace("]", "");
                        String datalist4 = datalist3.replace(", ", "");

                        doPrint(datalist4);

                        Log.d("userdatalist", datalist4);

                        for (int i = 0; i < report.size(); i++) {

                           /* doPrint(report.get(i).getDate(),report.get(i).getUName(),report.get(i).getAccountNo(),
                                    report.get(i).getCollectAmount(),report.get(i).getBalanceAmount());*/


                        }
                    }

                }
            });

        }

        return view;
    }

    public void getReport(String agentId, String date) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();

        String apilistData = Appurl.DailyCollection + "AgentId=" + agentId + "&DateofCollection=" + date;

        Log.d("showdatedata", apilistData);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apilistData, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String SVCGetAgentCollectionResult = jsonObject.getString("SVCGetAgentCollectionResult");

                    JSONArray jsonArray = new JSONArray(SVCGetAgentCollectionResult);

                    report.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {

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

                        report.add(new Report_ModelClass(CreatedDate, "", AccountNumber, DepositAmount, Balance, VocherNumber, UName));

                    }

                    linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    reportAdapter = new ReportAdapter(report, getActivity());
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
                Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void doPrint(String printtext) {
        try {
            if (getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.BLUETOOTH}, PERMISSION_BLUETOOTH);
                } else {
                    BluetoothConnection connection = BluetoothPrintersConnections.selectFirstPaired();
                    if (connection != null) {
                        EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 30);
                        final String text =
                                "[L]<b> Date [R]" + "collectDate" + "</b>\n" +
                                        "[L]<b> Name   [R] " + "name" + "</b>\n" +
                                        "[L]<b> Ac/no. [R]" + "AccNo" + "</b>\n" +
                                        "[L]<b> Collect Amount[R]" + "collectAmount" + "</b>\n" +
                                        "[L]<b> Balance Amount[R]" + "balAmount" + "</b>\n"
                                   /* "[L]\n" +
                                    "[C]--------------------------------\n" +
                                    "[C]--------------------------------\n" +
                                    "[C]<b>" + AmounttoText + "</b>\n" +
                                    "[C]<b> Thanks You" + "</b>\n" +
                                    "[L]\n"*/;

                        printer.printFormattedText(printtext);
                    } else {
                        Toast.makeText(getActivity(), "No printer was connected!", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {

                Toast.makeText(getActivity(), "bluetooth_not_supported", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("APP", "Can't print", e);
        }
    }
}
