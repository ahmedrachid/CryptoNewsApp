package com.digger.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digger.app.R;
import com.digger.app.model.Alert;
import com.digger.app.model.Stock;
import com.digger.app.ui.alert.DeleteListener;
import com.digger.app.ui.dashboard.StockListener;

import java.util.ArrayList;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.ViewHolder> {

    private ArrayList<Alert> alerts = new ArrayList<Alert>();

    private DeleteListener listener;

    public void setListener(DeleteListener listener){
        this.listener =listener;
    }

    public void setAlerts(ArrayList<Alert> alerts) {
        this.alerts = alerts;
    }

    @NonNull
    @Override
    public AlertAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View stockViewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_item,parent,false);
        return new AlertAdapter.ViewHolder(stockViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertAdapter.ViewHolder holder, final int position) {
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(alerts.get(position));
            }
        });
        holder.setAlertItemView(alerts.get(position));
    }

    @Override
    public int getItemCount() {
        return alerts.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private View alertItem;

        private View.OnClickListener clickListener;

        void setOnClickListener(View.OnClickListener onClickListener) {
            clickListener = onClickListener;
        }



        void setAlertItemView(Alert alert) {


            TextView stockName = alertItem.findViewById(R.id.stock_name_item);
            stockName.setText(alert.getStock());

            TextView more = alertItem.findViewById(R.id.more_less_item);
            boolean isMore = alert.isMore();
            if(isMore)
                more.setText(">");
            else
                more.setText("<");
            TextView value = alertItem.findViewById(R.id.value_item);
            value.setText(Double.toString(alert.getValue()));
            alertItem.setOnClickListener(clickListener);
        }

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.alertItem = itemView;
        }

    }
}
