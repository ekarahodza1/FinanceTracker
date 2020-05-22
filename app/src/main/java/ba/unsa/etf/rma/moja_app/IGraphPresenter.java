package ba.unsa.etf.rma.moja_app;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Map;

public interface IGraphPresenter {
    public ArrayList<BarEntry> getMonthIncome();
    public ArrayList<BarEntry> getMonthPayment();
    public ArrayList<BarEntry> getMonthAll();
    public ArrayList<BarEntry> getWeekIncome(ArrayList<Transaction> t);
    public ArrayList<BarEntry> getWeekPayment(ArrayList<Transaction> t);
    public ArrayList<BarEntry> getWeekAll(ArrayList<Transaction> t);
    public ArrayList<BarEntry> getDayIncome(ArrayList<Transaction> t);
    public ArrayList<BarEntry> getDayPayment(ArrayList<Transaction> t);
    public ArrayList<BarEntry> getDayAll(ArrayList<Transaction> t);

    ArrayList<Transaction> get();
}
