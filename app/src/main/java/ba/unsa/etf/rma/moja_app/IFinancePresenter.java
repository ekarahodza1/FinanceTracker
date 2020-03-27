package ba.unsa.etf.rma.moja_app;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public interface IFinancePresenter {
    void refreshTransactions();
    void filterTransactions(String clickedName);
    void sortTransactions(String text);

    void previousMonth();

    void nextMonth();
}
