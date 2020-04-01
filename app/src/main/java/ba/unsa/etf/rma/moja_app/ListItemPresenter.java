package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class ListItemPresenter implements IListItemPresenter {
    private IListItemView view;
    private Context context;

    public ListItemPresenter(IListItemView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public boolean validateTitle(String title){
        if (title.length() > 3 && title.length() < 15) return true;
        return false;
    }

    @Override
    public boolean validateType(String type){
        if (type.matches("INDIVIDUAL PAYMENT") || type.matches("REGULAR PAYMENT") ||
                type.matches("PURCHASE") || type.matches("INDIVIDUAL INCOME") ||
                type.matches("REGULAR INCOME")) return true;
        return false;
    }
    @Override
    public boolean validateDescription(String description, String type){
        if ((type.matches("REGULAR INCOME") || type.matches("INDIVIDUAL INCOME")) &&
        !description.matches("-")) return false;
        return true;
    }
    @Override
    public boolean validateInterval(int interval, String type){
        if (!(type.matches("REGULAR PAYMENT") || type.matches("REGULAR INCOME")) && interval != 0) return false;
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean validateDate(String date){
        try {
            LocalDate d = LocalDate.parse(date);

        }
        catch(Exception e){
            return false;
        }
        return true;
    }
}
