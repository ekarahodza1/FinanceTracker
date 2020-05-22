package ba.unsa.etf.rma.moja_app;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class AccountInteractor extends AsyncTask<HashMap<String, Account>, Integer, Void>  implements IAccountInteractor {

    private OnAccountAdd caller;
    private Account account;

    public AccountInteractor(OnAccountAdd p) {
        caller = p;
        this.account = new Account();
    };

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


    @Override
    protected Void doInBackground(HashMap<String, Account>... maps) {

        if (maps[0].get("update") != null) postAccount(maps[0].get("update"));
        //else if (maps[0].get("get") != null)
            getAccount();
        return null;
    }



    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        caller.onDone(account);
    }

    public interface OnAccountAdd{
        void onDone(Account result);
    }

    private void getAccount(){
        String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                + "b2a4cd97-f112-4cb8-87eb-ef51be2fb114";

        try {
            URL url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String result = convertStreamToString(in);
            JSONObject jo = new JSONObject(result);

            int id = jo.getInt("id");
            double budget = jo.getDouble("budget");
            double monthLimit = jo.getDouble("monthLimit");
            double totalLimit = jo.getDouble("totalLimit");

            account.setId(id);
            account.setBudget(budget);
            account.setMonthLimit(monthLimit);
            account.setTotalLimit(totalLimit);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void postAccount(Account account) {
        try {
            String querry = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                    + "b2a4cd97-f112-4cb8-87eb-ef51be2fb114";
            URL url = new URL (querry);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject obj = new JSONObject();

            obj.put("budget", account.getBudget());
            obj.put("monthLimit", account.getMonthLimit());
            obj.put("totalLimit", account.getTotalLimit());

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
}
