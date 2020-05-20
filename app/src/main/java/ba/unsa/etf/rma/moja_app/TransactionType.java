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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;



public class TransactionType {
    private static Map<Integer, String> mapa;
    private static boolean b = false;

    public TransactionType() {
        if (!b) {
            this.mapa = new HashMap<Integer, String>();
            jsonParse();
            b = true;
        }
    }

    public String getType(Integer id) {
        return mapa.get(id);
    }


    private void jsonParse() {
        String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/transactionTypes";
        try {
            URL url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String result = convertStreamToString(in);
            JSONObject jo = new JSONObject(result);
            JSONArray results = jo.getJSONArray("rows");

            for (int i = 0; i < results.length(); i++) {
                JSONObject type = results.getJSONObject(i);

                Integer id = type.getInt("id");
                String name = type.getString("name");

                mapa.put(id, name);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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


    public int getTypeId(String typeString) {
//        for (int i = 1; i < mapa.size(); i++){
//            if (mapa.get(i).matches(imageType)) return i;
//        }
        if (typeString.matches("Regular payment")) return 1;
        else if (typeString.matches("Regular income")) return 2;
        else if (typeString.matches("Purchase")) return 3;
        else if (typeString.matches("Individual income")) return 4;
        else if (typeString.matches("Individual payment")) return 5;
        return 0;
    }
}
