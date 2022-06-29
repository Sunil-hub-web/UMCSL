package in.co.umcsl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import in.co.fragment.AccountCreationFragment;
import in.co.fragment.CollectAmountFragment;
import in.co.fragment.HomeFragment;
import in.co.fragment.ProfileFragment;
import in.co.fragment.ReportFragment;

public class DeshBoard extends AppCompatActivity {

    public static ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desh_board);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chipNavigationBar = findViewById(R.id.bottomNavigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "HomeFragment").addToBackStack(null).commit();

        chipNavigationBar.setItemSelected(R.id.home, true);

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                Fragment fragment = null;

                switch (i) {

                    case R.id.home:

                        fragment = new HomeFragment();

                        break;

                    case R.id.profile:

                        fragment = new ProfileFragment();

                        break;

                    case R.id.report:

                        fragment = new ReportFragment();

                        break;

                    case R.id.creation:

                        fragment = new AccountCreationFragment();

                        break;

                    case R.id.collectAmount:

                        fragment = new CollectAmountFragment();

                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
            }
        });

    }

    /*@Override
    public void onBackPressed() {
       // super.onBackPressed();

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "HomeFragment").addToBackStack(null).commit();
        chipNavigationBar.setItemSelected(R.id.home, true);

    }*/
}