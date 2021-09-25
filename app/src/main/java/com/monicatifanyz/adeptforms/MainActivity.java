package com.monicatifanyz.adeptforms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    BottomAppBar bottomAppBar;
    FloatingActionButton floatingActionButton;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // kita set default nya Home Fragment
//        loadFragment(new HomeFragment());
//    // inisialisasi BottomNavigaionView
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigationbar);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
//                Fragment fragment = null;
//                switch (item.getItemId()){
//                    case R.id.home_menu:
//                        fragment = new HomeFragment();
//                        break;
//                    case R.id.account_menu:
//                        fragment = new AccountFragment();
//                        break;
//                    case R.id.setting_menu:
//                        fragment = new SettingsFragment();
//                        break;
//                    case R.id.file_menu:
//                        fragment = new DocsFragment();
//                        break;
//                    case R.id.scan_menu:
//                        fragment = new ScanFragment();
//                        break;
//                }
//                return loadFragment(fragment);
//            }
//        });

    }

//    private boolean loadFragment(Fragment fragment) {
//        if (fragment != null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fl_container, fragment)
//                    .commit();
//            return true;
//        }
//        return false;
//    }


}