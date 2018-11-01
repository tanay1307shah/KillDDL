package com.map524s1a.killddl;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.map524s1a.killddl.DailyFragment.*;

/**
 * Created by tanay on 10/15/2018.
 */

public class MonthlyFragment extends Fragment {
    //private DailyFragment.EventListAdapter adapter;
    //private FrameLayout f2;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fm_layout = inflater.inflate(R.layout.activity_monthly,container,false);
        //lv2 = fm_layout.findViewById(R.id.list_view_monthly);
        FragmentManager fm = getChildFragmentManager();
        Fragment f = fm.findFragmentById(R.id.list_frag_container);
        FragmentTransaction ft = fm.beginTransaction();
        if(f == null){
            f = new DailyFragment();
            ft.add(R.id.list_frag_container,f);
            ft.commit();
        }
        else {
            f = new DailyFragment();
            ft.replace(R.id.list_frag_container, f);
            ft.commit();
        }





//        adapter = new DailyFragment.EventListAdapter(getApplicationContext(), 0, events);
//        lv2.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
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
