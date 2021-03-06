package ba.unsa.etf.rma.moja_app;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
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

@RequiresApi(api = Build.VERSION_CODES.O)
public class GraphsFragment extends Fragment implements GestureDetector.OnGestureListener {

    private BarChart income;
    private BarChart payment;
    private BarChart all;
    private Button day;
    private Button week;
    private Button month;
    private ArrayList<Transaction> list;
    private GestureDetector gestureDetector;
    private IGraphPresenter graphPresenter = new GraphPresenter(getActivity());
    private OnSwipeChange onSwipeChange;
    private Account account;
    private boolean weekClicked, dayClicked = false;



    public interface OnSwipeChange {
        public void onRightClicked3();
        public void onLeftClicked3(Account a);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.graphs_fragment, container, false);
        graphPresenter.addTransactions();
        if (getArguments() != null && getArguments().containsKey("budget")) {
            account = getArguments().getParcelable("budget");
        }


        income = view.findViewById(R.id.incomeChart);
        payment = view.findViewById(R.id.paymentChart);
        all = view.findViewById(R.id.allChart);

        income.setDragEnabled(false);
        income.setScaleEnabled(false);

        payment.setDragEnabled(false);
        payment.setScaleEnabled(false);

        all.setDragEnabled(false);
        all.setScaleEnabled(false);

        ArrayList<BarEntry> mIncome = graphPresenter.getMonthIncome();
        ArrayList<BarEntry> mPayment = graphPresenter.getMonthPayment();
        ArrayList<BarEntry> mAll = graphPresenter.getMonthAll();

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

        day = (Button)view.findViewById(R.id.days);
        week = (Button)view.findViewById(R.id.week);
        month = (Button)view.findViewById(R.id.month);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (!dayClicked && !weekClicked)
                month.callOnClick();
            }
        }, 3000);   //5 seconds

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayClicked = true;
                list = graphPresenter.get();
                ArrayList<BarEntry> mIncome = graphPresenter.getDayIncome();
                ArrayList<BarEntry> mPayment = graphPresenter.getDayPayment();
                ArrayList<BarEntry> mAll = graphPresenter.getDayAll();

                BarDataSet set1 = new BarDataSet(mIncome, "Payment");
                BarDataSet set2 = new BarDataSet(mPayment, "Income");
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
                weekClicked = true;
                ArrayList<BarEntry> mIncome = graphPresenter.getWeekIncome();
                ArrayList<BarEntry> mPayment = graphPresenter.getWeekPayment();
                ArrayList<BarEntry> mAll = graphPresenter.getWeekAll();

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
                ArrayList<BarEntry> mIncome = graphPresenter.getMonthIncome();
                ArrayList<BarEntry> mPayment = graphPresenter.getMonthPayment();
                ArrayList<BarEntry> mAll = graphPresenter.getMonthAll();

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

        onSwipeChange = (OnSwipeChange) getActivity();

        gestureDetector = new GestureDetector(this);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        return view;
    }



    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent down, MotionEvent move, float X, float Y) {
        boolean value = false;
        float diffY = move.getY() - down.getY();
        float diffX = move.getX() - down.getX();
        if (Math.abs(diffX) > 100 && Math.abs(X) > 100){
            if (diffX > 0){
                onSwipeRight();
            } else {
                onSwipeLeft();
            }
            value = true;
        }
        return value;
    }

    private void onSwipeRight() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) onSwipeChange.onLeftClicked3(account);
    }

    private void onSwipeLeft() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) onSwipeChange.onRightClicked3();
    }

}
