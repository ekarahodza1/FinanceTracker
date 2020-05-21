package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class FinancePresenter implements IFinancePresenter, FinanceInteractor.OnTransactionsAdd {
    private ArrayList<Transaction> transactions;
    private HashMap<Integer, Transaction> map = new HashMap<Integer, Transaction>();
    private IFinanceView view;
    private IFinanceInteractor interactor;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    public void sortTransactions(String criteria) {


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
             Collections.reverse(transactions);
         }
         if (criteria.matches("Title - Ascending")) {
             Collections.sort(transactions, new Comparator<Transaction>() {
                 @Override
                 public int compare(Transaction o1, Transaction o2) {
                     return o1.getTitle().compareTo(o2.getTitle());
                 }

             });

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void filterTransactions(String criteria, LocalDate current){
        ArrayList<Transaction> pomocne = new ArrayList<>();
        if (criteria.matches("All")) {
            filterMonth(current);
            return;
        }

        filterMonth(current);

        for (int i = 0; i < transactions.size(); i++){
            if (criteria.matches("Individual Payment") && transactions.get(i).getTypeString().matches("Individual Payment")){
                pomocne.add(transactions.get(i));
            }
            if (criteria.matches("Regular Payment") && transactions.get(i).getTypeString().matches("Regular Payment")){
                pomocne.add(transactions.get(i));
            }
            if (criteria.matches("Purchase") && transactions.get(i).getTypeString().matches("Purchase")){
                pomocne.add(transactions.get(i));
            }
            if (criteria.matches("Individual Income") && transactions.get(i).getTypeString().matches("Individual Income")){
                pomocne.add(transactions.get(i));
            }
            if (criteria.matches("Regular Income") && transactions.get(i).getTypeString().matches("Regular Income")){
                pomocne.add(transactions.get(i));
            }
        }
        transactions = pomocne;
        view.setTransactions(pomocne);


    }



    public FinancePresenter(IFinanceView view, Context context) {
        this.view       = view;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.interactor = (IFinanceInteractor)
                    new FinanceInteractor((FinanceInteractor.OnTransactionsAdd)this);
        }

        this.context    = context;
        transactions = interactor.getT();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addTransactions(){
        map.put(0, new Transaction(-1));
        new FinanceInteractor((FinanceInteractor.OnTransactionsAdd)this).execute();

    }

    @Override
    public void refreshTransactions() {
        view.setTransactions(transactions);
        view.notifyTransactionsListDataSetChanged();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void filterMonth(LocalDate current1){
        ArrayList<Transaction> pomocne = new ArrayList<>();
        ArrayList<Transaction> pomocne1 = new ArrayList<>();
        pomocne1 = transactions;
        for (Transaction t: pomocne1) {
            if (t.getEndDate() != null) {
                if (current1.getMonthValue() >= t.getDate().getMonthValue()
                        && current1.getMonthValue() <= t.getEndDate().getMonthValue()
                        && current1.getYear() == t.getEndDate().getYear()){
                    pomocne.add(t);
                }
            }
            else if (t.getDate().getMonthValue() == current1.getMonthValue()
                    && current1.getYear() == t.getDate().getYear()) pomocne.add(t);

        }

        //transactions = pomocne;
        view.setTransactions(pomocne);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTransaction(Transaction t){
        map.put(1,t);
        new FinanceInteractor((FinanceInteractor.OnTransactionsAdd)this).execute(map);
        interactor.add(t);
        view.setTransactions(interactor.getT());
        view.notifyTransactionsListDataSetChanged();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteTransaction(Transaction t){
        map.put(2,t);
        new FinanceInteractor((FinanceInteractor.OnTransactionsAdd)this).execute(map);
        interactor.delete(t);
        view.setTransactions(interactor.getT());
        view.notifyTransactionsListDataSetChanged();

    }

    @Override
    public void onDone(ArrayList<Transaction> results) {
        transactions = results;
        view.setTransactions(results);
        view.notifyTransactionsListDataSetChanged();
    }

    public ArrayList<Transaction> get() {
        return transactions;
    }
}
