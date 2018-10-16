package com.map524s1a.killddl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainViewActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private FloatingActionButton addBtn;


//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.list:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_time:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        mTextMessage = (TextView) findViewById(R.id.message);
        addBtn = findViewById(R.id.floatingActionButton);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);


        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);
        FragmentTransaction ft = fm.beginTransaction();
        if(f == null){
            f = new MonthlyFragment();
            ft.add(R.id.fragment_container,f);
            ft.commit();
        }
        else {
            f = new MonthlyFragment();
            ft.replace(R.id.fragment_container, f);
            ft.commit();
        }


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainViewActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_create_new_event,null);
                builder.setView(mView);
                AlertDialog dialog =  builder.create();
                dialog.show();
            }

        });




      //  navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
      //  navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
