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


public class MainActivity extends AppCompatActivity implements TransactionListFragment.OnItemClick, TransactionDetailFragment.OnItemChange {//AdapterView.OnItemSelectedListener,

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
            public void onItemClicked(Transaction transaction) {

                Bundle arguments = new Bundle();
                arguments.putParcelable("transaction", transaction);
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


    @Override
    public void onDeleteClicked(Transaction transaction) {

        Bundle arguments = new Bundle();
        arguments.putParcelable("delete", transaction);
        TransactionListFragment listFragment = new TransactionListFragment();
        listFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_list, listFragment)
                .addToBackStack(null)
                .commit();
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

    @Override
    public void onChangeClicked(Transaction t1, Transaction t2) {

        Bundle arguments = new Bundle();
        arguments.putParcelable("change1", t1);
        arguments.putParcelable("change2", t2);
        TransactionListFragment listFragment = new TransactionListFragment();
        listFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_list, listFragment)
                .addToBackStack(null)
                .commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onNewClicked(Transaction transaction) {

        Bundle arguments = new Bundle();
        arguments.putParcelable("new", transaction);
        TransactionDetailFragment detailFragment = new TransactionDetailFragment();
        detailFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.transaction_list, detailFragment)
                .addToBackStack(null)
                .commit();
    }



}
