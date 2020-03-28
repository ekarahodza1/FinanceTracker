package ba.unsa.etf.rma.moja_app;

import android.os.Build;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class TransactionsModel {
    public static ArrayList<Transaction> transactions = new ArrayList<Transaction>() {
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 2, 3), "Shopping", 2000, Type.PURCHASE,
                        "new dress", 0, null));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2019, 1, 24), "Insurance", 500, Type.INDIVIDUALINCOME,
                        null, 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 3, 1), "Salary", 2000, Type.REGULARINCOME,
                        null, 30, LocalDate.of(2020, 12, 1)));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 3, 1), "Meal allowance", 200, Type.REGULARINCOME,
                        null, 30, LocalDate.of(2020, 12, 1)));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 2, 2), "Shopping", 999, Type.INDIVIDUALPAYMENT,
                        "new washing machine", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 3, 13), "Utilities", 53, Type.REGULARPAYMENT,
                        "electricity", 30, LocalDate.of(2020, 12, 1)));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 3, 13), "Utilities", 123, Type.REGULARPAYMENT,
                        "gas", 30, LocalDate.of(2020, 12, 1)));
            }

            add(new Transaction(null, "Shopping", 2000, Type.PURCHASE,
                    "new dress", 0, null));
            add(new Transaction(null, "Insurance", 500, Type.INDIVIDUALINCOME,
                    null, 0, null));

        }


    };
}
