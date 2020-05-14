package ba.unsa.etf.rma.moja_app;

import android.os.Parcelable;

public interface IAccountPresenter {
    public Account get();
    public void set(Account a);
    public void set(Parcelable a);

    void addAccount();
}
