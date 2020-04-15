package ba.unsa.etf.rma.moja_app;

import java.util.ArrayList;

public interface IFinanceInteractor {
    public ArrayList<Transaction> get();

    void add(Transaction t);

    void delete(Transaction t);
}
