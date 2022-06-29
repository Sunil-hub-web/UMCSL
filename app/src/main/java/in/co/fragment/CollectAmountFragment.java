package in.co.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.co.umcsl.R;

public class CollectAmountFragment extends Fragment {

    Spinner spinner_PaidBy,spinner_PaymentMode;
    String paidBy[] = {"--Paid By--","Self","Relatives","Others"};
    String paymentMode[] = {"--Payment Mode--","Online","Cash"};
    Button btn_Save,btn_Show;
    EditText edit_Date;
    TextInputLayout editDate;
    int year, month, day, hour, minute;
    String date,time;

    DatePickerDialog.OnDateSetListener setListener;

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

        name_text.setText("Collect Amount");

        ArrayAdapter paymentModeAdapter = new ArrayAdapter(getActivity(), R.layout.spinneritem, paymentMode);
        paymentModeAdapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
        spinner_PaymentMode.setAdapter(paymentModeAdapter);
        spinner_PaymentMode.setSelection(-1, true);

        ArrayAdapter paidByAdapter = new ArrayAdapter(getActivity(), R.layout.spinneritem, paidBy);
        paidByAdapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
        spinner_PaidBy.setAdapter(paidByAdapter);
        spinner_PaidBy.setSelection(-1, true);

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                PrintDocument printDocument = new PrintDocument();
                fragmentTransaction.replace(R.id.nav_host_fragment,printDocument);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                ShowAccount showAccount = new ShowAccount();
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
}
