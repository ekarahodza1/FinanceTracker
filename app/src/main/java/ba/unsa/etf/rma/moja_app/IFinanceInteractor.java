package ba.unsa.etf.rma.moja_app;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;

public interface IFinanceInteractor {
    public ArrayList<Transaction> getT();
    void add(Transaction t, Context context);
    void delete(Transaction t, Context context);
    void update(Transaction t, Context context);
    ArrayList<Transaction> getTransactionsFromTable(Context context);
    void deleteTable(Context context);
    ArrayList<Transaction> getDeleteTransactionsFromTable(Context context);
}
