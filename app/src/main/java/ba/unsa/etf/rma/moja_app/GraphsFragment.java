package ba.unsa.etf.rma.moja_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class GraphsFragment extends Fragment implements OnChartGestureListener, OnChartValueSelectedListener {

    private BarChart income;
    private BarChart payment;
    private BarChart all;
    private Button day;
    private Button week;
    private Button month;
    private FinanceInteractor interactor = new FinanceInteractor();
    private ArrayList<Transaction> list;
    IGraphPresenter graphPresenter = new GraphPresenter(getActivity());



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.graphs_fragment, container, false);

        income = view.findViewById(R.id.incomeChart);
        payment = view.findViewById(R.id.paymentChart);
        all = view.findViewById(R.id.allChart);

        income.setDragEnabled(false);
        income.setScaleEnabled(false);

        payment.setDragEnabled(false);
        payment.setScaleEnabled(false);

        all.setDragEnabled(false);
        all.setScaleEnabled(false);

        list = interactor.get();

        ArrayList<BarEntry> mIncome = graphPresenter.getMonthIncome(list);
        ArrayList<BarEntry> mPayment = graphPresenter.getMonthPayment(list);
        ArrayList<BarEntry> mAll = graphPresenter.getMonthAll(list);

        BarDataSet set1 = new BarDataSet(mIncome, "Income");
        BarDataSet set2 = new BarDataSet(mPayment, "Payment");
        BarDataSet set3 = new BarDataSet(mAll, "All");

        BarData data1 = new BarData(); data1.addDataSet(set1);
        BarData data2 = new BarData(); data2.addDataSet(set2);
        BarData data3 = new BarData(); data3.addDataSet(set3);

        income.setData(data1);
        payment.setData(data2);
        all.setData(data3);

        payment.setDescription(null);
        income.setDescription(null);
        all.setDescription(null);

        //income.invalidate();

        day = (Button)view.findViewById(R.id.days);
        week = (Button)view.findViewById(R.id.week);
        month = (Button)view.findViewById(R.id.month);

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BarEntry> mIncome = graphPresenter.getDayIncome(list);
                ArrayList<BarEntry> mPayment = graphPresenter.getDayPayment(list);
                ArrayList<BarEntry> mAll = graphPresenter.getDayAll(list);

                BarDataSet set1 = new BarDataSet(mIncome, "Income");
                BarDataSet set2 = new BarDataSet(mPayment, "Payment");
                BarDataSet set3 = new BarDataSet(mAll, "All");

                BarData data1 = new BarData(); data1.addDataSet(set1);
                BarData data2 = new BarData(); data2.addDataSet(set2);
                BarData data3 = new BarData(); data3.addDataSet(set3);

                income.setData(data1);
                payment.setData(data2);
                all.setData(data3);

                income.notifyDataSetChanged();
                income.invalidate();

                payment.notifyDataSetChanged();
                payment.invalidate();

                all.notifyDataSetChanged();
                all.invalidate();
            }
        });

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BarEntry> mIncome = graphPresenter.getWeekIncome(list);
                ArrayList<BarEntry> mPayment = graphPresenter.getWeekPayment(list);
                ArrayList<BarEntry> mAll = graphPresenter.getWeekAll(list);

                BarDataSet set1 = new BarDataSet(mIncome, "Income");
                BarDataSet set2 = new BarDataSet(mPayment, "Payment");
                BarDataSet set3 = new BarDataSet(mAll, "All");

                BarData data1 = new BarData(); data1.addDataSet(set1);
                BarData data2 = new BarData(); data2.addDataSet(set2);
                BarData data3 = new BarData(); data3.addDataSet(set3);

                income.setData(data1);
                payment.setData(data2);
                all.setData(data3);

                income.notifyDataSetChanged();
                income.invalidate();

                payment.notifyDataSetChanged();
                payment.invalidate();

                all.notifyDataSetChanged();
                all.invalidate();
            }
        });

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BarEntry> mIncome = graphPresenter.getMonthIncome(list);
                ArrayList<BarEntry> mPayment = graphPresenter.getMonthPayment(list);
                ArrayList<BarEntry> mAll = graphPresenter.getMonthAll(list);

                BarDataSet set1 = new BarDataSet(mIncome, "Income");
                BarDataSet set2 = new BarDataSet(mPayment, "Payment");
                BarDataSet set3 = new BarDataSet(mAll, "All");

                BarData data1 = new BarData(); data1.addDataSet(set1);
                BarData data2 = new BarData(); data2.addDataSet(set2);
                BarData data3 = new BarData(); data3.addDataSet(set3);

                income.setData(data1);
                payment.setData(data2);
                all.setData(data3);

                income.notifyDataSetChanged();
                income.invalidate();

                payment.notifyDataSetChanged();
                payment.invalidate();

                all.notifyDataSetChanged();
                all.invalidate();
            }
        });

        return view;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
