package in.co.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.co.adapter.AssignCustomerAdapter;
import in.co.adapter.ReportAdapter;
import in.co.modelclass.AssignCustomer_ModelClass;
import in.co.modelclass.Report_ModelClass;
import in.co.umcsl.R;

public class AssignCustomerFragment extends Fragment {

    RecyclerView recyclerAssignReport;
    LinearLayoutManager linearLayoutManager;
    AssignCustomerAdapter assignCustomerAdapter;
    ArrayList<AssignCustomer_ModelClass> assigncustomer = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assigncustomer,container,false);

        recyclerAssignReport = view.findViewById(R.id.recyclerAssignReport);

        TextView name_text = view.findViewById(R.id.name_text);

        name_text.setText("Assign Customer");

        assigncustomer.add(new AssignCustomer_ModelClass("13 jan 2014","1002","Basanti Sahoo","1234567890","Active"));
        assigncustomer.add(new AssignCustomer_ModelClass("19 fb 2014","1031","Basanta Nayak","1234567890","Active"));
        assigncustomer.add(new AssignCustomer_ModelClass("21 feb 2014","1039","Bira Baho","1234567890","Active"));
        assigncustomer.add(new AssignCustomer_ModelClass("06 mar 2014","1071","Sukanti Khatei","1234567890","Active"));
        assigncustomer.add(new AssignCustomer_ModelClass("19 dec 2015","1072","Basanti Sahoo","1234567890","Active"));

        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        assignCustomerAdapter = new AssignCustomerAdapter(assigncustomer,getActivity());
        recyclerAssignReport.setLayoutManager(linearLayoutManager);
        recyclerAssignReport.setHasFixedSize(true);
        recyclerAssignReport.setAdapter(assignCustomerAdapter);

        return view;
    }
}
