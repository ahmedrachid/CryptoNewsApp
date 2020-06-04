package com.digger.app.ui.alert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digger.app.R;
import com.digger.app.adapter.AlertAdapter;
import com.digger.app.adapter.StockAdapter;
import com.digger.app.model.Alert;
import com.digger.app.model.Stock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AlertFragment extends Fragment {

    private AlertViewModel alertViewModel;

    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_alert, container, false);
        alertViewModel =
                ViewModelProviders.of(this).get(AlertViewModel.class);
        recyclerView = root.findViewById(R.id.list_alert);

        final AlertAdapter adapter = new AlertAdapter();

        FloatingActionButton addButton = (FloatingActionButton) root.findViewById(R.id.add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertFragment.this.getContext(),AddAlertActivity.class);
                startActivity(intent);
            }
        });

        alertViewModel.getAlerts().observe(this, new Observer<ArrayList<Alert>>() {
            @Override
            public void onChanged(ArrayList<Alert> alerts) {
                adapter.setAlerts(alerts);
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(adapter);
        return root;
    }


}
