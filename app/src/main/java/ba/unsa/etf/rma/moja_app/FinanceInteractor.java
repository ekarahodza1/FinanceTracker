package ba.unsa.etf.rma.moja_app;

import java.util.ArrayList;

public class FinanceInteractor implements IFinanceInteractor {
    @Override
    public ArrayList<Transaction> get(){
        return TransactionsModel.transactions;
    }
}
