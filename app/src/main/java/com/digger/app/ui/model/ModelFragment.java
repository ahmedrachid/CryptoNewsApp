package com.digger.app.ui.model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.digger.app.R;
import com.digger.app.model.DataPlot;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class ModelFragment extends Fragment {

    private ModelViewModel modelFragmentView;
    private LineChart mpLineChart;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        modelFragmentView = new ModelViewModel();


        View root = inflater.inflate(R.layout.chart_line, container, false);

        mpLineChart = (LineChart)  root.findViewById(R.id.line_chart);


        modelFragmentView.dataValues().observe(this, new Observer<ArrayList<Entry>>() {
            @Override
            public void onChanged(ArrayList<Entry> stocks) {
                LineDataSet lineDataSet = new LineDataSet(stocks,"Data Set 1");


                final ArrayList<String> xLabel = modelFragmentView.getLabels().getValue();

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





        return root;
    }
}
