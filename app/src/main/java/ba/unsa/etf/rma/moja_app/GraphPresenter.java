package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class GraphPresenter implements IGraphPresenter,  FinanceInteractor.OnTransactionsAdd{
    private Context context;
    private static ArrayList<Transaction> list = new ArrayList<Transaction>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GraphPresenter(Context context) {
        this.context = context;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTransactions(){
        new FinanceInteractor((FinanceInteractor.OnTransactionsAdd)this).execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<BarEntry> getMonthIncome(){
        ArrayList<BarEntry> data = new ArrayList<BarEntry>();
        ArrayList<Transaction> t = new ArrayList<>();
        t.addAll(list);
        double[] months = new double[]{0,0,0,0,0,0,0,0,0,0,0,0};
        for (int i = 0; i < t.size(); i++){
            Transaction trans = t.get(i);
            if (trans.getTypeString().matches("Individual income") || trans.getTypeString().matches("Regular income")){
                if (trans.getDate().getYear() == LocalDate.now().getYear()){
                    if (trans.getEndDate() == null){
                        months[trans.getDate().getMonthValue() - 1] += trans.getAmount();
                    } else {
                        for (int j = trans.getDate().getMonthValue(); j <= trans.getEndDate().getMonthValue(); j++){
                            months[j-1] += trans.getAmount();
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 12; i++){
            data.add(new BarEntry(i+1, (float) months[i]));
        }
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<BarEntry> getMonthPayment(){
        ArrayList<BarEntry> data = new ArrayList<BarEntry>();
        double[] months = new double[]{0,0,0,0,0,0,0,0,0,0,0,0};
        ArrayList<Transaction> t = new ArrayList<>();
        t.addAll(list);
        for (int i = 0; i < t.size(); i++){
            Transaction trans = t.get(i);
            if (trans.getTypeString().matches("Purchase")
                    || trans.getTypeString().matches("Regular payment")
                    || trans.getTypeString().matches("Individual payment")){
                if (trans.getDate().getYear() == LocalDate.now().getYear()){
                    if (trans.getEndDate() == null){
                        months[trans.getDate().getMonthValue() - 1] -= trans.getAmount();
                    } else {
                        for (int j = trans.getDate().getMonthValue(); j <= trans.getEndDate().getMonthValue(); j++){
                            months[j-1] -= trans.getAmount();
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 12; i++){
            data.add(new BarEntry(i+1, (float) months[i]));
        }
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<BarEntry> getMonthAll(){
        ArrayList<BarEntry> data = new ArrayList<BarEntry>();
        double[] months = new double[]{0,0,0,0,0,0,0,0,0,0,0,0};
        ArrayList<Transaction> t = new ArrayList<>();
        t.addAll(list);
        for (int i = 0; i < t.size(); i++){
            Transaction trans = t.get(i);
                if (trans.getDate().getYear() == LocalDate.now().getYear()){
                    if (trans.getEndDate() == null){
                        months[trans.getDate().getMonthValue() - 1] += trans.getAmount();
                    } else {
                        for (int j = trans.getDate().getMonthValue(); j <= trans.getEndDate().getMonthValue(); j++){
                            months[j-1] += trans.getAmount();
                        }
                    }
            }
        }

        for (int i = 0; i < 12; i++){
            data.add(new BarEntry(i+1, (float) months[i]));
        }
        return data;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<BarEntry> getWeekIncome(){
        ArrayList<BarEntry> data = new ArrayList<BarEntry>();
        double[] weeks = new double[]{0,0,0,0,0};
        ArrayList<Transaction> t = new ArrayList<>();
        t.addAll(list);
        for (int i = 0; i < t.size(); i++){
            Transaction trans = t.get(i);
            if (trans.getTypeString().matches("Individual income") || trans.getTypeString().matches("Regular income")){
                if (trans.getDate().getMonthValue() == LocalDate.now().getMonthValue()){
                    int j = trans.getDate().getDayOfMonth() / 7;
                    weeks[j] += trans.getAmount();
                }
                else if (trans.getEndDate() != null
                        && LocalDate.now().getMonthValue() <= trans.getEndDate().getMonthValue()
                        && LocalDate.now().getMonthValue() >= trans.getDate().getMonthValue()){
                    int j = trans.getDate().getDayOfMonth() / 7;
                    weeks[j] += trans.getAmount();
                }
            }
        }

        for (int i = 0; i < 5; i++){
            data.add(new BarEntry(i+1, (float) weeks[i]));
        }
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<BarEntry> getWeekPayment(){
        ArrayList<BarEntry> data = new ArrayList<BarEntry>();
        ArrayList<Transaction> t = new ArrayList<>();
        t.addAll(list);
        double[] weeks = new double[]{0,0,0,0,0};
        for (int i = 0; i < t.size(); i++){
            Transaction trans = t.get(i);
            if (trans.getTypeString().matches("Purchase")
                    || trans.getTypeString().matches("Regular payment")
                    || trans.getTypeString().matches("Individual payment")){
                if (trans.getDate().getMonthValue() == LocalDate.now().getMonthValue()){
                    int j = trans.getDate().getDayOfMonth() / 7;
                    weeks[j] -= trans.getAmount();
                } else if (trans.getEndDate() != null
                        && LocalDate.now().getMonthValue() <= trans.getEndDate().getMonthValue()
                        && LocalDate.now().getMonthValue() >= trans.getDate().getMonthValue()){
                    int j = trans.getDate().getDayOfMonth() / 7;
                    weeks[j] -= trans.getAmount();
                }
            }
        }

        for (int i = 0; i < 5; i++){
            data.add(new BarEntry(i+1, (float) weeks[i]));
        }
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<BarEntry> getWeekAll(){
        ArrayList<BarEntry> data = new ArrayList<BarEntry>();
        double[] weeks = new double[]{0,0,0,0,0};
        ArrayList<Transaction> t = new ArrayList<>();
        t.addAll(list);
        for (int i = 0; i < t.size(); i++){
            Transaction trans = t.get(i);
                if (trans.getDate().getMonthValue() == LocalDate.now().getMonthValue()){
                    int j = trans.getDate().getDayOfMonth() / 7;
                    weeks[j] += trans.getAmount();
                } else if (trans.getEndDate() != null
                        && LocalDate.now().getMonthValue() <= trans.getEndDate().getMonthValue()
                        && LocalDate.now().getMonthValue() >= trans.getDate().getMonthValue()) {
                    int j = trans.getDate().getDayOfMonth() / 7;
                    weeks[j] += trans.getAmount();
                }

        }

        for (int i = 0; i < 5; i++){
            data.add(new BarEntry(i+1, (float) weeks[i]));
        }
        return data;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<BarEntry> getDayIncome(){
        ArrayList<BarEntry> data = new ArrayList<BarEntry>();
        ArrayList<Transaction> t = new ArrayList<>();
        t.addAll(list);
        double[] days = new double[30];
        for (int i = 0; i < 30; i++) days[i] = 0;
        for (int i = 0; i < t.size(); i++){
            Transaction trans = t.get(i);
            if (trans.getTypeString().matches("Purchase")
                    || trans.getTypeString().matches("Regular payment")
                    || trans.getTypeString().matches("Individual payment")){
                if (trans.getDate().getMonthValue() == LocalDate.now().getMonthValue()){ ;
                    days[trans.getDate().getDayOfMonth() - 1] += trans.getAmount();
                } else if (trans.getEndDate() != null
                        && LocalDate.now().getMonthValue() <= trans.getEndDate().getMonthValue()
                        && LocalDate.now().getMonthValue() >= trans.getDate().getMonthValue()){
                    days[trans.getDate().getDayOfMonth() - 1] += trans.getAmount();
                }
            }
        }

        for (int i = 0; i < 30; i++){
            data.add(new BarEntry(i+1, (float) days[i]));
        }
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<BarEntry> getDayPayment(){
        ArrayList<BarEntry> data = new ArrayList<BarEntry>();
        ArrayList<Transaction> t = new ArrayList<>();
        t.addAll(list);
        double[] days = new double[30];
        for (int i = 0; i < 30; i++) days[i] = 0;
        for (int i = 0; i < t.size(); i++){
            Transaction trans = t.get(i);
            if (trans.getTypeString().matches("Individual income") || trans.getTypeString().matches("Regular income")){
                if (trans.getDate().getMonthValue() == LocalDate.now().getMonthValue()){ ;
                    days[trans.getDate().getDayOfMonth() - 1] += trans.getAmount();
                } else if (trans.getEndDate() != null
                        && LocalDate.now().getMonthValue() <= trans.getEndDate().getMonthValue()
                        && LocalDate.now().getMonthValue() >= trans.getDate().getMonthValue()){
                    days[trans.getDate().getDayOfMonth() - 1] += trans.getAmount();
                }
            }
        }

        for (int i = 0; i < 30; i++){
            data.add(new BarEntry(i+1, (float) days[i]));
        }
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<BarEntry> getDayAll(){
        ArrayList<BarEntry> data = new ArrayList<BarEntry>();
        ArrayList<Transaction> t = new ArrayList<>();
        t.addAll(list);
        double[] days = new double[30];
        for (int i = 0; i < 30; i++) days[i] = 0;
        for (int i = 0; i < t.size(); i++){
            Transaction trans = t.get(i);

                if (trans.getDate().getMonthValue() == LocalDate.now().getMonthValue()){ ;
                    days[trans.getDate().getDayOfMonth() - 1] += trans.getAmount();
                } else if (trans.getEndDate() != null
                        && LocalDate.now().getMonthValue() <= trans.getEndDate().getMonthValue()
                        && LocalDate.now().getMonthValue() >= trans.getDate().getMonthValue()){
                    days[trans.getDate().getDayOfMonth() - 1] += trans.getAmount();
                }

        }

        for (int i = 0; i < 30; i++){
            data.add(new BarEntry(i+1, (float) days[i]));
        }
        return data;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDone(ArrayList<Transaction> results) {
        list.addAll(results);
        GraphsFragment g = new GraphsFragment();
        g.set(results);
        System.out.println("ucitano");
    }

    public ArrayList<Transaction> get(){
        return list;
    }
}
