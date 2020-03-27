package ba.unsa.etf.rma.moja_app;

import java.util.ArrayList;
import java.util.Date;

public class TransactionsModel {
    public static ArrayList<Transaction> transactions = new ArrayList<Transaction>() {
        {
            add(new Transaction(new Date(14,12,2020), "Shopping", 2000, Type.PURCHASE,
                    "new dress", 0, null));
            add(new Transaction(new Date(21,1,2020), "Insurance", 500, Type.INDIVIDUALINCOME,
                    null, 0, null));
            add(new Transaction(new Date(1,3,2020), "Salary", 2000, Type.REGULARINCOME,
                    null, 30, new Date(31, 12, 2020)));
            add(new Transaction(new Date(2020, 3,1), "Meal allowance", 200, Type.REGULARINCOME,
                     null, 30, new Date(2020, 12, 1)));
         add(new Transaction(new Date(2020,2,2), "Shopping", 999, Type.INDIVIDUALPAYMENT,
                "new washing machine", 0, null));
            add(new Transaction(new Date(2020,3,13), "Utilities", 53, Type.REGULARPAYMENT,
                    "electricity", 30, new Date(2020,12,1)));
            add(new Transaction(new Date(2020,3,13), "Utilities", 123, Type.REGULARPAYMENT,
                    "gas", 30, new Date(2020,12,1)));
        }

    };
}
