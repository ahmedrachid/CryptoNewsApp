package com.digger.app.adapter;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digger.app.R;
import com.digger.app.model.Stock;
import com.digger.app.ui.alert.AddAlertActivity;
import com.digger.app.ui.alert.AlertFragment;
import com.digger.app.ui.dashboard.ChartFragment;
import com.digger.app.ui.dashboard.StockListener;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {
    private ArrayList<Stock> mStocks = new ArrayList<Stock>();

    private StockListener listener;

    public void setListener(StockListener listener) {
        this.listener = listener;
    }

    public ArrayList<Stock> getStocks() {
        return mStocks;
    }

    public void setStocks(ArrayList<Stock> mStocks) {
        this.mStocks = mStocks;
    }



    @NonNull
    @Override
    public StockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View stockViewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_item,parent,false);
        return new ViewHolder(stockViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdapter.ViewHolder holder, final int position) {
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick();
            }
        });
        holder.setStockItemView(mStocks.get(position));

    }

    @Override
    public int getItemCount() {
        return mStocks.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private View stockItem;
        private View.OnClickListener clickListener;

        void setOnClickListener(View.OnClickListener onClickListener) {
            clickListener = onClickListener;
        }


        void setStockItemView(Stock stock) {

            TextView currentValue = stockItem.findViewById(R.id.current_value);
            currentValue.setText(Double.toString(stock.getMCurrentValue()));

            TextView open = stockItem.findViewById(R.id.open_value);
            open.setText(Double.toString(stock.getMOpen()));

            TextView percentage = stockItem.findViewById(R.id.difference_percentage);
            percentage.setText(Double.toString(stock.getMPercentage()) + "%");

            stockItem.setOnClickListener(clickListener);
        }

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.stockItem = itemView;
        }

    }






//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent){
//
//        View listViewItem = convertView;
//
//
//        if(listViewItem == null) {
//            listViewItem = LayoutInflater.from(getContext()).inflate(
//                   R.layout.stock_item, parent, false);
//        }
//
//        Stock currentStock = getItem(position);
//
//        TextView currentValue = listViewItem.findViewById(R.id.current_value);
//        currentValue.setText( Double.toString(currentStock.getMCurrentValue()));
//
//
//        TextView open = listViewItem.findViewById(R.id.open_value);
//        open.setText(Double.toString(currentStock.getMOpen()));
//
//        TextView percentage = listViewItem.findViewById(R.id.difference_percentage);
//        percentage.setText(Double.toString(currentStock.getMPercentage())+"%");
//
//
//
//        return listViewItem;
//    }



}
