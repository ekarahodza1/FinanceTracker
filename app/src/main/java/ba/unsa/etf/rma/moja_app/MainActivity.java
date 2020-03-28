package ba.unsa.etf.rma.moja_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, IFinanceView {
    private TextView limitView;
    private TextView globalAmountView;
    private TextView sortView;
    private TextClock filterView;
    private Spinner sortSpinner;
    private Spinner filterSpinner;
    private CalendarView calendarView;
    private ListView transactionList;
    private ArrayList<Transaction> mTransactionList;
    private SpinnerAdapter mAdapter;
    private TextView monthView;
    private Button leftButton;
    private Button rightButton;
    private LocalDate current = null;
    private LocalDate current1 = null;
    private String month = null;
    private int year = 0;
    private int month_value = 0;

    private ListAdapter listAdapter;

    private IFinancePresenter financePresenter;
    public IFinancePresenter getPresenter() {
        if (financePresenter == null) {
            financePresenter = new FinancePresenter(this, this);
        }
        return financePresenter;
    }

    private IFinanceInteractor interactor = new FinanceInteractor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sortSpinner = findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sorting, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);
        sortSpinner.setOnItemSelectedListener(this);

        initList();

        Spinner spinnerTransactions = findViewById(R.id.filterSpinner);
        mAdapter = new SpinnerAdapter(this, mTransactionList);
        spinnerTransactions.setAdapter(mAdapter);

        listAdapter = new ListAdapter(getApplicationContext(), R.layout.list_view1, new ArrayList<Transaction>());
        transactionList = (ListView) findViewById(R.id.transactionList);
        transactionList.setAdapter(listAdapter);
        getPresenter().refreshTransactions();



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            current = LocalDate.now();
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            month = current.getMonth().toString();
            month_value = current.getMonth().getValue();
            year = current.getYear();
        }
        current1 = current;

        monthView = (TextView) findViewById(R.id.monthView);
        monthView.setText(month + " ," + year);

        leftButton = (Button)findViewById(R.id.leftButton);
        rightButton = (Button)findViewById(R.id.rightButton);

        leftButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        current = current.minusMonths(1);
                    }

                monthView.setText(current.getMonth().toString() + " ," + current.getYear());
                financePresenter.filterMonth(current);
            }
    });

        rightButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   current = current.plusMonths(1);
                }

                monthView.setText(current.getMonth().toString() + " ," + current.getYear());
                financePresenter.filterMonth(current);
            }
        });





        spinnerTransactions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Transaction clicked = (Transaction) parent.getItemAtPosition(position);
                String clickedName = clicked.getTypeString();
                financePresenter.filterTransactions(clickedName);
                notifyTransactionsListDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initList(){
        mTransactionList = new ArrayList<>();
        mTransactionList.add(new Transaction("", R.drawable.white));
        mTransactionList.add(new Transaction("Individual Payment", R.drawable.individual_payment));
        mTransactionList.add(new Transaction("Regular Payment", R.drawable.regular_payment));
        mTransactionList.add(new Transaction("Purchase", R.drawable.purchase));
        mTransactionList.add(new Transaction("Individual Income", R.drawable.individual_income));
        mTransactionList.add(new Transaction("Regular Income", R.drawable.regular_income));

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text  = parent.getItemAtPosition(position).toString();
        financePresenter.sortTransactions(text);
        notifyTransactionsListDataSetChanged();



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    @Override
    public void setTransactions(ArrayList<Transaction> transactions) {
        listAdapter.setTransaction(transactions);
    }

    @Override
    public void notifyTransactionsListDataSetChanged() {
        listAdapter.notifyDataSetChanged();
    }
}
