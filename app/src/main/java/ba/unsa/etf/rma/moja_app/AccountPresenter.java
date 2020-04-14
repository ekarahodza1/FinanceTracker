package ba.unsa.etf.rma.moja_app;

import android.content.Context;

public class AccountPresenter implements IAccountPresenter{

    private Account account = Model.account;
    private IFinanceView view;
    private Context context;

    public AccountPresenter(IFinanceView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public Account get(){
        return account;
    }

    public void set(Account a){
        this.account = a;
    }
}
