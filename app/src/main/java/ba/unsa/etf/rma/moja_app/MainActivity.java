package ba.unsa.etf.rma.moja_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
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
    private Button add;
    private String month = null;
    private int year = 0;
    private int month_value = 0;
    private Transaction transaction;
    private Account account = new Account(658,-1000,100);



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


        limitView = findViewById(R.id.limitView);
        globalAmountView = findViewById(R.id.globalAmountView);

        String s = ""; s += account.getTotalLimit();
        limitView.setText(s);

        s = "";  s += account.getBudget();
        globalAmountView.setText(s);

        transactionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ListItemActivity.class);
                Transaction t = listAdapter.getItem(position);
                transaction = t;
                intent.putExtra("title", t.getTitle());
                Bundle b = new Bundle();
                b.putDouble("amount", t.getAmount());
                intent.putExtras(b);
                intent.putExtra("date", t.getDate().toString());
                if (t.getEndDate() == null) intent.putExtra("eDate", "");
                else intent.putExtra("eDate", t.getEndDate().toString());
                b.putInt("interval", t.getTransactionInterval());
                intent.putExtra("type", t.getType().toString());
                intent.putExtra("description", t.getItemDescription());
                b.putDouble("budget", account.getBudget());
                startActivityForResult(intent, 1);

            }

        });




        add = (Button)findViewById(R.id.buttonAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListItemActivity.class);
                intent.putExtra("title", "");
                Bundle b = new Bundle();
                b.putDouble("amount", 0);
                intent.putExtras(b);
                intent.putExtra("date", "");
                intent.putExtra("eDate", "");
                b.putInt("interval", 0);
                intent.putExtra("type", "");
                intent.putExtra("description", "");
                b.putDouble("budget", account.getBudget());
                startActivityForResult(intent, 1);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Transaction t = null;
            LocalDate date1 = null;
            LocalDate date2 = null;
            String s = data.getStringExtra("type");
            Type type_ = null;
            Bundle b = data.getExtras();
            if (s.matches("INDIVIDUALPAYMENT")) type_ = Type.INDIVIDUALPAYMENT;
            if (s.matches("REGULARPAYMENT")) type_ = Type.REGULARPAYMENT;
            if (s.matches("PURCHASE")) type_ = Type.PURCHASE;
            if (s.matches("INDIVIDUALINCOME")) type_ = Type.INDIVIDUALINCOME;
            if (s.matches("REGULARINCOME")) type_ = Type.REGULARINCOME;

            if (!data.getStringExtra("date").matches("")) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                date1 = LocalDate.parse(data.getStringExtra("date"));
            }
            if (!data.getStringExtra("eDate").matches("")) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                date2 = LocalDate.parse(data.getStringExtra("eDate"));
            }
            double d = account.getBudget();
            account.setBudget(d + b.getDouble("amount"));
            String s1 = "" + account.getBudget();
            globalAmountView.setText(s1);
            t = new Transaction(date1, data.getStringExtra("title"), b.getDouble("amount"),
                    type_, data.getStringExtra("description"), b.getInt("interval"), date2);
            if (resultCode == RESULT_OK) { }
            if (resultCode == RESULT_CANCELED) { }
            if (resultCode == 2){
                financePresenter.deleteTransaction(t);
            }
            if (resultCode == 3){
                financePresenter.addTransaction(t);
            }
            if (resultCode == 4){
                financePresenter.deleteTransaction(transaction);
                financePresenter.addTransaction(t);
            }
        }
    }

    private void initList(){
        mTransactionList = new ArrayList<>();
        mTransactionList.add(new Transaction("All", R.drawable.white));
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
