package ba.unsa.etf.rma.moja_app;

import android.content.Context;

public interface IAccountInteractor {
    void updateTable(Account account, Context context);

    void addTable(Account account, Context context);
}
