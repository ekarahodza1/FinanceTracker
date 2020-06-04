package ba.unsa.etf.rma.moja_app;

import android.os.Parcelable;

import java.time.LocalDate;

public interface IListItemPresenter {
    boolean validateTitle(String title);
    boolean validateDescription(String description, String type);
    boolean validateInterval(int interval, String type);
    void setTransaction(Parcelable transaction);
    Transaction getTransaction();
    boolean validateDate(LocalDate date1, LocalDate date2, int id);
    boolean connected();
}
