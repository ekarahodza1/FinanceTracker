package ba.unsa.etf.rma.moja_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements
        TransactionListFragment.OnItemClick,
        TransactionDetailFragment.OnItemChange,
         BudgetFragment.OnBudgetChange, GraphsFragment.OnSwipeChange{

    private boolean twoPaneMode;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FrameLayout details = findViewById(R.id.transaction_detail);

        if (details != null) {
            twoPaneMode = true;
            TransactionDetailFragment detailFragment = (TransactionDetailFragment) fragmentManager.findFragmentById(R.id.transaction_detail);

            if (detailFragment == null) {
                detailFragment = new TransactionDetailFragment();
                fragmentManager.beginTransaction().
                        replace(R.id.transaction_detail, detailFragment)
                        .commit();
            }
        } else {
            twoPaneMode = false;
        }

        Fragment listFragment = fragmentManager.findFragmentByTag("list");

        if (listFragment == null) {
            listFragment = new TransactionListFragment();
            fragmentManager.beginTransaction().replace(R.id.transaction_list, listFragment, "list").commit();
        } else {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }


            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClicked(Transaction transaction, Account account) {

                Bundle arguments = new Bundle();
                arguments.putParcelable("transaction", transaction);
                arguments.putParcelable("account", account);
                TransactionDetailFragment detailFragment = new TransactionDetailFragment();
                detailFragment.setArguments(arguments);
                if (twoPaneMode){

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.transaction_detail, detailFragment)
                            .commit();
                }
                else {

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.transaction_list, detailFragment)
                            .addToBackStack(null)
                            .commit();


                }
            }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDeleteClicked(Transaction transaction) {

        Bundle arguments = new Bundle();
        arguments.putParcelable("delete", transaction);
        TransactionListFragment listFragment = new TransactionListFragment();
        TransactionDetailFragment detailFragment = new TransactionDetailFragment();
        listFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_list, listFragment)
                .addToBackStack(null)
                .commit();
        if (twoPaneMode) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.transaction_detail, detailFragment)
                    .commit();
        }
    }

    @Override
    public void onAddClicked(Transaction transaction) {

        Bundle arguments = new Bundle();
        arguments.putParcelable("add", transaction);
        TransactionListFragment listFragment = new TransactionListFragment();
        listFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_list, listFragment)
                .addToBackStack(null)
                .commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRightClicked1(Account a) {

        Bundle arguments = new Bundle();
        arguments.putParcelable("budget", a);
        BudgetFragment budgetFragment = new BudgetFragment();
        budgetFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_list, budgetFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onChanged(Account a) {

        Bundle arguments = new Bundle();
        arguments.putParcelable("new_account", a);
        BudgetFragment budgetFragment = new BudgetFragment();
        budgetFragment.setArguments(arguments);

    }

    @Override
    public void onRightClicked2() {

//        Bundle arguments = new Bundle();
//        arguments.putParcelable("budget", t);
        GraphsFragment graphsFragment = new GraphsFragment();
      //  budgetFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_list, graphsFragment)
                .addToBackStack(null)
                .commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onChangeClicked(Transaction t1, Transaction t2) {

        Bundle arguments = new Bundle();
        arguments.putParcelable("change1", t1);
        arguments.putParcelable("change2", t2);
        TransactionListFragment listFragment = new TransactionListFragment();
        TransactionDetailFragment detailFragment = new TransactionDetailFragment();
        listFragment.setArguments(arguments);
        detailFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_list, listFragment)
                .addToBackStack(null)
                .commit();
        if (twoPaneMode) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.transaction_detail, detailFragment)
                    .commit();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onNewClicked(Transaction transaction, Account account) {

        Bundle arguments = new Bundle();
        arguments.putParcelable("new", transaction);
        arguments.putParcelable("account", account);
//        TransactionDetailFragment detailFragment = new TransactionDetailFragment();
//        detailFragment.setArguments(arguments);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.transaction_list, detailFragment)
//                .addToBackStack(null)
//                .commit();
        TransactionListFragment listFragment = new TransactionListFragment();
        TransactionDetailFragment detailFragment = new TransactionDetailFragment();
        listFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_list, listFragment)
                .addToBackStack(null)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_detail, detailFragment)
                .commit();
    }

    @Override
    public void onLeftClicked1(Account a){
        GraphsFragment graphsFragment = new GraphsFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable("budget", a);
        graphsFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_list, graphsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLeftClicked2(){
        TransactionListFragment transactionListFragment = new TransactionListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_list, transactionListFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onRightClicked3(){
        TransactionListFragment transactionListFragment = new TransactionListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_list, transactionListFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLeftClicked3(Account a){
        BudgetFragment budgetFragment = new BudgetFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable("budget", a);
        budgetFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_list, budgetFragment)
                .addToBackStack(null)
                .commit();
    }





}
