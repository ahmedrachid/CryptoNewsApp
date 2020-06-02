package com.digger.app.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digger.app.R;
import com.digger.app.adapter.StockAdapter;
import com.digger.app.model.Stock;

import java.util.ArrayList;

public class StockMarketIndicesFragment extends Fragment {
    private DashboardViewModel viewModel;
    private RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel =
                ViewModelProviders.of(this.getActivity()).get(DashboardViewModel.class);

        recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock(1,"bitcoin",new Double(7000),new Double(7500),new Double(5),null));
        stocks.add(new Stock(1,"bitcoin",new Double(7000),new Double(7500),new Double(5),null));

        final StockAdapter adapter = new StockAdapter();

        recyclerView.setAdapter(adapter);

        viewModel.getStockMarketIndices().observe(this, new Observer<ArrayList<Stock>>() {
            @Override
            public void onChanged(ArrayList<Stock> stocks) {
                adapter.setStocks(stocks);
            }
        });



        return recyclerView;
    }
}
