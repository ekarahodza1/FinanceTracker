package ba.unsa.etf.rma.moja_app;

import android.content.Context;

public class FinancePresenter implements IFinancePresenter {
    public static void sortTransactions(String criteria){

    }

    private IFinanceView view;
    private IFinanceInteractor interactor;
    private Context context;

    public FinancePresenter(IFinanceView view, Context context) {
        this.view       = view;
        this.interactor = new FinanceInteractor();
        this.context    = context;
    }
    @Override
    public void refreshTransactions() {
        view.setTransactions(interactor.get());
        view.notifyTransactionsListDataSetChanged();
    }
}
