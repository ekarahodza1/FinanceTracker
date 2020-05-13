package ba.unsa.etf.rma.moja_app;

import android.content.Context;
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

    @Override
    public boolean validateTitle(String title){
        if (title.length() >= 3 && title.length() <= 15) return true;
        return false;
    }

    @Override
    public boolean validateType(String type){
        if (type.matches("INDIVIDUALPAYMENT") || type.matches("REGULARPAYMENT") ||
                type.matches("PURCHASE") || type.matches("INDIVIDUALINCOME") ||
                type.matches("REGULARINCOME")) return true;
        return false;
    }
    @Override
    public boolean validateDescription(String description, String type){
        if ((type.matches("REGULARINCOME") || type.matches("INDIVIDUALINCOME")) &&
        !description.matches("")) return false;
        return true;
    }
    @Override
    public boolean validateInterval(int interval, String type){
        if (!(type.matches("REGULARPAYMENT") || type.matches("REGULARINCOME")) && interval != 0) return false;
        return true;
    }


}
