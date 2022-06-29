package in.co.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.co.adapter.ReportAdapter;
import in.co.modelclass.Report_ModelClass;
import in.co.umcsl.R;

public class ReportFragment extends Fragment {

    RecyclerView recyclerReport;
    LinearLayoutManager linearLayoutManager;
    ReportAdapter reportAdapter;
    ArrayList<Report_ModelClass> report = new ArrayList<>();
    EditText edit_ToDate,edit_FromDate;
    int year, month, day, hour, minute;
    String date,time;
    DatePickerDialog.OnDateSetListener setListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report,container,false);

        recyclerReport = view.findViewById(R.id.recyclerReport);
        edit_ToDate = view.findViewById(R.id.edit_ToDate);
        edit_FromDate = view.findViewById(R.id.edit_FromDate);

        TextView name_text = view.findViewById(R.id.name_text);

        name_text.setText("Report");

        report.add(new Report_ModelClass("29.4.2022","12:41","1002","Rs. 1200","Rs. 3000"));
        report.add(new Report_ModelClass("15.3.2022","11:41","1031","Rs. 1000","Rs. 2000"));
        report.add(new Report_ModelClass("11.1.2022","1:42","1039","Rs. 1500","Rs. 4000"));
        report.add(new Report_ModelClass("25.8.2021","10:31","1071","Rs. 1800","Rs. 5000"));
        report.add(new Report_ModelClass("22.8.2021","9:41","1072","Rs. 1300","Rs. 2500"));


        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        reportAdapter = new ReportAdapter(report,getActivity());
        recyclerReport.setLayoutManager(linearLayoutManager);
        recyclerReport.setHasFixedSize(true);
        recyclerReport.setAdapter(reportAdapter);

        edit_FromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCalender1();
            }
        });

        edit_ToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCalender2();
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
                edit_FromDate.setText(date);

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
}
