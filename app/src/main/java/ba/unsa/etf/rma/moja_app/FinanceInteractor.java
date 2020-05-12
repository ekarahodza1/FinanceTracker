package ba.unsa.etf.rma.moja_app;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;

public class FinanceInteractor extends AsyncTask<String, Integer, Void> implements IFinanceInteractor {

    private ArrayList<Transaction> transactions;
    private OnTransactionsAdd caller;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FinanceInteractor(OnTransactionsAdd p) {
        caller = p;
        this.transactions = new ArrayList<Transaction>();

    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new
                InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Void doInBackground(String... strings) {


        String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                + "b2a4cd97-f112-4cb8-87eb-ef51be2fb114" + "/transactions?page=0";

        System.out.println("Uslo 1");
        try {
            URL url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String result = convertStreamToString(in);
            JSONObject jo = new JSONObject(result);
            JSONArray results = jo.getJSONArray("transactions");
            System.out.println("Uslo 2");
            for (int i = 0; i < results.length(); i++) {
                JSONObject t = results.getJSONObject(i);

                String date = t.getString("date"); LocalDate d1 = LocalDate.now();
                String title = t.getString("title");
                Double amount = t.getDouble("amount");
                Integer type = t.getInt("TransactionTypeId");
                String description = t.getString("itemDescription");
                Integer interval;
                if (t.getString("transactionInterval") == null) interval = 0;
                else interval = t.getInt("transactionInterval");
                String endDate = t.getString("endDate");  LocalDate d2 = null;
                Integer id = t.getInt("id");

                if(endDate != null) {
                    d2 = LocalDate.now();
                    d2.minusMonths(-2);
                }

                transactions.add(new Transaction(id, d1, title, amount, type, description, interval, d2));

                if (i==4) break;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public ArrayList<Transaction> getT(){
        return transactions;
    }

    public void add(Transaction t){
        transactions.add(t);
    }

    public void delete(Transaction t){
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t1 = transactions.get(i);
            if (t1.getTitle().matches(t.getTitle()) && t1.getAmount() == t.getAmount()
                    && t1.getTransactionInterval() == t.getTransactionInterval()
                    && t1.getItemDescription().matches(t.getItemDescription())){
                transactions.remove(t1);
                break;
            }
        }
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        caller.onDone(transactions);
    }

    public interface OnTransactionsAdd{
        public void onDone(ArrayList<Transaction> results);
    }


}
