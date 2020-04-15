package ba.unsa.etf.rma.moja_app;

import java.util.ArrayList;

public class FinanceInteractor implements IFinanceInteractor {

    private ArrayList<Transaction> transactions;

    public FinanceInteractor() {
        this.transactions = Model.transactions;
    }

    @Override
    public ArrayList<Transaction> get(){
        return transactions;
    }

    public void add(Transaction t){
        transactions.add(t);
    }

    public void delete(Transaction t){
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t1 = transactions.get(i);
            if (t1.getTitle().matches(t.getTitle()) && t1.getAmount() == t.getAmount()
                    && t1.getTransactionInterval() == t.getTransactionInterval()
                    && t1.getItemDescription().matches(t.getItemDescription())){
                transactions.remove(t1);
                break;
            }
        }
    }


}
