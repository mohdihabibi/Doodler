package edu.sjsu.android.doodle;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    final FragmentManager FM = getSupportFragmentManager();
    private BottomNavigationView BNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BNV = findViewById(R.id.navigation_bar);

        BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new Fragment();
                switch (item.getItemId()) {
                    case R.id.feeds:
                        fragment = new FeedsFrag();
                        break;
                    case R.id.doodling:
                        fragment = new DoodleFrag();
                        break;
                    case R.id.profile:
                        fragment = new ProfileFrag();
                        break;
                    case R.id.logout:
                        Intent i = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(i);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                FM.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        BNV.setSelectedItemId(R.id.feeds);
    }
}
