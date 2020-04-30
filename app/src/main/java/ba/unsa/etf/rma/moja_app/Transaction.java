package ba.unsa.etf.rma.moja_app;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.Date;

public class Transaction implements Parcelable {

    private LocalDate date;
    private String title;
    private double amount;
    private String typeString;
    private Type type;
    private String itemDescription;
    private int transactionInterval;
    private LocalDate endDate;
    private int image;

    public Transaction(LocalDate date, String title, double amount, Type type, String itemDescription, int transactionInterval, LocalDate endDate) {
        this.date = date;
        this.title = title;
        this.amount = amount;
        this.type = type;
        this.itemDescription = itemDescription;
        this.transactionInterval = transactionInterval;
        this.endDate = endDate;
    }

    public Transaction(String type, int image) {
        this.typeString = type;
        this.image = image;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected Transaction(Parcel in) {
        title = in.readString();
        date = LocalDate.parse(in.readString());
        endDate = LocalDate.parse(in.readString());
        itemDescription = in.readString();
        amount = in.readDouble();
        transactionInterval = in.readInt();
        typeString = in.readString();
      //  type = in.readString();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getTransactionInterval() {
        return transactionInterval;
    }

    public void setTransactionInterval(int transactionInterval) {
        this.transactionInterval = transactionInterval;

    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;

    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (date == null) dest.writeString("");
        else dest.writeString(date.toString());
        if (endDate == null) dest.writeString("");
        else dest.writeString(endDate.toString());
        dest.writeString(title);
        dest.writeString(itemDescription);
        dest.writeDouble(amount);
        dest.writeInt(transactionInterval);
        dest.writeString(type.toString());


    }
}
