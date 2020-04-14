package ba.unsa.etf.rma.moja_app;

import android.os.Build;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Model {
    public static ArrayList<Transaction> transactions = new ArrayList<Transaction>() {
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 2, 3), "Shopping", -80, Type.PURCHASE,
                        "new dress", 0, null));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2019, 1, 24), "Insurance", 500, Type.INDIVIDUALINCOME,
                        "", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 3, 1), "Salary", 2000, Type.REGULARINCOME,
                        "", 30, LocalDate.of(2020, 12, 1)));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 3, 1), "Meal allowance", 200, Type.REGULARINCOME,
                        "", 30, LocalDate.of(2020, 12, 1)));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 2, 2), "Shopping", -999, Type.INDIVIDUALPAYMENT,
                        "new washing machine", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 3, 13), "Utilities", -53, Type.REGULARPAYMENT,
                        "electricity", 30, LocalDate.of(2020, 12, 1)));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2019, 3, 13), "Gift", -199, Type.PURCHASE,
                        "blender", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 3, 13), "Utilities", -123, Type.REGULARPAYMENT,
                        "gas", 30, LocalDate.of(2020, 12, 1)));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2019, 3, 13), "Gift", -199, Type.PURCHASE,
                        "blender", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2019, 10, 28), "Kids", -72, Type.PURCHASE,
                        "barbie doll", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 1, 3), "Kids", -43, Type.PURCHASE,
                        "blanket", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2019, 8, 20), "Penalty", -100, Type.INDIVIDUALPAYMENT,
                        "parking ticket", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 3, 13), "Utilities", -75, Type.REGULARPAYMENT,
                        "groceries", 15, LocalDate.of(2020, 12, 1)));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2019, 11, 18), "Shopping", -99, Type.PURCHASE,
                        "new boots", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2019, 12, 10), "Penalty", -100, Type.INDIVIDUALPAYMENT,
                        "parking ticket", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2018, 12, 31), "Bonus", 100, Type.INDIVIDUALINCOME,
                        "", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2019, 8, 18), "Shopping", -39, Type.PURCHASE,
                        "pants", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2020, 4, 13), "Utilities", -15, Type.REGULARPAYMENT,
                        "water bill", 30, LocalDate.of(2020, 12, 1)));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2019, 3, 3), "Gift", -17, Type.PURCHASE,
                        "picture frame", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2019, 12, 31), "Bonus", 100, Type.INDIVIDUALINCOME,
                        "", 0, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(new Transaction(LocalDate.of(2019, 1, 7), "Shopping", -29, Type.PURCHASE,
                        "headphones", 0, null));
            }

        }

    };

    public static Account account = new Account(658, -1000, -1000);
}
