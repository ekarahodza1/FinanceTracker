package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FinanceDBOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RMADataBase.db";
    public static final int DATABASE_VERSION = 2;

    public FinanceDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public FinanceDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String TRANSACTION_TABLE = "transactions";
    public static final String TRANSACTION_TITLE = "title";
    public static final String ID = "id";
    public static final String INTERNAL_ID = "_id";
    public static final String TRANSACTION_DATE = "date";
    public static final String TRANSACTION_END_DATE = "endDate";
    public static final String TRANSACTION_DESCRIPTION = "itemDescription";
    public static final String TRANSACTION_INTERVAL = "transactionInterval";
    public static final String TYPE_ID = "TransactionTypeId";
    public static final String TRANSACTION_AMOUNT = "amount";
    private static final String ADD_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TRANSACTION_TABLE + " (" + INTERNAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ID + " INTEGER UNIQUE, "
                    + TRANSACTION_TITLE + " TEXT NOT NULL, "
                    + TRANSACTION_DATE + " TEXT, "
                    + TRANSACTION_END_DATE + " TEXT, "
                    + TRANSACTION_DESCRIPTION + " TEXT, "
                    + TRANSACTION_INTERVAL + " INTEGER, "
                    + TRANSACTION_AMOUNT + " REAL, "
                    + TYPE_ID + " INTEGER);";


    private static final String TRANSACTIONS_DROP = "DROP TABLE IF EXISTS " + TRANSACTION_TABLE;



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ADD_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TRANSACTIONS_DROP);
        onCreate(db);
    }
}
