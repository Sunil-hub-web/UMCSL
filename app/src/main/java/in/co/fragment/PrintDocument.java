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
import androidx.fragment.app.FragmentTransaction;

import in.co.umcsl.DeshBoard;
import in.co.umcsl.R;

public class PrintDocument extends Fragment {

    Button btn_Print;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_print,container,false);

        btn_Print = view.findViewById(R.id.btn_Print);

        btn_Print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment,homeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                DeshBoard.chipNavigationBar.setItemSelected(R.id.home, false);
            }
        });
        return view;
    }
}
