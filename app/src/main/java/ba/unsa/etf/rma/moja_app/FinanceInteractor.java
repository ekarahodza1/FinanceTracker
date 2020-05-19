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
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class FinanceInteractor extends AsyncTask<HashMap<Integer, Transaction>, Integer, Void> implements IFinanceInteractor {

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
    protected Void doInBackground(HashMap<Integer, Transaction>... maps) {
        if (maps[0].get(0) != null) getTransactions();
        else if (maps[0].get(1) != null) postTransaction(maps[0].get(1));

        return null;
    }


    @Override
    public ArrayList<Transaction> getT(){
        return transactions;
    }

    public void add(Transaction t){

        //postTransaction(t);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getTransactions(){
        for (int j = 0; j < 4; j++) {

            String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                    + "b2a4cd97-f112-4cb8-87eb-ef51be2fb114" + "/transactions?page=" + j;

            try {
                URL url = new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String result = convertStreamToString(in);
                JSONObject jo = new JSONObject(result);
                JSONArray results = jo.getJSONArray("transactions");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject t = results.getJSONObject(i);

                    String date = t.getString("date");
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                    LocalDate d1 = LocalDate.parse(date, dateTimeFormatter);
                    String title = t.getString("title");
                    Double amount = t.getDouble("amount");
                    Integer type = t.getInt("TransactionTypeId");
                    String description = t.getString("itemDescription");
                    if (description.matches("null")) description = "";
                    Integer interval;
                    String interv = t.getString("transactionInterval");
                    if (interv.matches("null")) interval = 0;
                    else interval = Integer.parseInt(interv);
                    String endDate = t.getString("endDate");
                    LocalDate d2 = null;
                    Integer id = t.getInt("id");

                    if (!endDate.matches("null")) {
                        d2 = LocalDate.parse(endDate, dateTimeFormatter);
                    }

                    transactions.add(new Transaction(id, d1, title, amount, type, description, interval, d2));

                    if (i == 4) break;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void postTransaction(Transaction t)  {

        try {
            String querry = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/b2a4cd97-f112-4cb8-87eb-ef51be2fb114/transactions";
            URL url = new URL (querry);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject obj = new JSONObject();
            obj.put("date", t.getDate());
            obj.put("title", t.getTitle());
            obj.put("amount", t.getAmount());
            obj.put("itemDescription", t.getItemDescription());
            obj.put("endDate", t.getEndDate());
            //obj.put("AccountId", 11);
           // obj.put("TransactionTypeId", 4);
            String inputString = String.valueOf(obj);

            OutputStream o = con.getOutputStream();

            try(OutputStream os = con.getOutputStream()){
                byte[] input = inputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            int code = con.getResponseCode();
            System.out.println(code);

            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))){
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
