package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;

import java.util.HashMap;

public class AccountPresenter implements IAccountPresenter, AccountInteractor.OnAccountAdd{

    private Account account;
    private IFinanceView view;
    private Context context;
    private HashMap<String, Account> map = new HashMap<>();

    public AccountPresenter(IFinanceView view, Context context) {
        this.account = new Account();
        this.context = context;
        this.view = view;
    }

    public Account get(){

        return account;
    }

    public void setAccount(Parcelable a){
        this.account = (Account)a;
    }

    @Override
    public void addAccount() {
        map.put("get", new Account(-1, 0, 0, 0));
        new AccountInteractor((AccountInteractor.OnAccountAdd)
                this).execute(map);
    }

    public void setAccount(Account a) {
        map.put("update", a);
        new AccountInteractor((AccountInteractor.OnAccountAdd)
                    this).execute(map);
    }

    @Override
    public void onDone(Account result) {

        account = result;
        view.setAccount(result);
    }

    @Override
    public boolean connected() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        return connected;
    }
}
