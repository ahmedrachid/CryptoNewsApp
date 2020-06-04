package com.digger.app.ui.dashboard;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.digger.app.R;
import com.digger.app.model.Stock;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class ChartFragment  extends FragmentActivity {

    LineChart mpLineChart;
    ChartViewModel viewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_line);


        mpLineChart = (LineChart)  findViewById(R.id.line_chart);


        viewModel = new ChartViewModel();

        viewModel.dataValues().observe(this, new Observer<ArrayList<Entry>>() {
            @Override
            public void onChanged(ArrayList<Entry> stocks) {
                LineDataSet lineDataSet = new LineDataSet(stocks,"Data Set 1");


                final ArrayList<String> xLabel = viewModel.getLabels().getValue();

                XAxis xAxis = mpLineChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                xAxis.setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return xLabel.get((int)value);
                    }
                });

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(lineDataSet);

                LineData data = new LineData(dataSets);
                mpLineChart.setData(data);
                mpLineChart.invalidate();
            }
        });

    }




}
