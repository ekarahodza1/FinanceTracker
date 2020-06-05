package ba.unsa.etf.rma.moja_app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
    private FinanceDBOpenHelper financeDBOpenHelper;
    private SQLiteDatabase database;

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

        if (maps.length != 0) {
            if (maps[0].get(1) != null) {
                postTransaction(maps[0].get(1));
                System.out.println("POSTAVLJANE");
            } else if (maps[0].get(2) != null) {
                deleteTransaction(maps[0].get(2));
                System.out.println("BRISANJE");
            } else if (maps[0].get(3) != null) {
                updateTransaction(maps[0].get(3));
                System.out.println("DOBAVLJANJE");
            }

            else if (maps[0].get(0) != null) {

                System.out.println("DOBAVLJANJE");
            }
        }
        getTransactions();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        caller.onDone(transactions);
    }

    public interface OnTransactionsAdd{
        public void onDone(ArrayList<Transaction> results);
    }

    @Override
    public ArrayList<Transaction> getT(){
        return transactions;
    }


    //add, delete, update stavljaju u bazu transakcije u istoimene tabele kao i metode
    @Override
    public void add(Transaction t, Context context){

        financeDBOpenHelper = new FinanceDBOpenHelper(context);
        ContentValues values = new ContentValues();
        values.put(FinanceDBOpenHelper.TRANSACTION_TITLE, t.getTitle());
        values.put(FinanceDBOpenHelper.TRANSACTION_DATE, t.getDate().toString());
        if (t.getEndDate() == null) values.put(FinanceDBOpenHelper.TRANSACTION_END_DATE, "null");
        else values.put(FinanceDBOpenHelper.TRANSACTION_END_DATE, t.getEndDate().toString());
        values.put(FinanceDBOpenHelper.TRANSACTION_DESCRIPTION, t.getItemDescription());
        values.put(FinanceDBOpenHelper.TRANSACTION_INTERVAL, t.getTransactionInterval());
        values.put(FinanceDBOpenHelper.TYPE_ID, t.getTId());
        values.put(FinanceDBOpenHelper.TRANSACTION_AMOUNT, t.getAmount());
        database = financeDBOpenHelper.getWritableDatabase();
        database.insert(FinanceDBOpenHelper.ADD_TABLE, null, values);

        String selectQuery = "SELECT  * FROM " + FinanceDBOpenHelper.ADD_TABLE;
        Cursor cursor      = database.rawQuery(selectQuery, null);

        if (cursor != null){
            cursor.moveToFirst();
            do{
            int title = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TRANSACTION_TITLE);
            int date = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TRANSACTION_DATE);
            int endDate = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TRANSACTION_END_DATE);
            int itemDescription = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TRANSACTION_DESCRIPTION);
            int transactionInterval = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TRANSACTION_INTERVAL);
            int typeId = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TYPE_ID);
            int amount = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TRANSACTION_AMOUNT);

            System.out.println(cursor.getString(title) + " " + cursor.getString(amount));

            } while(cursor.moveToNext());
        }
        cursor.close();


    }

    @Override
    public void delete(Transaction t, Context context){
        financeDBOpenHelper = new FinanceDBOpenHelper(context);
        ContentValues values = new ContentValues();
        values.put(FinanceDBOpenHelper.TRANSACTION_ID, t.getId());
        database = financeDBOpenHelper.getWritableDatabase();
        database.insert(FinanceDBOpenHelper.DELETE_TABLE, null, values);

    }

    @Override
    public void update(Transaction t, Context context){
        financeDBOpenHelper = new FinanceDBOpenHelper(context);
        ContentValues values = new ContentValues();
        values.put(FinanceDBOpenHelper.ID, t.getId());
        values.put(FinanceDBOpenHelper.TITLE, t.getTitle());
        values.put(FinanceDBOpenHelper.DATE, t.getDate().toString());
        if (t.getEndDate() == null) values.put(FinanceDBOpenHelper.END_DATE, "null");
        else values.put(FinanceDBOpenHelper.END_DATE, t.getEndDate().toString());
        values.put(FinanceDBOpenHelper.DESCRIPTION, t.getItemDescription());
        values.put(FinanceDBOpenHelper.INTERVAL, t.getTransactionInterval());
        values.put(FinanceDBOpenHelper.T_ID, t.getTId());
        values.put(FinanceDBOpenHelper.AMOUNT, t.getAmount());
        database = financeDBOpenHelper.getWritableDatabase();
        database.insert(FinanceDBOpenHelper.UPDATE_TABLE, null, values);

    }


    //getAddTransactions, getUpdateTransactions, getDeleteTransactions uzimaju iz baze transakcije iz ovih tabela
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public ArrayList<Transaction> getAddTransactions(Context context) {
        financeDBOpenHelper = new FinanceDBOpenHelper(context);
        database = financeDBOpenHelper.getWritableDatabase();
        ArrayList<Transaction> niz = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + FinanceDBOpenHelper.ADD_TABLE;
        Cursor cursor      = database.rawQuery(selectQuery, null);

        if (cursor != null){
            cursor.moveToFirst();
            do{
                int title = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TRANSACTION_TITLE);
                int date = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TRANSACTION_DATE);
                int endDate = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TRANSACTION_END_DATE);
                int itemDescription = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TRANSACTION_DESCRIPTION);
                int transactionInterval = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TRANSACTION_INTERVAL);
                int typeId = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TYPE_ID);
                int amount = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TRANSACTION_AMOUNT);

                String d1 = cursor.getString(endDate);
                LocalDate d2 = null;
                if (!d1.matches("null")) d2 = LocalDate.parse(d1);


                niz.add(new Transaction(-1, LocalDate.parse(cursor.getString(date)), cursor.getString(title),
                        cursor.getInt(amount), cursor.getInt(typeId), cursor.getString(itemDescription),
                        cursor.getInt(transactionInterval), d2));

            } while(cursor.moveToNext());
        }
        cursor.close();
        return niz;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public ArrayList<Transaction> getUpdateTransactions(Context context) {
        financeDBOpenHelper = new FinanceDBOpenHelper(context);
        database = financeDBOpenHelper.getWritableDatabase();
        ArrayList<Transaction> niz = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + FinanceDBOpenHelper.UPDATE_TABLE;
        Cursor cursor      = database.rawQuery(selectQuery, null);

        if (cursor != null){
            cursor.moveToFirst();
            do{
                int id = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.ID);
                int title = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TITLE);
                int date = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.DATE);
                int endDate = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.END_DATE);
                int itemDescription = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.DESCRIPTION);
                int transactionInterval = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.INTERVAL);
                int typeId = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.T_ID);
                int amount = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.AMOUNT);

                niz.add(new Transaction(cursor.getInt(id), LocalDate.parse(cursor.getString(date)), cursor.getString(title),
                        cursor.getInt(amount), cursor.getInt(typeId), cursor.getString(itemDescription),
                        cursor.getInt(transactionInterval), LocalDate.parse(cursor.getString(endDate))));

            } while(cursor.moveToNext());
        }
        cursor.close();
        return niz;
    }

    @Override
    public ArrayList<Transaction> getDeleteTransactions(Context context) {
        financeDBOpenHelper = new FinanceDBOpenHelper(context);
        database = financeDBOpenHelper.getWritableDatabase();
        ArrayList<Transaction> niz = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + FinanceDBOpenHelper.DELETE_TABLE;
        Cursor cursor      = database.rawQuery(selectQuery, null);

        if (cursor != null){
            cursor.moveToFirst();
            do{
                int id = cursor.getColumnIndexOrThrow(FinanceDBOpenHelper.TRANSACTION_ID);

                niz.add(new Transaction(cursor.getInt(id)));

            } while(cursor.moveToNext());
        }
        cursor.close();
        return niz;
    }

    @Override
    public void deleteAddTable(Context context) {
        financeDBOpenHelper = new FinanceDBOpenHelper(context);
        database = financeDBOpenHelper.getWritableDatabase();
        database.delete(FinanceDBOpenHelper.ADD_TABLE, null, null);
    }

    @Override
    public void deleteUpdateTable(Context context) {
        financeDBOpenHelper = new FinanceDBOpenHelper(context);
        database = financeDBOpenHelper.getWritableDatabase();
        database.delete(FinanceDBOpenHelper.UPDATE_TABLE, null, null);
    }

    @Override
    public void deleteDeleteTable(Context context) {
        financeDBOpenHelper = new FinanceDBOpenHelper(context);
        database = financeDBOpenHelper.getWritableDatabase();
        database.delete(FinanceDBOpenHelper.DELETE_TABLE, null, null);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getTransactions(){


        for (int j = 0; ; j++) {

            String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                    + "b2a4cd97-f112-4cb8-87eb-ef51be2fb114" + "/transactions?page=" + j;


            try {
                URL url = new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String result = convertStreamToString(in);
                JSONObject jo = new JSONObject(result);
                JSONArray results = jo.getJSONArray("transactions");
                if (results.length() == 0) break;

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
            String querry = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/" +
                    "account/b2a4cd97-f112-4cb8-87eb-ef51be2fb114/transactions";
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
            obj.put("transactionInterval", t.getTransactionInterval());
            obj.put("endDate", t.getEndDate());
            obj.put("TransactionTypeId", t.getTId());
            String inputString = String.valueOf(obj);

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

    private void deleteTransaction(Transaction t)  {

        try {
            String querry = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/" +
                    "account/b2a4cd97-f112-4cb8-87eb-ef51be2fb114/transactions/" + t.getId();
            URL url = new URL (querry);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

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
        }
    }

    private void updateTransaction(Transaction t){
        try {
            String querry = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com" +
                    "/account/b2a4cd97-f112-4cb8-87eb-ef51be2fb114/transactions/" +
                    t.getId();
            URL url = new URL (querry);
            System.out.println(querry);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type",  "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject obj = new JSONObject();
            obj.put("date", t.getDate());
            obj.put("title", t.getTitle());
            obj.put("amount", t.getAmount());
            obj.put("itemDescription", t.getItemDescription());
            obj.put("transactionInterval", t.getTransactionInterval());
            obj.put("endDate", t.getEndDate());
            obj.put("TransactionTypeId", t.getTId());
            String inputString = (obj).toString();

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
