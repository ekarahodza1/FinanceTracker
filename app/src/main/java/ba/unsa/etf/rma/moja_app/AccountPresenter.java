package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.os.Parcelable;

public class AccountPresenter implements IAccountPresenter, AccountInteractor.OnAccountAdd{

    private Account account;
    private IFinanceView view;
    private Context context;

    public AccountPresenter(IFinanceView view, Context context) {
        this.account = new Account();
        this.context = context;
        this.view = view;
    }

    public Account get(){

        return account;
    }

    public void set(Parcelable a){
        this.account = (Account)a;
    }

    @Override
    public void addAccount() {
        new AccountInteractor((AccountInteractor.OnAccountAdd)
                this).execute();
    }

    public void set(Account a) {
        this.account = a;
    }

    @Override
    public void onDone(Account result) {

        account = result;
        view.setAccount(result);
    }
}
