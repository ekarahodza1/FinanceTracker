package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TransactionListFragment extends Fragment implements IFinanceView,
        AdapterView.OnItemSelectedListener, GestureDetector.OnGestureListener {

    private TextView limitView;
    private TextView globalAmountView;
    private Spinner sortSpinner;
    private ListView transactionList;
    private ArrayList<Transaction> mTransactionList;
    private SpinnerAdapter mAdapter;
    private TextView monthView;
    private Button leftButton;
    private Button rightButton;
    private Button connect;
    private LocalDate current = null;
    private Button add;
    private String month = null;
    private int year = 0;
    private int month_value = 0;
    private Transaction trans;
    private Account account;
    private ListAdapter listAdapter;
    private IFinancePresenter financePresenter;
    private IAccountPresenter accountPresenter;
    private GestureDetector gestureDetector;
    private int positionOfLastItem = -1;
    private String clickedName = "All";
    private String text = "Not sorted";

    public IFinancePresenter getPresenter() {
        if (financePresenter == null) {
            financePresenter = new FinancePresenter(this, getActivity());
        }
        return financePresenter;
    }

    public IAccountPresenter getAccountPresenter(){
        if (accountPresenter == null){
            accountPresenter = new AccountPresenter(this, getActivity());
        }
        return accountPresenter;
    }


    private OnItemClick onItemClick;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);

        sortSpinner = fragmentView.findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sorting, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);
        sortSpinner.setOnItemSelectedListener(this);
        initList();

        Spinner spinnerTransactions = fragmentView.findViewById(R.id.filterSpinner);
        mAdapter = new SpinnerAdapter(getActivity(), mTransactionList);
        spinnerTransactions.setAdapter(mAdapter);

        listAdapter = new ListAdapter(getActivity(), R.layout.list_view1, new ArrayList<Transaction>());
        transactionList = (ListView) fragmentView.findViewById(R.id.transactionList);
        transactionList.setAdapter(listAdapter);
        getPresenter().addTransactions();
        getPresenter().refreshTransactions();
        getAccountPresenter().addAccount();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                getPresenter().refreshTransactions();

            }
        }, 2000);

        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {

            }
        }, 0, 2000);


        connect = fragmentView.findViewById(R.id.connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPresenter().connected()){
                    getAccountPresenter().backToConnection();
                    getPresenter().postChanges();
                    Toast.makeText(getActivity(), "You are now connected, please wait a few seconds", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), "Check your connection, you may not be connected", Toast.LENGTH_LONG).show();
                }
            }
        });


        getAccountPresenter().backToConnection();
        getPresenter().postChanges();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            current = LocalDate.now();
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            month = current.getMonth().toString();
            month_value = current.getMonth().getValue();
            year = current.getYear();
        }
        monthView = (TextView) fragmentView.findViewById(R.id.monthView);
        monthView.setText(month + ", " + year);

        //notifyTransactionsListDataSetChanged();
        leftButton = (Button)fragmentView.findViewById(R.id.leftButton);
        rightButton = (Button)fragmentView.findViewById(R.id.rightButton);


        getPresenter().postChanges();


        spinnerTransactions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Transaction clicked = (Transaction) parent.getItemAtPosition(position);
                clickedName = clicked.getTypeString();
                financePresenter.filter(clickedName, text, current);
                notifyTransactionsListDataSetChanged();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        leftButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        current = current.minusMonths(1);
                    }

                monthView.setText(current.getMonth().toString() + " ," + current.getYear());
                financePresenter.filter(clickedName, text, current);
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
                financePresenter.filter(clickedName, text, current);
            }
        });

        limitView = fragmentView.findViewById(R.id.limitView);
        globalAmountView = fragmentView.findViewById(R.id.globalAmountView);

        if (getArguments() != null && getArguments().containsKey("new_account")){
            account = getArguments().getParcelable("new_account");
            getAccountPresenter().setAccount(account);
        }
        else {
            account = getAccountPresenter().get();
        }


        String s = ""; s += account.getTotalLimit();
        limitView.setText(s);

        s = "";  s += account.getBudget();
        globalAmountView.setText(s);


        onItemClick = (OnItemClick) getActivity();
        transactionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (positionOfLastItem != position) {
                    positionOfLastItem = position;
                    Transaction transaction = listAdapter.getItem(position);
                    transactionList.setSelector(R.color.colorAccent);
                    onItemClick.onItemClicked(transaction, account);
                }
                else {
                    positionOfLastItem = -1;
                    transactionList.setSelector(R.color.white);
                    Transaction t = new Transaction(-1, null, null, 0, -1, null, 0, null);
                    onItemClick.onNewClicked(t, account);
                }


            }

        });


        add = (Button)fragmentView.findViewById(R.id.buttonAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction t = new Transaction(0, null, null, 0, 0, null, 0, null);
                onItemClick.onNewClicked(t, account);
            }
        });

        if (getArguments() != null && getArguments().containsKey("delete")) {
            trans = getArguments().getParcelable("delete");
            financePresenter.deleteTransaction(trans);
        }

        if (getArguments() != null && getArguments().containsKey("change1") && getArguments().containsKey("change2")) {
            //trans = getArguments().getParcelable("change1");
            //financePresenter.deleteTransaction(trans);
            trans = getArguments().getParcelable("change2");
            //financePresenter.addTransaction(trans);
            financePresenter.changeTransaction(trans);
        }

        if (getArguments() != null && getArguments().containsKey("add")) {
            trans = getArguments().getParcelable("add");
            financePresenter.addTransaction(trans);
        }

        gestureDetector = new GestureDetector(this);

        fragmentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });



        return fragmentView;
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
        text  = parent.getItemAtPosition(position).toString();
        financePresenter.filter(clickedName, text, current);
        notifyTransactionsListDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    @Override
    public void setTransactions(ArrayList<Transaction> transactions) {
        //onItemClick.onTransactionsAdded(transactions);
        listAdapter.setTransaction(transactions);
    }

    @Override
    public void notifyTransactionsListDataSetChanged() {
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAccount(Account result) {
        account = result;
        String s = ""; s += account.getTotalLimit();
        limitView.setText(s);

        s = "";  s += account.getBudget();
        globalAmountView.setText(s);
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

    private void onSwipeLeft() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            onItemClick.onRightClicked1(getAccountPresenter().get());
    }

    private void onSwipeRight() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            onItemClick.onLeftClicked1(getAccountPresenter().get());
    }





    public interface OnItemClick {
        public void onItemClicked(Transaction t, Account a);
        public void onNewClicked(Transaction t, Account a);
        public void onRightClicked1(Account a);
        public void onLeftClicked1(Account a);
        public void onTransactionsAdded(ArrayList<Transaction> t);
    }


}


