package ba.unsa.etf.rma.moja_app;

import android.os.Parcelable;

public interface IListItemPresenter {
    boolean validateTitle(String title);
    boolean validateType(String type);
    boolean validateDescription(String description, String type);
    boolean validateInterval(int interval, String type);
    void setTransaction(Parcelable transaction);
    Transaction getTransaction();


}
