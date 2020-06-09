package ba.unsa.etf.rma.moja_app;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Transaction implements Parcelable{

    private LocalDate date;
    private String title;
    private double amount;
    private String typeString;
    private int type_;
    private String itemDescription;
    private int transactionInterval;
    private LocalDate endDate;
    private int image;
    private int id;
    private int internalId;
    private String description;

    public int getInternalId() {
        return internalId;
    }

    public void setInternalId(int internalId) {
        this.internalId = internalId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Transaction(int id, LocalDate date, String title, double amount, int type,
                       String itemDescription, int transactionInterval, LocalDate endDate) {
        this.date = date;
        this.title = title;
        this.amount = amount;
        this.type_ = type;
        this.itemDescription = itemDescription;
        this.transactionInterval = transactionInterval;
        this.endDate = endDate;
        this.id = id;

        TransactionType t1 = new TransactionType();
        typeString = t1.getType(type);

    }

    public Transaction(LocalDate date, String title, double amount, String imageType,
                       String itemDescription, Integer transactionInterval, LocalDate endDate) {

        this.date = date;
        this.title = title;
        this.amount = amount;
        this.itemDescription = itemDescription;
        this.transactionInterval = transactionInterval;
        this.endDate = endDate;
        this.typeString = imageType;
        TransactionType t1 = new TransactionType();
        this.type_ = t1.getTypeId(imageType);




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
        id = in.readInt();
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

    public Transaction(int i) {
        this.id = i;
    }


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTId() {
        return type_;
    }

    public void setTId(int type_) {
        this.type_ = type_;
    }

        public int getTypeId(String typeString){

        if (typeString.matches("Regular payment")) return 1;
        else if (typeString.matches("Regular income")) return 2;
        else if (typeString.matches("Purchase")) return 3;
        else if (typeString.matches("Individual income")) return 4;
        else if (typeString.matches("Individual payment")) return 5;
        return 0;

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
       // dest.writeString(type.toString());
        dest.writeInt(id);


    }
}
