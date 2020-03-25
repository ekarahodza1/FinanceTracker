package ba.unsa.etf.rma.moja_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements IFinanceView{
    private TextView limitView;
    private TextView globalAmountView;
    private TextView sortView;
    private TextClock filterView;
    private Spinner sortSpinner;
    private Spinner filterSpinner;
    private CalendarView calendarView;
    private ListView transactionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
