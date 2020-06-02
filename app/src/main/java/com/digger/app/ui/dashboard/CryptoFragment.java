package com.digger.app.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digger.app.adapter.StockAdapter;
import com.digger.app.model.Stock;
import com.digger.app.ui.alert.AlertFragment;

import java.util.ArrayList;

public class CryptoFragment extends Fragment {

    private DashboardViewModel viewModel;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel =
                ViewModelProviders.of(this.getActivity()).get(DashboardViewModel.class);

        recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        final StockAdapter adapter = new StockAdapter();

        recyclerView.setAdapter(adapter);

        viewModel.getCrypto().observe(this, new Observer<ArrayList<Stock>>() {
            @Override
            public void onChanged(ArrayList<Stock> stocks) {
                    adapter.setStocks(stocks);
                    adapter.notifyDataSetChanged();
            }
        });

        adapter.setListener(new StockListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(CryptoFragment.this.getContext(), ChartFragment.class);
                startActivity(intent);
            }
        });



        return recyclerView;
    }
}
