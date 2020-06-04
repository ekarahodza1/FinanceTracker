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

    public static final String ADD_TABLE = "add";
    public static final String TRANSACTION_TITLE = "title";
    public static final String TRANSACTION_DATE = "date";
    public static final String TRANSACTION_END_DATE = "endDate";
    public static final String TRANSACTION_DESCRIPTION = "itemDescription";
    public static final String TRANSACTION_INTERVAL = "transactionInterval";
    public static final String TYPE_ID = "TransactionTypeId";
    public static final String TRANSACTION_AMOUNT = "amount";
    private static final String ADD_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + ADD_TABLE + " ("
                    + TRANSACTION_TITLE + " TEXT NOT NULL, "
                    + TRANSACTION_DATE + " TEXT, "
                    + TRANSACTION_END_DATE + " TEXT, "
                    + TRANSACTION_DESCRIPTION + " TEXT, "
                    + TRANSACTION_INTERVAL + " INTEGER, "
                    + TRANSACTION_AMOUNT + " REAL, "
                    + TYPE_ID + " INTEGER);";

    private static final String ADD_DROP = "DROP TABLE IF EXISTS " + ADD_TABLE;

    public static final String UPDATE_TABLE = "update";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String END_DATE = "endDate";
    public static final String DESCRIPTION = "itemDescription";
    public static final String INTERVAL = "transactionInterval";
    public static final String T_ID = "TransactionTypeId";
    public static final String AMOUNT = "amount";
    private static final String UPDATE_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + UPDATE_TABLE + " ("
                    + ID + " INTEGER, "
                    + TITLE + " TEXT NOT NULL, "
                    + DATE + " TEXT, "
                    + END_DATE + " TEXT, "
                    + DESCRIPTION + " TEXT, "
                    + INTERVAL + " INTEGER, "
                    + AMOUNT + " REAL, "
                    + T_ID + " INTEGER);";

    private static final String UPDATE_DROP = "DROP TABLE IF EXISTS " + UPDATE_TABLE;

    public static final String DELETE_TABLE = "delete";
    public static final String TRANSACTION_ID = "id";
    private static final String DELETE_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + DELETE_TABLE + " ("
                    + TRANSACTION_ID + " INTEGER);";

    private static final String DELETE_DROP = "DROP TABLE IF EXISTS " + DELETE_TABLE;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ADD_TABLE_CREATE);
        db.execSQL(UPDATE_TABLE_CREATE);
        db.execSQL(DELETE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ADD_DROP);
        db.execSQL(UPDATE_DROP);
        db.execSQL(DELETE_DROP);
        onCreate(db);
    }
}
