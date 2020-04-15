package ba.unsa.etf.rma.moja_app;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Map;

public interface IGraphPresenter {
    public ArrayList<BarEntry> getMonthIncome(ArrayList<Transaction> t);
    public ArrayList<BarEntry> getMonthPayment(ArrayList<Transaction> t);
    public ArrayList<BarEntry> getMonthAll(ArrayList<Transaction> t);
    public ArrayList<BarEntry> getWeekIncome(ArrayList<Transaction> t);
    public ArrayList<BarEntry> getWeekPayment(ArrayList<Transaction> t);
    public ArrayList<BarEntry> getWeekAll(ArrayList<Transaction> t);
    public ArrayList<BarEntry> getDayIncome(ArrayList<Transaction> t);
    public ArrayList<BarEntry> getDayPayment(ArrayList<Transaction> t);
    public ArrayList<BarEntry> getDayAll(ArrayList<Transaction> t);
}
