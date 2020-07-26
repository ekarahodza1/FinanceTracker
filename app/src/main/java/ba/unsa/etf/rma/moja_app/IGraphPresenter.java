package ba.unsa.etf.rma.moja_app;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Map;

public interface IGraphPresenter {
    public ArrayList<BarEntry> getMonthIncome();
    public ArrayList<BarEntry> getMonthPayment();
    public ArrayList<BarEntry> getMonthAll();
    public ArrayList<BarEntry> getWeekIncome();
    public ArrayList<BarEntry> getWeekPayment();
    public ArrayList<BarEntry> getWeekAll();
    public ArrayList<BarEntry> getDayIncome();
    public ArrayList<BarEntry> getDayPayment();
    public ArrayList<BarEntry> getDayAll();
    ArrayList<Transaction> get();
    void addTransactions();
}
