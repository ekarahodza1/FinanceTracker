package ba.unsa.etf.rma.moja_app;

import android.os.Parcelable;

public interface IAccountPresenter {
    public Account get();
    public void setAccount(Account a);
    public void setAccount(Parcelable a);

    void addAccount();
}
