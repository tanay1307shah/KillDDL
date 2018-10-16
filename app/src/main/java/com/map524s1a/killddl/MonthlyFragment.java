package com.map524s1a.killddl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tanay on 10/15/2018.
 */

public class MonthlyFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fm_layout = inflater.inflate(R.layout.activity_monthly,container,false);


//        FragmentManager fm = getFragmentManager();
//        Fragment f = fm.findFragmentById(R.id.fragment_container_monthly);
//        FragmentTransaction ft = fm.beginTransaction();
//        if(f == null){
//            f = new MonthlyFragment();
//            ft.add(R.id.fragment_container_monthly,f);
//            ft.commit();
//        }
//        else {
//            f = new MonthlyFragment();
//            ft.replace(R.id.fragment_container_monthly, f);
//            ft.commit();
//        }

        return fm_layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}
