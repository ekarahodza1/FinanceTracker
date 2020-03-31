package ba.unsa.etf.rma.moja_app;


import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.Date;

public class Transaction {

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
        if (type == Type.INDIVIDUALINCOME || type == Type.REGULARINCOME) this.itemDescription = null;
        else this.itemDescription = itemDescription;
    }

    public int getTransactionInterval() {
        return transactionInterval;
    }

    public void setTransactionInterval(int transactionInterval) {
        if (type == Type.REGULARINCOME || type == Type.REGULARPAYMENT) this.transactionInterval = transactionInterval;
        else return;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        if (type == Type.REGULARINCOME || type == Type.REGULARPAYMENT) this.endDate = endDate;
        else this.endDate = null;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }



}
