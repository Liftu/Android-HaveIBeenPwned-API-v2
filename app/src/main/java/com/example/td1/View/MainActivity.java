package com.example.td1.View;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.td1.R;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.activity_main_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> updateMainFragment(item.getItemId()));

        goTo(new MainFragment());
    }

    public void goTo(Fragment fragment) {
        currentFragment = fragment;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    public void goToWithBackStack(Fragment fragment) {
        currentFragment = fragment;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    private Boolean updateMainFragment(Integer menuItem) {
        switch (menuItem) {
            case R.id.action_list:
                if (!(currentFragment instanceof MainFragment)) {
                    goToWithBackStack(new MainFragment());
                }
                break;

            case R.id.action_check:
//                if (!(currentFragment instanceof checkFragment)) {
//                    goToWithBackStack(new checkFragment);
//                }
                //goTo(new BreachFragment());
                break;

            case R.id.action_graph:
//                if (!(currentFragment instanceof graphFragment)) {
//                    goToWithBackStack(new graphFragment);
//                }
                //this.mainFragment.updateDesignWhenUserClickedBottomView(MainFragment.REQUEST_GRAPH);
                break;

        }
        return true;
    }
}
