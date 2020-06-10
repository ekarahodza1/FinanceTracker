package ba.unsa.etf.rma.moja_app;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IFinancePresenter {
    void refreshTransactions();
    ArrayList<Transaction> filterMonth(LocalDate current1);
    void deleteTransaction(Transaction t);
    void addTransaction(Transaction t);
    void changeTransaction(Transaction t);
    void addTransactions();
    boolean connected();
    void filter(String criteria1, String criteria2, LocalDate current1);
    void postChanges();
}
