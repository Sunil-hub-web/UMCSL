package in.co.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.async.AsyncBluetoothEscPosPrint;
import in.co.async.AsyncEscPosPrint;
import in.co.async.AsyncEscPosPrinter;
import in.co.extra.Appurl;
import in.co.umcsl.DeshBoard;
import in.co.umcsl.R;

public class PrintDocument extends Fragment {

    Button btn_Print, btn_periviousPage;
    public static final int PERMISSION_BLUETOOTH = 1;

    String date, time, AccountNo, Balance, DepositeAmount, Message, Status, TransactionID, AgentCode, AmounttoText, ApplicantName, Bankname, Chequenumber, Contactnumber, JoiningDate, OpeningBalance, Otherpersonname, Paidby, VocherNumber, vocherNumber, CollectionDate, Youkey;

    TextView text_Branch, text_MSA, text_DateTime, text_Name, text_AccNo, text_Type, text_ACOpenDate, text_CustomerCode, text_TrxnNo, text_OpngBalance, text_RcvdAmount, text_showWrite, text_ClosingBalance;

    public OnBluetoothPermissionsGranted onBluetoothPermissionsGranted;
    public static final int PERMISSION_BLUETOOTH_ADMIN = 2;
    public static final int PERMISSION_BLUETOOTH_CONNECT = 3;
    public static final int PERMISSION_BLUETOOTH_SCAN = 4;
    BluetoothConnection selectedDevice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_print, container, false);

        btn_Print = view.findViewById(R.id.btn_Print);
        btn_periviousPage = view.findViewById(R.id.btn_periviousPage);
        text_Branch = view.findViewById(R.id.text_Branch);
        text_MSA = view.findViewById(R.id.text_MSA);
        text_DateTime = view.findViewById(R.id.text_DateTime);
        text_Name = view.findViewById(R.id.text_Name);
        text_AccNo = view.findViewById(R.id.text_AccNo);
        text_Type = view.findViewById(R.id.text_Type);
        text_ACOpenDate = view.findViewById(R.id.text_ACOpenDate);
        // text_CustomerCode = view.findViewById(R.id.text_CustomerCode);
        text_TrxnNo = view.findViewById(R.id.text_TrxnNo);
        text_OpngBalance = view.findViewById(R.id.text_OpngBalance);
        text_RcvdAmount = view.findViewById(R.id.text_RcvdAmount);
        text_showWrite = view.findViewById(R.id.text_showWrite);
        text_ClosingBalance = view.findViewById(R.id.text_ClosingBalance);

        Bundle arguments = getArguments();

        if (arguments != null) {

            vocherNumber = arguments.get("VocherNumber").toString();
            Youkey = arguments.get("Youkey").toString();

            if (vocherNumber != null) {

                Log.d("VocherNumberqqqq", vocherNumber);
                Log.d("VocherNumberqqqq", vocherNumber);
                printDetails(vocherNumber);

            } else {

                Toast.makeText(getContext(), "vocherNumber not equals null", Toast.LENGTH_SHORT).show();
            }

        }

        btn_Print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment, homeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                DeshBoard.chipNavigationBar.setItemSelected(R.id.home, false);


                doPrint();

               // printBluetooth();
            }
        });

        btn_periviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Youkey.equals("CollectAmount")) {

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    CollectAmountFragment collectAmountFragment = new CollectAmountFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("product_id", "PrintPdf");
                    collectAmountFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.nav_host_fragment, collectAmountFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else {

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    ReportFragment reportFragment = new ReportFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("product_id", "PrintPdf");
                    reportFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.nav_host_fragment, reportFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }


            }
        });

        return view;
    }

    public void printDetails(String vocherNumber) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading.....");
        progressDialog.show();

        String accCreateApi = Appurl.GetPrintTransactionDtl + vocherNumber;

        Log.d("apidetailsss", accCreateApi);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, accCreateApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String SSaveDailyAccountDepositeResult = jsonObject.getString("SVCGetPrintTransactionDtlResult");
                    JSONArray jsonArray = new JSONArray(SSaveDailyAccountDepositeResult);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        AccountNo = jsonObject1.getString("AccountNo");
                        AgentCode = jsonObject1.getString("AgentCode");
                        AmounttoText = jsonObject1.getString("AmounttoText");
                        ApplicantName = jsonObject1.getString("ApplicantName");
                        Balance = jsonObject1.getString("Balance");
                        Bankname = jsonObject1.getString("Bankname");
                        Chequenumber = jsonObject1.getString("Chequenumber");
                        CollectionDate = jsonObject1.getString("CollectionDate");
                        Contactnumber = jsonObject1.getString("Contactnumber");
                        DepositeAmount = jsonObject1.getString("DepositeAmount");
                        JoiningDate = jsonObject1.getString("JoiningDate");
                        OpeningBalance = jsonObject1.getString("OpeningBalance");
                        Message = jsonObject1.getString("Message");
                        Otherpersonname = jsonObject1.getString("Otherpersonname");
                        Paidby = jsonObject1.getString("Paidby");
                        Status = jsonObject1.getString("Status");
                        TransactionID = jsonObject1.getString("TransactionID");
                        VocherNumber = jsonObject1.getString("VocherNumber");
                    }

                    if (Status.equals("1")) {

                        Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();

                        text_Branch.setText("Chandanpur");
                        text_MSA.setText(AgentCode);
                        text_DateTime.setText(CollectionDate);
                        text_Name.setText(ApplicantName);
                        text_AccNo.setText(AccountNo);
                        text_Type.setText("Deposit");
                        text_ACOpenDate.setText(JoiningDate);
                        //   text_CustomerCode.setText(AccountNo);
                        text_TrxnNo.setText(VocherNumber);
                        text_OpngBalance.setText(OpeningBalance);
                        text_RcvdAmount.setText(DepositeAmount);
                        text_showWrite.setText(AmounttoText);
                        text_ClosingBalance.setText(Balance);

                    } else {

                        Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void doPrint() {
        try {
            if (getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.BLUETOOTH}, PERMISSION_BLUETOOTH);
                } else {
                    // Toast.makeText(getActivity(), "Printdetails ", Toast.LENGTH_SHORT).show();
                    BluetoothConnection connection = BluetoothPrintersConnections.selectFirstPaired();
                    if (connection != null) {
                        EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 32);
                        final String text = "[C] <b>UMCSL" + "</b>\n" + "[C] <b>Chandanpur</b>" + "\n" +

                                "[C]================================\n" +

                                "[L]<b> Branch [R]" + "Chandanpur" + "</b>\n" + "[L]<b> MSA    [R]" + AgentCode +
                                "</b>\n" + "[L]<b> Date [R]" + CollectionDate + "</b>\n" +
                                "[L]<b> Name   [R] " + ApplicantName + "</b>\n" +
                                "[L]<b> Ac/no. [R]" + AccountNo + "</b>\n" +
                                "[L]<b> Type   [R]" + "Deposit" + "</b>\n" +
                                "[L]<b> A/C Open Date[R]" + JoiningDate + "</b>\n" +
                                "[L]<b> Trxn No[R]" + VocherNumber + "</b>\n" +
                                "[L]<b> Opng Bal[R]" + OpeningBalance + "</b>\n" +
                                "[L]<b> Recev Amount[R]" + DepositeAmount + "</b>\n" +
                                "[L]<b> Closing Bal[R]" + Balance + "</b>\n" + "[L]\n" +
                                "[C]--------------------------------\n" +
                                "[C]--------------------------------\n" +
                                "[C]<b>" + AmounttoText + "</b>\n" +
                                "[C]<b> Thanks You" + "</b>\n" + "[L]\n"+
                                "[C]                                  \n"+
                                "[C]                                  \n";

                        printer.printFormattedText(text);
                    } else {
                        Toast.makeText(getActivity(), "No printer was connected!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {

                Toast.makeText(getActivity(), "ble_not_supported", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("APP", "Can't print", e);
        }
    }

    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("'on' yyyy-MM-dd 'at' HH:mm:ss");
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 48f, 32);
        return printer.addTextToPrint(
                "[C] <b>UMCSL" + "</b>\n" + "[C] <b>Chandanpur</b>" + "\n" +

                "[C]================================\n" +

                "[L]<b> Branch [R]" + "Chandanpur" + "</b>\n" +
                "[L]<b> MSA    [R]" + AgentCode + "</b>\n" +
                "[L]<b> Date [R]" + CollectionDate + "</b>\n" +
                "[L]<b> Name   [R] " + ApplicantName + "</b>\n" +
                "[L]<b> Ac/no. [R]" + AccountNo + "</b>\n" +
                "[L]<b> Type   [R]" + "Deposit" + "</b>\n" +
                "[L]<b> A/C Open Date[R]" + JoiningDate + "</b>\n" +
                "[L]<b> Trxn No[R]" + VocherNumber + "</b>\n" +
                "[L]<b> Opng Bal[R]" + OpeningBalance + "</b>\n" +
                "[L]<b> Recev Amount[R]" + DepositeAmount + "</b>\n" +
                "[L]<b> Closing Bal[R]" + Balance + "</b>\n" + "[L]\n" +
                "[C]--------------------------------\n" +
                "[C]--------------------------------\n" +
                "[C]<b>" + AmounttoText + "</b>\n" +
                "[C]<b> Thanks You" + "</b>\n" + "[L]\n" );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case PrintDocument.PERMISSION_BLUETOOTH:
                case PrintDocument.PERMISSION_BLUETOOTH_ADMIN:
                case PrintDocument.PERMISSION_BLUETOOTH_CONNECT:
                case PrintDocument.PERMISSION_BLUETOOTH_SCAN:
                    this.checkBluetoothPermissions(this.onBluetoothPermissionsGranted);
                    break;
            }
        }
    }

    public void checkBluetoothPermissions(OnBluetoothPermissionsGranted onBluetoothPermissionsGranted) {
        this.onBluetoothPermissionsGranted = onBluetoothPermissionsGranted;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.BLUETOOTH}, PrintDocument.PERMISSION_BLUETOOTH);
        } else if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.BLUETOOTH_ADMIN}, PrintDocument.PERMISSION_BLUETOOTH_ADMIN);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.BLUETOOTH_CONNECT}, PrintDocument.PERMISSION_BLUETOOTH_CONNECT);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.BLUETOOTH_SCAN}, PrintDocument.PERMISSION_BLUETOOTH_SCAN);
        } else {
            this.onBluetoothPermissionsGranted.onPermissionsGranted();
        }
    }

    public void printBluetooth() {
        this.checkBluetoothPermissions(() -> {
            new AsyncBluetoothEscPosPrint(getContext(), new AsyncEscPosPrint.OnPrintFinished() {
                @Override
                public void onError(AsyncEscPosPrinter asyncEscPosPrinter, int codeException) {
                    Log.e("Async.OnPrintFinished", "AsyncEscPosPrint.OnPrintFinished : An error occurred !");
                }

                @Override
                public void onSuccess(AsyncEscPosPrinter asyncEscPosPrinter) {
                    Log.i("Async.OnPrintFinished", "AsyncEscPosPrint.OnPrintFinished : Print is finished !");
                }
            }).execute(this.getAsyncEscPosPrinter(selectedDevice));
        });
    }

    public interface OnBluetoothPermissionsGranted {
        void onPermissionsGranted();
    }


}
