package in.co.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import in.co.extra.Appurl;
import in.co.extra.SessionManager;
import in.co.umcsl.R;

public class CollectAmountFragment extends Fragment {

    Spinner spinner_PaidBy,spinner_PaymentMode;
    String paidBy[] = {"Self","Relatives","Others"};
    String paymentMode[] = {"Cash","Online"};
    Button btn_Save,btn_Show;
    EditText edit_Date,edit_AccountNo,edit_VocherNumber,edit_DepositAmount,edit_FixAccount;
    TextInputLayout editDate;
    int year, month, day, hour, minute;
    String date,time, AccountNo,Balance,DepositeAmount,Message,Status,TransactionID,str_Date,str_AccountNo,
            str_VocherNumber,str_DepositAmount,str_spinner_PaidBy,str_spinner_PaymentMode,str_SMSAllow,AgentCode,
            AmounttoText,ApplicantName,Bankname,Chequenumber,Contactnumber,JoiningDate, OpeningBalance,Otherpersonname,
            Paidby1,VocherNumber1,userId;

    DatePickerDialog.OnDateSetListener setListener;

    RadioButton selectedradioButton;
    RadioGroup radioGroup;
    int selectedId;
    ImageView logout_bar_img;
    SessionManager sessionManager;
    TextView errorshow;
    int min = 10;
    int max = 999;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_collamount,container,false);

        TextView name_text = view.findViewById(R.id.name_text);
        spinner_PaymentMode = view.findViewById(R.id.spinner_PaymentMode);
        spinner_PaidBy = view.findViewById(R.id.spinner_PaidBy);
        btn_Save = view.findViewById(R.id.btn_Save);
        btn_Show = view.findViewById(R.id.btn_Show);
        edit_Date = view.findViewById(R.id.edit_Date);
        editDate = view.findViewById(R.id.editDate);
        edit_AccountNo = view.findViewById(R.id.edit_AccountNo);
        edit_VocherNumber = view.findViewById(R.id.edit_VocherNumber);
        edit_DepositAmount = view.findViewById(R.id.edit_DepositAmount);
        radioGroup = view.findViewById(R.id.radioGroupSms);
        logout_bar_img = view.findViewById(R.id.logout_bar_img);
        errorshow = view.findViewById(R.id.errorshow);
        edit_FixAccount = view.findViewById(R.id.edit_FixAccount);

        name_text.setText("Collect Amount");



        ArrayAdapter paymentModeAdapter = new ArrayAdapter(getActivity(), R.layout.spinneritem, paymentMode);
        paymentModeAdapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
        spinner_PaymentMode.setAdapter(paymentModeAdapter);
        spinner_PaymentMode.setSelection(-1, true);

        ArrayAdapter paidByAdapter = new ArrayAdapter(getActivity(), R.layout.spinneritem, paidBy);
        paidByAdapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
        spinner_PaidBy.setAdapter(paidByAdapter);
        spinner_PaidBy.setSelection(-1, true);

        sessionManager = new SessionManager(getContext());

        String date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        edit_Date.setText(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d = new Date();
        String result = sdf.format(d);
        int b = (int)(Math.random()*(max-min+1)+min);

        String number = String.valueOf(b);
        String rendom_number = "v"+result+number;
        edit_VocherNumber.setText(rendom_number);

        sessionManager = new SessionManager(getContext());
        userId = sessionManager.getUserID();

        Bundle arguments = getArguments();

        if (arguments!=null){

            String message = arguments.get("product_id").toString();

            if (message.equals("PrintPdf")){

                edit_AccountNo.setText("");
               // edit_VocherNumber.setText("");
                edit_DepositAmount.setText("");
            }

        }

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_spinner_PaidBy = spinner_PaidBy.getSelectedItem().toString();
                str_spinner_PaymentMode = spinner_PaymentMode.getSelectedItem().toString();

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                selectedradioButton = (RadioButton) view.findViewById(selectedId);

                if(edit_AccountNo.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(), "Enter Account No", Toast.LENGTH_SHORT).show();
                } else if (edit_Date.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Select Your Date", Toast.LENGTH_SHORT).show();
                }else if (edit_DepositAmount.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Enter Your Deposit Amount", Toast.LENGTH_SHORT).show();
                }else if (selectedId == -1) {
                    Toast.makeText(getActivity(), "please select sms option", Toast.LENGTH_SHORT).show();
                }else if (edit_VocherNumber.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "set You VocherNumber", Toast.LENGTH_SHORT).show();
                }else {

                    String accno = edit_AccountNo.getText().toString().trim();
                    String accno1 = edit_FixAccount.getText().toString().trim();
                    str_AccountNo = accno1+accno;

                  //  str_AccountNo = edit_AccountNo.getText().toString().trim();
                    str_Date = edit_Date.getText().toString().trim();
                    str_DepositAmount = edit_DepositAmount.getText().toString().trim();
                    str_SMSAllow = selectedradioButton.getText().toString();
                    str_VocherNumber = edit_VocherNumber.getText().toString().trim();

                    amountCollect(str_AccountNo,str_Date,str_SMSAllow,str_spinner_PaymentMode,str_DepositAmount,
                            str_DepositAmount,"123",str_spinner_PaidBy,str_VocherNumber);
                }


            }
        });
        btn_Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String accno = edit_AccountNo.getText().toString().trim();
                String accno1 = edit_FixAccount.getText().toString().trim();
                String totalacc = accno1+accno;

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                ShowAccount showAccount = new ShowAccount();
                Bundle bundle=new Bundle();
                bundle.putString("product_id",totalacc);
                showAccount.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment,showAccount);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });
        edit_Date.setOnClickListener(new View.OnClickListener() {
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

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        date = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(new Date());

       /* setListener = new DatePickerDialog.OnDateSetListener() {
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
                edit_Date.setText(date);

            }
        };*/

        edit_AccountNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!edit_AccountNo.getText().toString().trim().equals("")){

                    String accno = edit_AccountNo.getText().toString().trim();
                    String accno1 = edit_FixAccount.getText().toString().trim();
                    String totalacc = accno1+accno;
                    Log.d("alldetails",totalacc);
                    assignCustomer(totalacc,userId);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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

                date = year+"/"+fmonth+"/"+fDate;
                //String date = year+"-"+month+"-"+day;
                edit_Date.setText(date);

            }
        },year,month,day);

        datePickerDialog.show();
    }

    public void amountCollect(String Accountno,String DateOfCollection,String SmsAllow,String PaymentMode,
                              String WithdrawalAmount,String DepositsAmount,String TranscationId,String Paidby,String VocherNumber){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading.....");
        progressDialog.show();

        String accCreateApi = Appurl.AccountDeposite+"Accountno="+Accountno+"&DateOfCollection="+DateOfCollection+"&SmsAllow="+SmsAllow+"&PaymentMode="
                +PaymentMode+"&DepositsAmount="+DepositsAmount+"&VocherNumber="+VocherNumber+"&Bankname="+""+"&Chequenumber="+""+"&Paidby="
                +Paidby+"&Otherpersonname="+""+"&Contactnumber="+"";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, accCreateApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String SSaveDailyAccountDepositeResult = jsonObject.getString("SSaveDailyAccountDepositeResult");
                    JSONArray jsonArray = new JSONArray(SSaveDailyAccountDepositeResult);

                    if(jsonArray.length() != 0 ){

                        for (int i=0;i< jsonArray.length();i++){

                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                            AccountNo = jsonObject1.getString("AccountNo");
                            AgentCode = jsonObject1.getString("AgentCode");
                            AmounttoText = jsonObject1.getString("AmounttoText");
                            ApplicantName = jsonObject1.getString("ApplicantName");
                            Balance = jsonObject1.getString("Balance");
                            Bankname = jsonObject1.getString("Bankname");
                            Chequenumber = jsonObject1.getString("Chequenumber");
                            Contactnumber = jsonObject1.getString("Contactnumber");
                            DepositeAmount = jsonObject1.getString("DepositeAmount");
                            JoiningDate = jsonObject1.getString("JoiningDate");
                            OpeningBalance = jsonObject1.getString("OpeningBalance");
                            Message = jsonObject1.getString("Message");
                            Otherpersonname = jsonObject1.getString("Otherpersonname");
                            Paidby1 = jsonObject1.getString("Paidby");
                            Status = jsonObject1.getString("Status");
                            TransactionID = jsonObject1.getString("TransactionID");
                            VocherNumber1 = jsonObject1.getString("VocherNumber");

                        }

                        if (Status.equals("1")){

                            Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();

                            if (VocherNumber1.equals("")){

                                Toast.makeText(getActivity(), "VocherNumber Not Provide To print", Toast.LENGTH_SHORT).show();

                                edit_Date.setText("");
                                edit_AccountNo.setText("");
                                edit_VocherNumber.setText("");
                                edit_DepositAmount.setText("");

                            }else{

                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                PrintDocument printDocument = new PrintDocument();
                                Bundle bundle=new Bundle();
                                bundle.putString("VocherNumber", VocherNumber);
                                bundle.putString("Youkey", "CollectAmount");
                                printDocument.setArguments(bundle);
                                fragmentTransaction.replace(R.id.nav_host_fragment,printDocument);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                                edit_Date.setText("");
                                edit_AccountNo.setText("");
                                edit_VocherNumber.setText("");
                                edit_DepositAmount.setText("");
                            }



                        }else{

                            Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
                        }
                    }else{

                        Toast.makeText(getActivity(), "Data Not Found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(),""+error, Toast.LENGTH_SHORT).show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void assignCustomer(String AccountNumber, String AgentId){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading.....");
        progressDialog.show();

        String assignCustomer = Appurl.SVCCheckAgentAccountExists+"AccountNumber="+AccountNumber+"&AgentId="+AgentId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, assignCustomer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String SVCCheckAgentAccountExistsResult = jsonObject1.getString("SVCCheckAgentAccountExistsResult");
                    JSONArray jsonArray = new JSONArray(SVCCheckAgentAccountExistsResult);

                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String Message = jsonObject.getString("Message");
                        String Status = jsonObject.getString("Status");

                        if (Status.equals("0")){

                            Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();

                            errorshow.setText(Message);

                        }else{

                            Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();

                            errorshow.setText(Message);
                            errorshow.setTextColor(Color.GREEN);
                        }
                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(),""+error, Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}
