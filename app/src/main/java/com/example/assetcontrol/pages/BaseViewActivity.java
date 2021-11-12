package com.example.assetcontrol.pages;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.assetcontrol.R;
import com.example.assetcontrol.pages.history.HistoryFragment;
import com.example.assetcontrol.pages.home.HomeFragment;
import com.example.assetcontrol.pages.inventory.InventoryFragment;
import com.example.assetcontrol.pages.master.MasterFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class BaseViewActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_view_activity);

        chipNavigationBar = findViewById(R.id.navigation);
        chipNavigationBar.setItemSelected(R.id.home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_view, new HomeFragment()).commit();
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.master:
                        fragment = new MasterFragment();
                        break;
                    case R.id.inventory:
                        fragment = new InventoryFragment();
                        break;
                    case R.id.history:
                        fragment = new HistoryFragment();
                        break;
                }
                if (fragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_view, fragment).commit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        } else {
            backToast = Toast.makeText(this, "Tekan Lagi Untuk Keluar", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}