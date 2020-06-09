package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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
    public static final String DESCRIPTION = "actionDescription";
    public static final String ADD_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TRANSACTION_TABLE + " (" + INTERNAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ID + " INTEGER, "
                    + DESCRIPTION + " TEXT, "
                    + TRANSACTION_TITLE + " TEXT NOT NULL, "
                    + TRANSACTION_DATE + " TEXT, "
                    + TRANSACTION_END_DATE + " TEXT, "
                    + TRANSACTION_DESCRIPTION + " TEXT, "
                    + TRANSACTION_INTERVAL + " INTEGER, "
                    + TRANSACTION_AMOUNT + " REAL, "
                    + TYPE_ID + " INTEGER);";


    private static final String TRANSACTIONS_DROP = "DROP TABLE IF EXISTS " + TRANSACTION_TABLE;

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }


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
