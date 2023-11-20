package in.co.umcsl;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import in.co.fragment.AccountCreationFragment;
import in.co.fragment.CollectAmountFragment;
import in.co.fragment.HomeFragment;
import in.co.fragment.PrintDocument;
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

        if (ContextCompat.checkSelfPermission(DeshBoard.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(DeshBoard.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                return;
            }
        }

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

    private static final String[] BLE_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    private static final String[] ANDROID_12_BLE_PERMISSIONS = new String[]{
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    public static void requestBlePermissions(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            ActivityCompat.requestPermissions(activity, ANDROID_12_BLE_PERMISSIONS, requestCode);
        else
            ActivityCompat.requestPermissions(activity, BLE_PERMISSIONS, requestCode);
    }


}