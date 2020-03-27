package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FinancePresenter implements IFinancePresenter {
    private ArrayList<Transaction> transactions;
    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    public void sortTransactions(String criteria){
         if (criteria.matches( "Price - Descending")) {
             Collections.sort(transactions, new Comparator<Transaction>() {
                 @Override
                 public int compare(Transaction o1, Transaction o2) {
                     return (int) (o2.getAmount() - o1.getAmount());
                 }
             });
         }
         if (criteria.matches("Price - Ascending")) {
             Collections.sort(transactions, new Comparator<Transaction>() {
                 @Override
                 public int compare(Transaction o1, Transaction o2) {
                     return (int) (o1.getAmount() - o2.getAmount());
                 }
             });
         }
         if (criteria.matches("Title - Descending")) {
             Collections.sort(transactions, new Comparator<Transaction>() {
                 @Override
                 public int compare(Transaction o1, Transaction o2) {
                     return o1.getTitle().compareTo(o2.getTitle());
                 }
             });
         }
         if (criteria.matches("Title - Ascending")) {
             Collections.sort(transactions, new Comparator<Transaction>() {
                 @Override
                 public int compare(Transaction o1, Transaction o2) {
                     return o1.getTitle().compareTo(o2.getTitle());
                 }
             });
             Collections.reverse(transactions);
         }
         if (criteria.matches("Date - Descending")) {
             Collections.sort(transactions, new Comparator<Transaction>() {
                 @Override
                 public int compare(Transaction o1, Transaction o2) {
                     return (int) (o1.getDate().compareTo(o2.getDate()));
                 }
             });
         }
         if (criteria.matches("Date - Ascending")) {
             Collections.sort(transactions, new Comparator<Transaction>() {
                 @Override
                 public int compare(Transaction o1, Transaction o2) {
                     return (int) (o2.getDate().compareTo(o1.getDate()));
                 }
             });
         }
         view.setTransactions(new ArrayList<Transaction>());
         view.setTransactions(transactions);

    }

    private IFinanceView view;
    private IFinanceInteractor interactor;
    private Context context;

    public FinancePresenter(IFinanceView view, Context context) {
        this.view       = view;
        this.interactor = new FinanceInteractor();
        this.context    = context;
        transactions = interactor.get();
    }
    @Override
    public void refreshTransactions() {
        view.setTransactions(interactor.get());
        view.notifyTransactionsListDataSetChanged();
    }
}
