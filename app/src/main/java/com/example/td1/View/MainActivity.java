package com.example.td1.View;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.td1.R;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.activity_main_bottom_navigation);
        bottomNavigationView.setOnNavigationItemReselectedListener(item -> updateMainFragment(item.getItemId()));

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_main, new MainFragment());
        ft.commit();
    }

    private Boolean updateMainFragment(Integer integer) {
        switch (integer) {
            case R.id.action_list:
                //this.mainFragment.updateDesignWhenUserClickedBottomView(MainFragment.REQUEST_LIST);
                break;

            case R.id.action_check:
                //this.mainFragment.updateDesignWhenUserClickedBottomView(MainFragment.REQUEST_check);
                break;

            case R.id.action_graph:
                //this.mainFragment.updateDesignWhenUserClickedBottomView(MainFragment.REQUEST_GRAPH);
                break;

        }
        return true;
    }
}
