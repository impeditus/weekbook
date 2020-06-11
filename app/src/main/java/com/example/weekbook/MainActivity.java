package com.example.weekbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

private  WeekPagerAdapter weekPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         weekPagerAdapter = new WeekPagerAdapter(getSupportFragmentManager(), 	1, this );

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(weekPagerAdapter);
        viewPager.setCurrentItem(4);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) searchItem.getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.settings:
                return true;
            case R.id.about:
                showAbout();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    public void showAbout() {
        FragmentManager manager = getSupportFragmentManager();
        AboutDialogFragment aboutDialogFragment = new AboutDialogFragment();
        aboutDialogFragment.show(manager, "myDialog");
    }

    }

