package com.example.assetcontrol.pages.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.assetcontrol.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class BaseAuthActivity extends AppCompatActivity {

    private ChipNavigationBar chipnav;
    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_auth_activity);

        chipnav = findViewById(R.id.navigation);

        chipnav.setItemSelected(R.id.login, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment()).commit();
        chipnav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.login:
                        fragment = new LoginFragment();
                        break;
                    case R.id.register:
                        fragment = new RegisterFragment();
                        break;
                }
                if (fragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        });

    }
}