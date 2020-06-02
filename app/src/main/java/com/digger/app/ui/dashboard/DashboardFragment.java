package com.digger.app.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.digger.app.R;
import com.digger.app.adapter.MyPagerAdapter;
import com.digger.app.model.Stock;
import com.digger.app.ui.alert.AlertFragment;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel;

    MyPagerAdapter pagerAdapter;
    ViewPager viewPager;

    @Override
    public void onCreate (Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        viewModel =
                ViewModelProviders.of(this.getActivity()).get(DashboardViewModel.class);
        viewModel.fetchDataStock();


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pagerAdapter = new MyPagerAdapter (getChildFragmentManager());
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
    }
}
