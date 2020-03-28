package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
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
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                         return (int) (o1.getDate().compareTo(o2.getDate()));
                     }
                     return 0;
                 }
             });
         }
         if (criteria.matches("Date - Ascending")) {
             Collections.sort(transactions, new Comparator<Transaction>() {
                 @Override
                 public int compare(Transaction o1, Transaction o2) {
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                         return (int) (o2.getDate().compareTo(o1.getDate()));
                     }
                     return 0;
                 }
             });
         }
         view.setTransactions(new ArrayList<Transaction>());
         view.setTransactions(transactions);

    }

    @Override
    public void filterTransactions(String criteria){
        ArrayList<Transaction> pomocne = new ArrayList<>();
        for (int i = 0; i < transactions.size(); i++){
            if (criteria.matches("Individual Payment") && transactions.get(i).getType() == Type.INDIVIDUALPAYMENT){
                pomocne.add(transactions.get(i));
            }
            if (criteria.matches("Regular Payment") && transactions.get(i).getType() == Type.REGULARPAYMENT){
                pomocne.add(transactions.get(i));
            }
            if (criteria.matches("Purchase") && transactions.get(i).getType() == Type.PURCHASE){
                pomocne.add(transactions.get(i));
            }
            if (criteria.matches("Individual Income") && transactions.get(i).getType() == Type.INDIVIDUALINCOME){
                pomocne.add(transactions.get(i));
            }
            if (criteria.matches("Regular Income") && transactions.get(i).getType() == Type.REGULARINCOME){
                pomocne.add(transactions.get(i));
            }
        }
        view.setTransactions(pomocne);


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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void filterMonth(LocalDate current1){
        ArrayList<Transaction> pomocne = new ArrayList<>();
        for (Transaction t: transactions) {
            if (t.getDate().getMonthValue() == current1.getMonthValue() ) pomocne.add(t);

        }

        view.setTransactions(pomocne);
    }
}
