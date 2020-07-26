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
        mapa.put(1, "Regular payment");
        mapa.put(2, "Regular income");
        mapa.put(3, "Purchase");
        mapa.put(4, "Individual income");
        mapa.put(5, "Individual payment");
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
        if (typeString == null) return 0;
        if (typeString.matches("Regular payment")) return 1;
        else if (typeString.matches("Regular income")) return 2;
        else if (typeString.matches("Purchase")) return 3;
        else if (typeString.matches("Individual income")) return 4;
        else if (typeString.matches("Individual payment")) return 5;
        return 0;
    }
}
