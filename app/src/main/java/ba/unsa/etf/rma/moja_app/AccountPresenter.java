package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.os.Parcelable;

public class AccountPresenter implements IAccountPresenter{

    private Account account = Model.account;
    private Context context;

    public AccountPresenter(Context context) {

        this.context = context;
    }

    public Account get(){
        return account;
    }

    public void set(Parcelable a){
        this.account = (Account)a;
    }

    public void set(Account a) {
        this.account = a;
    }
}
