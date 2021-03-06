package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class ListItemPresenter implements IListItemPresenter {

    private Context context;
    private Transaction transaction;

    public ListItemPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void setTransaction(Parcelable transaction) {
        this.transaction = (Transaction) transaction;
    }

    @Override
    public Transaction getTransaction() {
        return transaction;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean validateDate(LocalDate date1, LocalDate date2, int id) {
        if (date1 == null) return true;
        if ((id == 1 || id == 2) && date2 == null) return true;
        if ((id == 1 || id == 2) && date1.isAfter(date2)) return true;
        return false;
    }

    @Override
    public boolean connected(Context c) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        return connected;
    }

    @Override
    public boolean validateTitle(String title){
        if (title.length() >= 3 && title.length() <= 15) return true;
        return false;
    }


    @Override
    public boolean validateDescription(String description, String type){
        return true;
    }
    @Override
    public boolean validateInterval(int interval, String type){
        if (!(type.matches("Regular payment") || type.matches("Regular income")) && interval != 0) return false;
        return true;
    }


}
