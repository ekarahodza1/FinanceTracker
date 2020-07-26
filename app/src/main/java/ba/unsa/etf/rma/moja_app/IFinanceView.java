package ba.unsa.etf.rma.moja_app;

import java.util.ArrayList;

public interface IFinanceView {
    void setTransactions(ArrayList<Transaction> transactions);
    void notifyTransactionsListDataSetChanged();
    void setAccount(Account result);
}
