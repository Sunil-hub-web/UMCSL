package in.co.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import in.co.umcsl.DeshBoard;
import in.co.umcsl.R;

public class HomeFragment extends Fragment {

    Button btn_AccountCreation,btn_CollectAmount,btn_Report,btn_Profile,btn_AssignCustomer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        TextView name_text = view.findViewById(R.id.name_text);
        btn_Profile = view.findViewById(R.id.btn_Profile);
        btn_AccountCreation = view.findViewById(R.id.btn_AccountCreation);
        btn_CollectAmount = view.findViewById(R.id.btn_CollectAmount);
        btn_Report = view.findViewById(R.id.btn_Report);
        btn_AssignCustomer = view.findViewById(R.id.btn_AssignCustomer);

        name_text.setText("Home");

        btn_AccountCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                AccountCreationFragment accountCreationFragment = new AccountCreationFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment,accountCreationFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                DeshBoard.chipNavigationBar.setItemSelected(R.id.creation, true);
            }
        });

        btn_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                ProfileFragment profileFragment = new ProfileFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment,profileFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                DeshBoard.chipNavigationBar.setItemSelected(R.id.profile, true);
            }
        });

        btn_CollectAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                CollectAmountFragment collectAmountFragment = new CollectAmountFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment,collectAmountFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                DeshBoard.chipNavigationBar.setItemSelected(R.id.collectAmount, true);
            }
        });

        btn_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                ReportFragment reportFragment = new ReportFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment,reportFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                DeshBoard.chipNavigationBar.setItemSelected(R.id.report, true);
            }
        });

        btn_AssignCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                AssignCustomerFragment assignCustomerFragment = new AssignCustomerFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment,assignCustomerFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                DeshBoard.chipNavigationBar.setItemSelected(R.id.home, false);
            }
        });

        return view;
    }
}
