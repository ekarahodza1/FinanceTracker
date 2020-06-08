package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AccountDBOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RMADataBase.db";
    public static final int DATABASE_VERSION = 2;

    public AccountDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public AccountDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String ACCOUNT_TABLE = "account";
    public static final String INTERNAL_ID = "_id";
    public static final String BUDGET = "budget";
    public static final String MONTH_LIMIT = "monthLimit";
    public static final String TOTAL_LIMIT = "itemDescription";
    private static final String TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + ACCOUNT_TABLE + " (" + INTERNAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + BUDGET + " REAL, "
                    + MONTH_LIMIT + " REAL, "
                    + TOTAL_LIMIT + " REAL);";



    private static final String TABLE_DROP = "DROP TABLE IF EXISTS " + ACCOUNT_TABLE;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP);
        onCreate(db);
    }
}
