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

import java.util.ArrayList;


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
    private Button left;
    private Button right;

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

        left.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
              financePresenter.previousMonth();
            }
    });

        right.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                financePresenter.nextMonth();
            }
        });

        //calendarView.setMinDate((long) (System.currentTimeMillis()-2.592e+9));



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
