package ba.unsa.etf.rma.moja_app;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IFinancePresenter {
    void refreshTransactions();
    void filterTransactions(String clickedName, LocalDate l);
    void sortTransactions(String text);
    void filterMonth(LocalDate current1);
    void deleteTransaction(Transaction t);
    void addTransaction(Transaction t);

    void addTransactions();
}
