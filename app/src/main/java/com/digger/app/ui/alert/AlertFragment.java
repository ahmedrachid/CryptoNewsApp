package com.digger.app.ui.alert;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.digger.app.MainActivity;
import com.digger.app.R;
import com.digger.app.adapter.AlertAdapter;
import com.digger.app.model.Alert;
import com.digger.app.query.QueryUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onesignal.OneSignal;

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

        final View emptyView = root.findViewById(R.id.empty_view);

        final AlertAdapter adapter = new AlertAdapter();

        FloatingActionButton addButton = (FloatingActionButton) root.findViewById(R.id.add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertFragment.this.getContext(),AddAlertActivity.class);
                startActivity(intent);
            }
        });






        adapter.setListener(new DeleteListener() {
            @Override
            public void onClick(final Alert alert) {

                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Delete alert")
                        .setMessage("Do you wante to delate this alert ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DeleteAlertAsynTask task = new DeleteAlertAsynTask();
                                task.execute(alert.getId());
                                AlertFragment.this.onResume();

                            }
                        }).setNegativeButton("No",null)
                        .show();

            }
        });



        alertViewModel.getAlerts().observe(this, new Observer<ArrayList<Alert>>() {
            @Override
            public void onChanged(ArrayList<Alert> alerts) {
                adapter.setAlerts(alerts);
                adapter.notifyDataSetChanged();
                if (alerts.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }

            }
        });

        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        alertViewModel.refreshAlerts();
    }

    private class DeleteAlertAsynTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... id) {
            String link = "https://ahmed-alerts.ndogga.com/deletealert/"+id[0];

            QueryUtils.deleteAlert("https://ahmed-alerts.ndogga.com/deletealert/"+id[0]);
                 return null;

        }

        @Override
        protected  void onPostExecute(Void result){

        }
    }

}
