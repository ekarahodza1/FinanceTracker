package ba.unsa.etf.rma.moja_app;

import android.content.Context;

import java.util.ArrayList;

public interface IFinanceInteractor {
    public ArrayList<Transaction> getT();
    void add(Transaction t, Context context);
    void delete(Transaction t, Context context);
    void update(Transaction t, Context context);

    ArrayList<Transaction> getAddTransactions(Context context);

    ArrayList<Transaction> getUpdateTransactions(Context context);

    ArrayList<Transaction> getDeleteTransactions(Context context);

    void deleteAddTable(Context context);

    void deleteUpdateTable(Context context);

    void deleteDeleteTable(Context context);
}
