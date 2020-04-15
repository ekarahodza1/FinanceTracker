package ba.unsa.etf.rma.moja_app;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
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
    private LocalDate current = null;
    private Button add;
    private String month = null;
    private int year = 0;
    private int month_value = 0;
    private Transaction trans;
    private Account account;
    private ListAdapter listAdapter;
    private IFinancePresenter financePresenter;
    private IAccountPresenter accountPresenter = new AccountPresenter(getActivity());
    private GestureDetector gestureDetector;
    private int orientation = getResources().getConfiguration().orientation;

    public IFinancePresenter getPresenter() {
        if (financePresenter == null) {
            financePresenter = new FinancePresenter(this, getActivity());
        }
        return financePresenter;
    }


    private OnItemClick onItemClick;

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
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
        onItemClick.onRightClicked1(accountPresenter.get());
    }

    private void onSwipeRight() {
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
        onItemClick.onLeftClicked1(accountPresenter.get());
    }





    public interface OnItemClick {
        public void onItemClicked(Transaction t, Account a);
        public void onNewClicked(Transaction t, Account a);
        public void onRightClicked1(Account a);
        public void onLeftClicked1(Account a);
    }



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
        getPresenter().refreshTransactions();


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
        financePresenter.filterMonth(current);
        notifyTransactionsListDataSetChanged();
        leftButton = (Button)fragmentView.findViewById(R.id.leftButton);
        rightButton = (Button)fragmentView.findViewById(R.id.rightButton);


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

        limitView = fragmentView.findViewById(R.id.limitView);
        globalAmountView = fragmentView.findViewById(R.id.globalAmountView);

        if (getArguments() != null && getArguments().containsKey("new_account")){
            account = getArguments().getParcelable("new_account");
        }
        else account = accountPresenter.get();


        String s = ""; s += account.getTotalLimit();
        limitView.setText(s);

        s = "";  s += account.getBudget();
        globalAmountView.setText(s);


        onItemClick = (OnItemClick) getActivity();
        transactionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Transaction transaction = listAdapter.getItem(position);
                onItemClick.onItemClicked(transaction, account);


            }

        });


        add = (Button)fragmentView.findViewById(R.id.buttonAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction t = new Transaction(null, null, 0, null, null, 0, null);
                onItemClick.onNewClicked(t, account);
            }
        });

        if (getArguments() != null && getArguments().containsKey("delete")) {
            trans = getArguments().getParcelable("delete");
            financePresenter.deleteTransaction(trans);
        }

        if (getArguments() != null && getArguments().containsKey("change1") && getArguments().containsKey("change2")) {
            trans = getArguments().getParcelable("change1");
            financePresenter.deleteTransaction(trans);
            trans = getArguments().getParcelable("change2");
            financePresenter.addTransaction(trans);
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


