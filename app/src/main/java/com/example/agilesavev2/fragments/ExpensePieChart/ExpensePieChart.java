package com.example.agilesavev2.fragments.ExpensePieChart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.views.accounts.AccountTypeActivity;
import com.example.agilesavev2.views.ai_analysis.AIAnalysisActivity;
import com.example.agilesavev2.views.current_balance.CurrentBalanceActivity;
import com.example.agilesavev2.views.home.HomeActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpensePieChart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpensePieChart extends Fragment implements ExpensesContract.ExpensesView, com.example.agilesavev2.assets.Fragment {

    private View layout;
    private Intent intent;
    private String selectedValue="";
    private ExpensesPresenter presenter;
    private String currencySymbol;
    public ExpensePieChart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        intent= getActivity().getIntent();
        return inflater.inflate(R.layout.fragment_expense_pie_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ExpensesPresenter(intent,this, getContext());
        Button goDeeper= view.findViewById(R.id.goDeeper);
        Button goBack= view.findViewById(R.id.goBack);
        goDeeper.setOnClickListener(presenter::inspectPieChart);
        goBack.setOnClickListener(presenter::handleBack);
        layout=view;
//        presenter.getExpenses();
        ((com.example.agilesavev2.assets.View)getActivity()).setAdapter(this);
        currencySymbol = Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();

        TextView showMore = view.findViewById(R.id.showMore);
        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getIntent().setClass(getActivity(), AIAnalysisActivity.class);
                startActivity(getActivity().getIntent());
            }
        });


    }

    @Override
    public void showPieChart(HashMap<String, Double>  data){

        //get pie chart
        PieChart pieChart = layout.findViewById(R.id.any_chart_view);
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleColor(140070);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.getLegend().setTextColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(0);



        pieChart .getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        pieChart .getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        pieChart .getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);

        //entries
        ArrayList<PieEntry> entries = new ArrayList<>();
        int totalExpense = 0;
        boolean empty=false;
        if(data==null || data.isEmpty()){//show an empty pie chart is data given is empty
            entries.add(new PieEntry(100,""));
            empty=true;
            pieChart.setCenterText(currencySymbol+"0");
            pieChart.getLegend().setEnabled(false);
        }else {
            pieChart.getLegend().setEnabled(true);
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                entries.add(new PieEntry(Float.parseFloat(String.valueOf(entry.getValue())), entry.getKey()));
                totalExpense += entry.getValue();
                pieChart.setDrawEntryLabels(false);
                pieChart.setClickable(false);
            }
            pieChart.setCenterText(currencySymbol+totalExpense);
        }



        //get colours
        ArrayList<Integer> colours = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS) colours.add(color);
        for(int color: ColorTemplate.VORDIPLOM_COLORS)colours.add(color);

        //dataset
        PieDataSet dataSet = new PieDataSet(entries,"");
        if(empty){
            dataSet.setColors(Color.GRAY);
        }else{
            dataSet.setColors(colours);
        }

        PieData pieData= new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);



        pieChart.setData(pieData);
        pieChart.invalidate();

        pieChart.setDrawEntryLabels(false);
        pieChart.getData().setDrawValues(false);

        Button button = layout.findViewById(R.id.goDeeper);
        int finalTotalExpense = totalExpense;

        ((TextView)getView().findViewById(R.id.total)).setText(currencySymbol+finalTotalExpense);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pe = (PieEntry) e;
                System.out.println(pe.getLabel());
                button.setVisibility(View.VISIBLE);
                selectedValue=(pe.getLabel());
                pieChart.setCenterText(currencySymbol+pe.getValue());
            }

            @Override
            public void onNothingSelected() {
                button.setVisibility(View.INVISIBLE);
                selectedValue="";
                pieChart.setCenterText(currencySymbol+ finalTotalExpense);

            }
        });



    }


    @Override
    public String getPieChartSelectedVal() {
        return selectedValue;
    }

    @Override
    public String getID() {
        Intent intent = getActivity().getIntent();
        return intent.getStringExtra("ID");
    }

    @Override
    public String getSelectedAccountID() {
        HashMap<String, Integer> accounts = (HashMap<String, Integer>) getActivity().getIntent().getSerializableExtra("ACCOUNTS");
        if(accounts.isEmpty()){
            getActivity().getIntent().setClass(getActivity(), AccountTypeActivity.class);
            startActivityForResult(getActivity().getIntent(),1);
            return null;
        }
        String accountName= getActivity().getIntent().getStringExtra("SELECTED");
        System.out.println("ACCOUNTS "+accounts);
        String accountID = Integer.toString(accounts.get(accountName));
        return accountID;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void refreshFragment() {

        presenter.getExpenses();
    }
}
