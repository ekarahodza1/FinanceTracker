package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class FinancePresenter implements IFinancePresenter, FinanceInteractor.OnTransactionsAdd {
    private static ArrayList<Transaction> transactions;
    private  ArrayList<Transaction> transactions1 = new ArrayList<>();

    private HashMap<Integer,Transaction> map = new HashMap<Integer, Transaction>();
    private IFinanceView view;
    private IFinanceInteractor interactor;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void filter(String criteria1, String criteria2, LocalDate current1){
        ArrayList<Transaction> pomocne = new ArrayList<>();
        ArrayList<Transaction> pomocne1 = new ArrayList<>(transactions.size());
        pomocne1.addAll(transactions);

        LocalDate d = null;
        for (Transaction t: pomocne1) {
            if (t.getEndDate() != null) {

                d = t.getDate();
                while(d.isBefore(t.getEndDate())){
                    if(d.getMonthValue() == current1.getMonthValue()
                            && current1.getYear() == d.getYear()){
                        pomocne.add(t);
                    }
                    d = d.minusDays(-t.getTransactionInterval());
                }
            }
            else if (t.getDate().getMonthValue() == current1.getMonthValue()
                    && current1.getYear() == t.getDate().getYear()) pomocne.add(t);

        }


        pomocne1.clear();

        for (int i = 0; i < pomocne.size(); i++){
            if (criteria1.matches("Individual Payment") && pomocne.get(i).getTypeString().matches("Individual payment")){
                pomocne1.add(pomocne.get(i));
            }
            if (criteria1.matches("Regular Payment") && pomocne.get(i).getTypeString().matches("Regular payment")){
                pomocne1.add(pomocne.get(i));
            }
            if (criteria1.matches("Purchase") && pomocne.get(i).getTypeString().matches("Purchase")){
                pomocne1.add(pomocne.get(i));
            }
            if (criteria1.matches("Individual Income") && pomocne.get(i).getTypeString().matches("Individual income")){
                pomocne1.add(pomocne.get(i));
            }
            if (criteria1.matches("Regular Income") && pomocne.get(i).getTypeString().matches("Regular income")){
                pomocne1.add(pomocne.get(i));
            }
            if (criteria1.matches("All")) pomocne1.add(pomocne.get(i));
        }


        if (criteria2.matches( "Price - Descending")) {
            Collections.sort(pomocne1, new Comparator<Transaction>() {
                @Override
                public int compare(Transaction o1, Transaction o2) {
                    return (int) (o2.getAmount() - o1.getAmount());
                }
            });
        }
        if (criteria2.matches("Price - Ascending")) {
            Collections.sort(pomocne1, new Comparator<Transaction>() {
                @Override
                public int compare(Transaction o1, Transaction o2) {
                    return (int) (o1.getAmount() - o2.getAmount());
                }
            });
        }
        if (criteria2.matches("Title - Descending")) {
            Collections.sort(pomocne1, new Comparator<Transaction>() {
                @Override
                public int compare(Transaction o1, Transaction o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
            Collections.reverse(pomocne1);
        }
        if (criteria2.matches("Title - Ascending")) {
            Collections.sort(pomocne1, new Comparator<Transaction>() {
                @Override
                public int compare(Transaction o1, Transaction o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }

            });

        }
        if (criteria2.matches("Date - Descending")) {
            Collections.sort(pomocne1, new Comparator<Transaction>() {
                @Override
                public int compare(Transaction o1, Transaction o2) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        return (int) (o1.getDate().compareTo(o2.getDate()));
                    }
                    return 0;
                }
            });
        }
        if (criteria2.matches("Date - Ascending")) {
            Collections.sort(pomocne1, new Comparator<Transaction>() {
                @Override
                public int compare(Transaction o1, Transaction o2) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        return (int) (o2.getDate().compareTo(o1.getDate()));
                    }
                    return 0;
                }
            });
        }

        view.setTransactions(pomocne1);
        view.notifyTransactionsListDataSetChanged();


        pomocne1.addAll(transactions);
        pomocne.clear();

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
        if (connected()){
            map.put(0, new Transaction(-1));
            new FinanceInteractor((FinanceInteractor.OnTransactionsAdd)this).execute();
        }
    }

    @Override
    public void refreshTransactions()
    {
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

                LocalDate d = t.getDate();
                while(d.isBefore(t.getEndDate())){
                    if(d.getMonthValue() == current1.getMonthValue()
                            && current1.getYear() == d.getYear()){
                        pomocne.add(t);
                    }
                    d = d.minusDays(-t.getTransactionInterval());
                }
            }
            else if (t.getDate().getMonthValue() == current1.getMonthValue()
                    && current1.getYear() == t.getDate().getYear()) pomocne.add(t);

        }
        view.setTransactions(pomocne);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void postChanges(){
        if (connected()) {
            ArrayList<Transaction> add = interactor.getAddTransactions(context);
         //   ArrayList<Transaction> update = interactor.getUpdateTransactions(context);
          //  ArrayList<Transaction> delete = interactor.getDeleteTransactions(context);

            if (add.size() != 0) {
                for (int i = 0; i < add.size(); i++) {
                    Transaction temp = add.get(i);
                    addTransaction(temp);
                }
                interactor.deleteAddTable(context);
            }
//            if (update.size() != 0) {
//                for (Transaction t : update) changeTransaction(t);
//                interactor.deleteUpdateTable(context);
//            }
//            if (delete.size() != 0) {
//                for (Transaction t : delete) deleteTransaction(t);
//                interactor.deleteDeleteTable(context);
//            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTransaction(Transaction t){
        boolean connected = connected();
        if (connected){
            map.put(1,t);
            new FinanceInteractor((FinanceInteractor.OnTransactionsAdd)this).execute(map);
            map.remove(1);
        }
        else {
            interactor.add(t, context);
            transactions.add(t);
            view.setTransactions(transactions);
            view.notifyTransactionsListDataSetChanged();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteTransaction(Transaction t){
        boolean connected = connected();
        if (connected){
            map.put(2,t);
            new FinanceInteractor((FinanceInteractor.OnTransactionsAdd)this).execute(map);
        }
        else {
            //interactor.delete(t, context);
        }

        view.setTransactions(interactor.getT());
        view.notifyTransactionsListDataSetChanged();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void changeTransaction(Transaction t){
        boolean connected = connected();
        if (connected){
            map.put(3,t);
            new FinanceInteractor((FinanceInteractor.OnTransactionsAdd)this).execute(map);
        }
        else {
            //interactor.update(t, context);
            view.setTransactions(transactions);
            view.notifyTransactionsListDataSetChanged();
        }



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDone(ArrayList<Transaction> results) {
        transactions = results;
        transactions1 = results;
        filterMonth(LocalDate.now());
        //view.setTransactions(results);
        view.notifyTransactionsListDataSetChanged();
    }

    public ArrayList<Transaction> get() {
        return transactions;
    }

    @Override
    public boolean connected() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        return connected;
    }
}
